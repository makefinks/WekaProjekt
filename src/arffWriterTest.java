import java.io.IOException;
import java.util.ArrayList;

public class arffWriterTest {

    public static void main(String[] args) throws IOException {

        ArrayList<DataObject> objets = new ArrayList<>();

        objets.add(new DataObject(1, "Atheismus", 0));
        objets.add(new DataObject(2, "Graphics", 1));
        objets.add(new DataObject(3, "Space", 2));
        objets.add(new DataObject(4, "Religion", 3));

        ArffWriter writer = new ArffWriter("testOutput.arff");

        writer.writeObjects(objets);

    }

}
