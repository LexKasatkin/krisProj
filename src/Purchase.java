import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Purchase extends Reserve{//Класс для описания поквартального выпуска продукции
    private String quarter;

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    Purchase(){
        super();
        this.quarter="";
    }

    @Override
    void getData(Scanner scanner) {
        super.getData(scanner);
        if(scanner.hasNext()) {
            this.quarter = scanner.next();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void writeFile(Writer writer){
        super.writeFile(writer);
        try {
            writer.write(this.quarter+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
