import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    private String filePath;
    ArrayList<DataObject> parsedObjects;

    public FileParser(String filepath) {
        filePath = filepath;
    }

    public ArrayList<DataObject> parseFile() throws IOException {

        parsedObjects = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder string = new StringBuilder();

        String line;
        while((line = reader.readLine()) != null){
            string.append(line);
        }

        ArrayList<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d,'.*?',[0-3])");
        Matcher matcher = pattern.matcher(string);

        while(matcher.find()){
            matches.add(matcher.group(1));
        }

        ArrayList<DataObject> splitResults = new ArrayList<>();
        for(int i = 0; i<matches.size(); i++){
            String[] split = matches.get(i).split(",");
            int id = Integer.parseInt(split[0]);
            String text = "";
            for(int x = 1; x<split.length-2; x++){
                text += split[x];
            }
            int groupid = Integer.parseInt(split[split.length-1]);

            DataObject object = new DataObject(id, text, groupid);
            splitResults.add(object);

        }
        parsedObjects = splitResults;
        return splitResults;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void writeReadableFile(String path) throws IOException {
        File file = new File(path);
        assert parsedObjects != null;

        BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath()));
        for(DataObject o : parsedObjects){

            bw.write(String.format("ID: %2d", o.getId()) + "\n");
            bw.write(String.format("Text: %5s", o.getText()) + "\n");
            bw.write(String.format("GroupID: %2d", o.getGroupId()) + "\n");
            bw.write("-".repeat(100) + "\n");
        }

    }

    public void writeSortedReadableFile(String path) throws IOException {
        File file = new File(path);
        assert parsedObjects != null;

        BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath()));

        //sort dataobjects by groupId
        parsedObjects.sort((x, y) -> Integer.compare(x.getGroupId(), y.getGroupId()));

        for(DataObject o : parsedObjects){

            bw.write(String.format("ID: %2d", o.getId()) + "\n");
            bw.write(String.format("Text: %5s", o.getText()) + "\n");
            bw.write(String.format("GroupID: %2d", o.getGroupId()) + "\n");
            bw.write("-".repeat(100) + "\n");
        }
    }
    public void writeGroupToFile(int groupID, String path) throws IOException {
        assert groupID > 0 && groupID <=4;

        File file = new File(path);
        assert parsedObjects != null;

        BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath()));

        //sort dataobjects by groupId
        parsedObjects.sort((x, y) -> Integer.compare(x.getGroupId(), y.getGroupId()));

        for(DataObject o : parsedObjects){

            if(o.getGroupId() == groupID){
                bw.write(String.format("ID: %2d", o.getId()) + "\n");
                bw.write(String.format("Text: %5s", o.getText()) + "\n");
                bw.write(String.format("GroupID: %2d", o.getGroupId()) + "\n");
                bw.write("-".repeat(100) + "\n");
            }
        }

    }

    public void writePureTextFromGroupTofile(int groupid, String path) throws IOException {
        assert groupid > 0 && groupid <=4;

        File file = new File(path);
        assert parsedObjects != null;

        BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath()));

        for(DataObject o : parsedObjects){

            if(o.getGroupId() == groupid){
                bw.write(o.getText() + "\n");
            }
        }
    }

}
