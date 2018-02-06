import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Plan  extends Reserve implements Comparable{//Класс для описания плана выпуска продукции
    private Date date;//дата запланированного выпуска

    public Date getDate() {
        return date;
    }

    public void setDate(String date) {//устанавливаем дату в поле объекта класса из строки
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);//формат записи (чтения)
        try {
            this.date = format.parse(date);//парсим строку и записываем в объект
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    Plan(){//конструктор
        this.date=new Date();
    }
    @Override
    void getData(Scanner scanner) {//берем данные из файла и записываем в поле объекта класса
        super.getData(scanner);//записываем название и количество деталей, обращаясь к методу родительского класса
        if(scanner.hasNext()) {
            this.setDate(scanner.next());//записываем дату
        }
    }

    @Override
    public boolean equals(Object obj) {//сравнение деталей по названию происходит с помощью обращения к такому же методу родительского класса
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
    public int compareTo(Object o) {//метод для сортировки списка планируемого выпуска деталей по полю дата
        return this.date.compareTo(((Plan)o).getDate());
    }
}
