import java.util.Arrays;

public class TestSplit {


    public static void main(String[] args) {

        String text = "Hallo, wie gehts dir, fragte er stutzend.";

        String[] split = text.split(",");

        for(int i = 0; i<split.length; i++){
            System.out.print(split[i] + ",");
        }

    }
}
