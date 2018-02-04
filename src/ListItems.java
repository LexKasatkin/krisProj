import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListItems{
    boolean equalItem = false;
    List<Reserve> reserveList;

    ListItems() {
        reserveList = new ArrayList<Reserve>();
    }

    public void getData(String filename, Class cl) {
        try {
            File file = new File(filename);

            Scanner input = new Scanner(file);


            while (input.hasNextLine()) {
                if (cl == Purchase.class) {
                    Purchase purchase = new Purchase();
                    purchase.getData(input);
                    reserveList.add(purchase);
                } else if (cl == Plan.class) {
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


    public void equalsPurchases() {//метод для выявления деталей с одинаковым именем и кварталом

    }


    public void writeInFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            // запись всей строки
//            String text = "Мама мыла раму, раму мыла мама";
//            writer.write(text);
//            // запись по символам
//            writer.append('\n');
//            writer.append('E');
//
//            writer.flush();
            for (Reserve reserve : this.reserveList) reserve.writeFile(writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEqualsItems() {
        for(int i=0;i<this.reserveList.size();i++){
            Purchase purchase1=(Purchase)reserveList.get(i);
            for(int j=0;j<this.reserveList.size();j++){
                Purchase purchase2=(Purchase)reserveList.get(j);
                if(purchase1.equalsNameAndQuarter(purchase2)&&i!=j){
                    try{
                        this.reserveList.get(i).setAmount(this.reserveList.get(j).getAmount());
                    }catch (Exception ex){
                    }
                    this.reserveList.remove(j);



                }
            }
        }
    }

}
