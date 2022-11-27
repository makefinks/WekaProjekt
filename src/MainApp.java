import javax.xml.crypto.Data;
import java.io.*;
import java.nio.Buffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApp {

    static WordCalculation wc=new WordCalculation();
    public static void main(String[] args) throws IOException {

        List<String> fNames = Arrays.asList("Atheism", "Graphics", "Religion", "Space");

        FileParser parser = new FileParser("traindevtest/train.arff");
        ArrayList<DataObject> objects = parser.parseFile();
        wc.init(objects);
        wc.calculate();
        // User choice: Generate Wordlist for 4 topics, or skip
        Scanner userInput = new Scanner(System.in);
        String yesNo = new String("pending");
        System.out.println("Generate word lists ? Y to generate, any key to skip [Y/N]");
        yesNo = userInput.nextLine();
        if(yesNo.equals("Y") | yesNo.equals("y")){
            parser.createWordLists(10);
        }
        
        AttributeAnalyser analyser = new AttributeAnalyser(objects);
        ArffWriter writer = new ArffWriter("arffOutputTraining.arff");
        writer.writeObjects(objects);


        FileParser testParser = new FileParser("traindevtest/test.arff");
        ArrayList<DataObject> testObjects = testParser.parseFile();
        AttributeAnalyser testAnalyser = new AttributeAnalyser(testObjects);
        ArffWriter testWriter = new ArffWriter("arffOutputTesting.arff");
        testWriter.writeObjects(testObjects);


/*
        parser.writeReadableFile("readable");
        parser.writeSortedReadableFile("sorted");
        parser.writeGroupToFile(0, "group0");
        parser.writeGroupToFile(1, "group1");
        parser.writeGroupToFile(2, "group2");
        parser.writeGroupToFile(3, "group3");
        parser.writePureTextFromGroupTofile(0, "group0rawText");
        parser.writePureTextFromGroupTofile(1, "group1rawText");
        parser.writePureTextFromGroupTofile(2, "group2rawText");
        parser.writePureTextFromGroupTofile(3, "group3rawText");





         ArrayList<ArrayList<String>> files = new ArrayList<>();
         for(String name : fNames){
             BufferedReader in = new BufferedReader(new FileReader("src/FrequencyWords"+name));
             String line;
             ArrayList<String> lines = new ArrayList<>();
             while((line = in.readLine()) != null){
                 lines.add(line);
             }
             files.add(lines);
         }
         //delete common words:
        System.out.println("Before");
        for(ArrayList<String> list : files){
            System.out.println(list.size());
        }
        ArrayList<ArrayList<String>> originalFiles = new ArrayList<>();
        files.forEach(x -> originalFiles.add((ArrayList<String>) x.clone()));
        for(int i = 0; i<files.size(); i++){
            for(int b = 0; b<originalFiles.get(i).size(); b++){
                String s = originalFiles.get(i).get(b);
                for(int x = 0; x<files.size(); x++){
                    if(i!=x){
                        if(files.get(x).contains(s)){
                            files.get(i).remove(s);
                            files.get(x).remove(s);
                        }
                    }
                }
            }
        }

        System.out.println();
        System.out.println("After");
        for(ArrayList<String> list : files){
            System.out.println(list.size());
        }

        for(int i = 0; i<4; i++){
            BufferedWriter out = new BufferedWriter(new FileWriter("src/WordList"+fNames.get(i)));
                ArrayList<String> list = files.get(i);
                for(int x = 0; x<list.size(); x++){
                    out.write(list.get(x)+"\n");
                }
        }






            BufferedReader reader = new BufferedReader(new FileReader("src/FrequencyWordsSpace"));
            ArrayList<String> lines = new ArrayList<>();

            String line;

            while((line = reader.readLine()) != null){
                lines.add(line);
            }

            for(int i = 0; i<lines.size(); i++){
                    String newstring = lines.get(i).replaceAll(": [0-9]*", "");
                    lines.set(i, newstring);
            }

            System.out.println(lines);

            BufferedWriter writer1 = new BufferedWriter(new FileWriter("src/FrequencyWordsSpace"));
            for(String l : lines){
                    writer1.write(l + "\n");
            }




       // ------------------------------------------------
       // Zählt die vorkommen von Schlüsselwörtern in den einzelnen Texten
       // ------------------------------------------------
        for (DataObject obj : objects) {
            System.out.println("Instance: " + objects.indexOf(obj));
            String text = obj.getText();
            int count = 0;
            for (String name : fNames) {
                BufferedReader in = new BufferedReader(new FileReader("src/WordList" + name));
                String line1;
                while ((line1 = in.readLine()) != null) {
                    String regex = ".*\\b"+line+"\\b.*";
                    if(line1.matches(".*[^a-zA-Z]+.*")){
                        //skip the word
                       // System.out.println("skipped: " + line);
                    }
                    else{
                        if(text.matches(regex)){
                            count++;
                        }
                    }
                }
                switch (name) {
                    case "Atheism":
                        obj.setAtheismCount(count);
                        break;
                    case "Graphics":
                        obj.setGraphicsCount(count);
                        break;
                    case "Religion":
                        obj.setReligionCount(count);
                        break;
                    case "Space":
                        obj.setSpaceCount(count);
                        break;
                }
                count=0;
            }
        }

        System.out.println("printing");
        ArffWriter fwriter = new ArffWriter("arfoutput.arff");
        fwriter.writeObjects(objects);
*/
    }


}

