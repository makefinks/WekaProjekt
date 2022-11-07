import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;

public class AttributeAnalyser {

    private ArrayList<DataObject> data;
    List<String> fNames = Arrays.asList("Atheism", "Graphics", "Religion", "Space");

    public AttributeAnalyser(List<DataObject> data) throws IOException {
        this.data = (ArrayList<DataObject>) data;
        execute();
    }


    public void execute() throws IOException {
        data.forEach((object) -> {
            String text = object.getText();
            //object.setAverageSentenceLength(averageSentenceLength(text));
            object.setSpecialCharacterCount(countRegexMatches(text, "[^A-Za-z0-9\s]"));
            object.setNumberCount(countRegexMatches(text, "[0-9]"));
            //email regex inspiration
            // https://stackoverflow.com/questions/201323/how-can-i-validate-an-email-address-using-a-regular-expression
            object.setEmailCount(countRegexMatches(text, "[a-z0-9!#$%&'*+\\/=?^_{|}~\\-]+[\\.a-z0-9!#$%&'*+\\/=?^_{|}~\\-]*@[a-z0-9\\-\\[\\]]+[\\.a-z0-9\\-\\[\\]]*"));
        });
        //setKeyWordCount();
    }

    public double averageSentenceLength(String text) {
        Matcher matcher = Pattern.compile("(\\s(\\.|!|\\?|:)|\\[\\.{1,3}\\]|(Mr|Ms|Mrs)\\.|(\\.|!|\\?|:)[^\\s\\.\\?!:]|\\d\\.|[^.!?:\\n\\r])+(\\.|!|\\?|:|\\n|\\r)",
                Pattern.CASE_INSENSITIVE)
                .matcher(text);
        double numberOfMatches = 0;
        double lengthOfText = 0;
        //fuer jeden match (satz) die satzlaenge berechnen
        //und addieren
        while (matcher.find()) {
            String sentence = matcher.group();
            if (sentence != "") {
                lengthOfText += sentence.length();
                numberOfMatches++;
            }
        }
        return lengthOfText / numberOfMatches;
    }

    private static int countRegexMatches(String text, String regex) {
        Matcher matcher = Pattern.compile(regex,
            Pattern.CASE_INSENSITIVE |
                Pattern.MULTILINE)
                .matcher(text);
        return (int) matcher.results().count();
    }

    /*private int anzahlNrCount(String text) {
        Pattern nr_PATTERN = Pattern.compile("[0-9]");
        Matcher nrMatcher = nr_PATTERN.matcher(text);
        return (int) nrMatcher.results().count();
    }*/

    private void deleteLeerTexte(String text) {
        String inputFileName = text;
        String outputFileName = "NewText.txt";

        try (BufferedReader inputFile = new BufferedReader(new FileReader(inputFileName));
             PrintWriter outputFile = new PrintWriter(new FileWriter(outputFileName)))
        {

            String lineOfText = null;
            while ((lineOfText = inputFile.readLine()) != null) {
                lineOfText = lineOfText.trim();
                if (!lineOfText.isEmpty()) {
                    outputFile.print(lineOfText);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double countAverageWordLength(String text){
        int count = 0;
        double sum = 0;
        String[] words = text.split("\\s+");
        for (String word : words) {
           double wordLength = word.length();
           sum += wordLength;
           count++;
        }
        double average = 0;
        if (count > 0) {
            average = sum / count;
        }
        return average;
    }

    private void setKeyWordCount() throws IOException {

        /*
        ------------------------------------------------
        Zählt die vorkommen von Schlüsselwörtern in den einzelnen Texten
        ------------------------------------------------
        */

        for (DataObject obj : data) {
            System.out.println("Instance: " + data.indexOf(obj));
            String text = obj.getText();
            int count = 0;
            for (String name : fNames) {
                BufferedReader in = new BufferedReader(new FileReader("WordList" + name));
                String line;
                while ((line = in.readLine()) != null) {
                    String regex = ".*\\b"+line+"\\b.*";
                    if(line.matches(".*[^a-zA-Z]+.*")){
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
        System.out.println("Finished creating KeyWordCount");
    }
}
