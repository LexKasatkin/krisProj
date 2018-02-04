import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Plan  extends Reserve implements Comparable{//Класс для описания плана выпуска продукции
    private Date date;//дата запланированного выпуска

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    Plan(){
        this.date=new Date();
    }
    @Override
    void getData(Scanner scanner) {
        super.getData(scanner);
        if(scanner.hasNext()) {
            this.setDate(scanner.next());
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getStringWithQuarter(){  //получить квартал из запланированной даты
        Date dateQuarter=this.date;
        int quarter = (this.date.getMonth() / 3) + 1;
        Calendar instance = null;
        if(quarter==1){
            quarter=4;
            instance = Calendar.getInstance();
            instance.setTime(this.date); //устанавливаем дату, с которой будет производить операции
            instance.add(Calendar.YEAR, -1);// прибавляем 3 дня к установленной дате
            dateQuarter = instance.getTime(); // получаем измененную дату
            DateFormat dateformat = new SimpleDateFormat("yyyy");
            String year = dateformat.format(dateQuarter);
            return year+"."+quarter;

        }else {
            quarter --;//отнимаем от квартала 1
            DateFormat dateformat = new SimpleDateFormat("yyyy");
            String year = dateformat.format(dateQuarter);
            return year + "." + quarter;//формируем год и квартал для вывода
        }
    }

    @Override
    public int compareTo(Object o) {
        return this.date.compareTo(((Plan)o).getDate());
    }
}
