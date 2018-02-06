import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Reserve {//класс для описания резервных деталей
    private String detail;//название детали
    private int amount;//количество

    public String getDetail() {
        return detail;
    }//возвращаем название датели

    public void setDetail(String detail) {
        this.detail = detail;
    }//присваиваем полю объекта название детали

    public int getAmount() {
        return amount;
    }//возвращаем количество деталей на складе

    public void setAmount(int amount) {
        this.amount = amount;
    }//устанавливаем количество

    Reserve(){//конструктор
        this.detail=new String();//создаем в поле название детали новую строку
        this.amount=0;//обнуляем количество деталей
    }

    void getData(Scanner scanner){//метод для чтения данных из файла и запись этих данных в объект
        if(scanner.hasNext()){
            this.detail=scanner.next();//присваиваем названию детали элемент строки из файла
        }
        if(scanner.hasNextInt()){
            this.amount=scanner.nextInt();//присваиваем количеству деталей элемент строки из файла, который является целым числом
        }
    }

    @Override
    public boolean equals(Object obj) {//метод для сравнения двух объектов (происходит по названию детали)
        return ((Reserve)obj).getDetail().equals(this.getDetail());
    }

    public void writeFile(Writer writer){
        try {
            writer.write(this.detail+" "+this.amount+" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
