import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Scanner;

public class Purchase extends Reserve  {//Класс для описания поквартального выпуска продукции
    private String quarter;//квартал - поле класса

    public String getQuarter() {
        return quarter;
    }//возвращаем квартал

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }//устанавливаем в поле класса значение quarter

    Purchase(){//конструктор
        super();//обращаемся к конструктору родительского класса дабы инициализировать поля detail и amount
        this.quarter="";//инициализируем поле квартал
    }

    @Override
    void getData(Scanner scanner) {//берем данные из файла (в ТЗ такого нет кстати)
        super.getData(scanner);
        if(scanner.hasNext()) {
            this.quarter = scanner.next();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }//сравнение объектов класса по названию детали

    @Override
    public void writeFile(Writer writer){//записываем данные объекта в файл
        super.writeFile(writer);//обращаемся к родительскому классу для записи в файл названия детали и количества
        try {
            writer.write(this.quarter+"\n");//записываем квартал в файл
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean equalsNameAndQuarter(Purchase purchase){
        return this.getDetail().equals(purchase.getDetail())&&this.getQuarter().equals(purchase.getQuarter());//сравнение объектов по названию детали и кварталу
    }
}
