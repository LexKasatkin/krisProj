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
                } else if (cl == Reserve.class) {
                    Reserve reserve = new Reserve();
                    reserve.getData(input);
                    equalItem = false;
                    for (int i = 0; i < reserveList.size(); i++) {
                        if (reserve.equals(reserveList.get(i))) {
                            reserveList.get(i).setAmount(reserveList.get(i).getAmount() + reserve.getAmount());
                            equalItem = true;
                        }
                    }
                    if (equalItem == false) {
                        reserveList.add(reserve);
                    }
                }
                input.nextLine();

            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ListItems setPurchase(ListItems listItemsPlan) {
        ListItems listItemsPurchase = new ListItems();
        for (int i = 0; i < this.reserveList.size(); i++) {
            Reserve reserve = reserveList.get(i);
            for (int j = 0; j < listItemsPlan.reserveList.size(); j++) {
                Plan plan = (Plan) listItemsPlan.reserveList.get(j);
                Purchase purchase = new Purchase();
                purchase.setQuarter(plan.getStringWithQuarter());
                purchase.setDetail(plan.getDetail());
                if (plan.equals(reserve)) {
                    if (plan.getAmount() <= reserve.getAmount()) {
                        purchase.setAmount(0);
                        reserveList.get(i).setAmount(reserve.getAmount() - plan.getAmount());
                    } else {
                        purchase.setAmount(plan.getAmount() - reserve.getAmount());
                        purchase.setAmount((int) (purchase.getAmount() + (Math.ceil(purchase.getAmount() * 0.1))));
                    }
                    listItemsPurchase.reserveList.add(purchase);
                } else {

                }
            }
        }
        for (int i = 0; i < listItemsPlan.reserveList.size(); i++) {
            if (!this.reserveList.contains(listItemsPlan.reserveList.get(i))) {
                Purchase purchase = new Purchase();
                purchase.setQuarter(((Plan) listItemsPlan.reserveList.get(i)).getStringWithQuarter());
                purchase.setDetail(listItemsPlan.reserveList.get(i).getDetail());
                purchase.setAmount((int) (listItemsPlan.reserveList.get(i).getAmount() + (Math.ceil(listItemsPlan.reserveList.get(i).getAmount() * 0.1))));
                listItemsPurchase.reserveList.add(purchase);
            }

        }
        return listItemsPurchase;
    }



    public void writeInFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (Reserve reserve : this.reserveList)
                if(reserve.getAmount()!=0)reserve.writeFile(writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEqualsItems() {//удаление одинаковых элементов
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
