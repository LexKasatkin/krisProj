import java.util.Collections;
import java.util.Comparator;

public class Main{
    static public void main(String[] args){
        ListItems listItemsReserve=new ListItems();//создаем список резервных объектов
        listItemsReserve.getData("/home/lex/IdeaProjects/krisProj/src/reserve.txt", Reserve.class);//заполняем список данными из файла

        ListItems listItemsPlan=new ListItems();//создаем список планируемых к выпуску деталей
        listItemsPlan.getData("/home/lex/IdeaProjects/krisProj/src/input.txt", Plan.class);// заполняем данными из файла
        Collections.sort(listItemsPlan.reserveList, new Comparator<Reserve>() {//сортируем список по дате
            @Override
            public int compare(Reserve o1, Reserve o2) {
                return ((Plan)o1).getDate().compareTo(((Plan)o2).getDate());
            }
        });
        ListItems listItemsPurchase=new ListItems();//создаем список закупаемых деталей
        listItemsPurchase=listItemsReserve.setPurchase(listItemsPlan);//обращаемся к методу setPurcase, с помощью которого заполняем список закупаемых деталей
        if(listItemsPurchase.reserveList.size()!=0){
                        listItemsPurchase.deleteEqualsItems();//обнуляем все повторяющиеся элементы
            listItemsPurchase.writeInFile("/home/lex/IdeaProjects/krisProj/src/out.txt");//записываем объект со списком в файл
        }



    }
}