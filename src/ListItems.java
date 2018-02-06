import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ListItems{  //объект для хранения объектов резерва, плана, вывода
    boolean equalItem = false;
    List<Reserve> reserveList;//список объектов

    ListItems() {
        reserveList = new ArrayList<Reserve>();
    }//конструктор для создания списка

    public void getData(String filename, Class cl) {//метод для получения данных из файла указанного в аргументах
        try {
            File file = new File(filename);//

            Scanner input = new Scanner(file);//создание сканера по файлу


            while (input.hasNextLine()) {//цикл для обхода всех строк файла
                if (cl == Purchase.class) { //если в метод передали класс для вывода плана закупок
                    Purchase purchase = new Purchase();//создаем объект вывода плана закупок
                    purchase.getData(input);//заполняем данными из файла
                    reserveList.add(purchase);//добавляем в список
                } else if (cl == Plan.class) {//если в метод передали класс - план реализаци
                    Plan plan = new Plan();
                    plan.getData(input);
                    reserveList.add(plan);
                } else if (cl == Reserve.class) {//если в метод передали класс - резерва
                    Reserve reserve = new Reserve();
                    reserve.getData(input);//заполняем данными из файла
                    equalItem = false;//находим все одинаковые детали(с одинаковыми именами) и объединяем в один объект
                    for (int i = 0; i < reserveList.size(); i++) {//так как детали в файле могут повторяться
                        if (reserve.equals(reserveList.get(i))) {
                            reserveList.get(i).setAmount(reserveList.get(i).getAmount() + reserve.getAmount());
                            equalItem = true;
                        }
                    }
                    if (equalItem == false) {
                        reserveList.add(reserve);
                    }
                }
                input.nextLine();//следующая строка

            }
            input.close();//закрываем сканнер

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ListItems setPurchase(ListItems listItemsPlan) {//метод для создания объектов  закупок конкретных деталей
        ListItems listItemsPurchase = new ListItems();// создание объекта списка закупок
        for (int i = 0; i < this.reserveList.size(); i++) {//перебираем все элементы в списке резервных деталей
            Reserve reserve = reserveList.get(i); //берем i-ый объект из списка и присваиваем ему имя reserve
            for (int j = 0; j < listItemsPlan.reserveList.size(); j++) {//перебираем все элементы из списка планируемого выпуска продукции
                Plan plan = (Plan) listItemsPlan.reserveList.get(j);//берем j-й элемент из списка планируемого выпуска деталей
                Purchase purchase = new Purchase();//создаем объект планируемой закупки детали
                purchase.setQuarter(plan.getStringWithQuarter());//вычисляем квартал закупки детали с помощью метода getStringWithQuarter
                purchase.setDetail(plan.getDetail());//присваиваем имя объекта плановой закупки такое же как у объекта планового выпуска
                if (plan.equals(reserve)) {//отыскиваем есть ли в списке резерва названия деталей такие же как у объекта планового выпуска
                    if (plan.getAmount() <= reserve.getAmount()) {//если есть и если количество планируемых деталей для выпуска меньше или равно чем резервных на складе
                        purchase.setAmount(0);// присваиваем объекту закупок деталей 0 - т.е. закупки производить не нужно
                        reserveList.get(i).setAmount(reserve.getAmount() - plan.getAmount());//из резервного объекта в списке отнимаем кол-во запланируемых деталей
                    } else {//если резервных деталей меньше чем планируемых к выпуску
                        purchase.setAmount(plan.getAmount() - reserve.getAmount());//присваиваем объекту планируемых закупаемых деталей величину равную количеству планируемых к выпуску деталей - количество деталей на складе
                        purchase.setAmount((int) (purchase.getAmount() + (Math.ceil(purchase.getAmount() * 0.1))));//+10%
                        reserveList.get(i).setAmount(0);//
                    }
                    listItemsPurchase.reserveList.add(purchase);//добавляем созданный объект в список планируемых закупаемых деталей
                } else {

                }
            }
        }
        for (int i = 0; i < listItemsPlan.reserveList.size(); i++) {//добавляем те элементы, которых нет в резерве, но есть в списке выпускаемых деталей
            if (!this.reserveList.contains(listItemsPlan.reserveList.get(i))) {
                Purchase purchase = new Purchase();
                purchase.setQuarter(((Plan) listItemsPlan.reserveList.get(i)).getStringWithQuarter());
                purchase.setDetail(listItemsPlan.reserveList.get(i).getDetail());
                purchase.setAmount((int) (listItemsPlan.reserveList.get(i).getAmount() + (Math.ceil(listItemsPlan.reserveList.get(i).getAmount() * 0.1))));
                listItemsPurchase.reserveList.add(purchase);
            }

        }
        return listItemsPurchase;//возвращаем объект со списком всех закупаемых деталей
    }



    public void writeInFile(String filename) {//метод для записи в файл всех закупаемых деталей
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (Reserve reserve : this.reserveList)
                if(reserve.getAmount()!=0)reserve.writeFile(writer);//записываем только те, количество деталей которых больше 0

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEqualsItems() {//обнуление тех элементов списка, которые повторяются по кварталу и по наименованию детали
        for(int i=0;i<this.reserveList.size();i++){
            Purchase purchase1=(Purchase) reserveList.get(i);
            for (int j=0;j<this.reserveList.size();j++){
                Purchase purchase2=(Purchase)reserveList.get(j);
                if(purchase1.equalsNameAndQuarter(purchase2)&&purchase1.getAmount()!=0&&purchase2.getAmount()!=0&&i!=j){
                    purchase1.setAmount(purchase1.getAmount()+purchase2.getAmount());
                    purchase2.setAmount(0);
                }
            }
        }
    }

}
