import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArffWriter {

    private String pathname;

    public ArffWriter(String pathname){
        this.pathname = pathname;
    }

    public void writeObjects(ArrayList<DataObject> objects) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(pathname));

        writer.write("@relation '20newsgroups'\n\n");

        System.out.println(DataObject.atts.size());
        for(int i = 0; i<DataObject.atts.size(); i++){
            writer.write("@attribute ");
            writer.write(DataObject.atts.get(i).name() + " " + DataObject.atts.get(i).type() + "\n");
        }

        writer.write("\n");

        writer.write("@data\n");

        for(DataObject obj : objects){
            writer.write(obj.getId() + ","
                    + "'" + obj.getText() +"'"          + ","
                    + obj.getGroupId()                  + ","
                    + obj.getAtheismCount()             + ","
                    + obj.getGraphicsCount()            + ","
                    + obj.getSpaceCount()               + ","
                    + obj.getReligionCount()            + ","
                    + obj.getTextLength()               + ","
                    + obj.getAverageSentenceLength()    + ","
                    + obj.getSpecialCharacterCount()    + ","
                    + obj.getNumberCount()              + ","
                    + obj.getEmailCount()               + "\n"
            );
        }

        writer.close();
    }
}
