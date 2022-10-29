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

        writer.write("@Relation '20newsgroups'\n");

        for(String key : DataObject.atts.keySet()){
            writer.write("@attribute ");
            writer.write(key + " " + DataObject.atts.get(key) + "\n");
        }

        writer.write("@data\n");

        for(DataObject obj : objects){
            writer.write(obj.getId() + ","
                    + obj.getText()             + ","
                    + obj.getGroupId()          + ","
                    + obj.getAtheismCount()     + ","
                    + obj.getGraphicsCount()    + ","
                    + obj.getSpaceCount()       + ","
                    + obj.getReligionCount()    + "\n");
        }

        writer.close();
    }
}
