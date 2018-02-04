import java.util.Collections;
import java.util.Comparator;

public class Main{
    static public void main(String[] args){
        ListItems listItemsReserve=new ListItems();
        listItemsReserve.getData("/home/lex/IdeaProjects/krisProj/src/reserve.txt", Reserve.class);

        ListItems listItemsPlan=new ListItems();
        listItemsPlan.getData("/home/lex/IdeaProjects/krisProj/src/input.txt", Plan.class);
        Collections.sort(listItemsPlan.reserveList, new Comparator<Reserve>() {
            @Override
            public int compare(Reserve o1, Reserve o2) {
                return ((Plan)o1).getDate().compareTo(((Plan)o2).getDate());
            }
        });
        ListItems listItemsPurchase=new ListItems();
        listItemsPurchase=listItemsReserve.setPurchase(listItemsPlan);
        if(listItemsPurchase.reserveList.size()!=0){
                        listItemsPurchase.deleteEqualsItems();
            listItemsPurchase.writeInFile("/home/lex/IdeaProjects/krisProj/src/out.txt");
        }



    }
}