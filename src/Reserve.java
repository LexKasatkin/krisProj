import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Reserve {
    private String detail;
    private int amount;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    Reserve(){
        this.detail=new String();
        this.amount=0;
    }

    void getData(Scanner scanner){
        if(scanner.hasNext()){
            this.detail=scanner.next();
        }
        if(scanner.hasNextInt()){
            this.amount=scanner.nextInt();
        }
    }

    @Override
    public boolean equals(Object obj) {
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
