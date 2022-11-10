import javax.xml.crypto.Data;
import java.io.*;
import java.nio.Buffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    private String filePath;
    ArrayList<DataObject> parsedObjects;
    List<String> fNames;

    public FileParser(String filepath) {
        filePath = filepath;
        fNames = Arrays.asList("Atheism", "Graphics", "Space", "Religion");
    }

    public ArrayList<DataObject> parseFile() throws IOException {

        parsedObjects = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String l;
        while((l = reader.readLine()) != null){
            if(l.equals("@data")){
                break;
            }
        }

        /*
        String line;
        while((line = reader.readLine()) != null){
            int firstComma = 0;
            int lastComma = line.length()-2;
            for(int i = 0; i<line.length(); i++){
                if(line.charAt(i) == ','){
                    firstComma = i;
                    break;
                }
            }
            int id = Integer.parseInt(line.substring(0, firstComma));
            int groupId = Integer.parseInt(line.substring(lastComma, line.length()));
            String text = line.substring(firstComma+1, lastComma);
            parsedObjects.add(new DataObject(id, text, groupId));
         */

        String line;
        while((line = reader.readLine()) != null){
            int id = Integer.parseInt(line.substring(0, line.indexOf(",")));

            int firstComma = line.indexOf(",");
            int lastComma = line.lastIndexOf(",");
            if(lastComma - firstComma <= 2){
                continue;
            }

            String text = line.substring(firstComma+2, lastComma-1);
            int groupId = Integer.parseInt(line.substring(line.lastIndexOf(",")+1, line.length()));

            if(text.isEmpty()) {
                continue;
            }
            //wenn ein text nicht mindestens ein Buchstabe hat
            //wird er nicht hinzugefuegt. Kann man vlt. verbessern:
            //z.B text braucht mehrere Buchstaben oder Woerter
            Matcher matcher = Pattern
                    .compile("[a-z]", Pattern.CASE_INSENSITIVE)
                    .matcher(text);
            if (!matcher.find()) {
                continue;
            }
            parsedObjects.add(new DataObject(id, text, groupId));
        }

        return parsedObjects;

        /*
        parsedObjects = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder string = new StringBuilder();

        String line;
        while((line = reader.readLine()) != null){
            string.append(line);
        }

        /*
        ArrayList<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\d*,'.*?',[0-3])");
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
                text += split[x] + ",";
            }
            if (text.matches("(\s+|)")) continue;
            text = text.replace("'", "");
            int groupid = Integer.parseInt(split[split.length-1]);

            DataObject object = new DataObject(id, text, groupid);
            splitResults.add(object);

        }
        parsedObjects = splitResults;
        return splitResults;
         */
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

        //sort dataobjec    ts by groupId
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

    public void createWordLists(int minVorkommen) throws IOException {

        assert parsedObjects != null;

        for(int groupid = 0; groupid<= 3; groupid++){
            ArrayList<String> allWords = new ArrayList<>();
            for(DataObject obj : parsedObjects){
                if(obj.getGroupId() == groupid){
                String objText = obj.getText();
                allWords.addAll(List.of(objText.split(" ")));
                }
            }

            allWords.removeIf(""::equals);

            HashMap<String, Integer> countMap = new HashMap<>();
            for(String s : allWords){
                if(countMap.containsKey(s)){
                    countMap.put(s, countMap.get(s)+1);
                }
                else{
                    countMap.put(s, 1);
                }
            }

            //print the words to a file respecting the minAmount parameter
            File file = new File("WordList" + fNames.get(groupid));
            BufferedWriter out = new BufferedWriter(new FileWriter(file.getPath()));

            for(String entry : countMap.keySet()){
                int entryCount = countMap.get(entry);
                if(entryCount >= minVorkommen && entry.length() > 2){
                    out.write(entry + "\n");
                }
            }
            out.close();

        }

    }

}
