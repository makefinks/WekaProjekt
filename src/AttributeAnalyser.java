import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;

public class AttributeAnalyser {

    private ArrayList<DataObject> data;
    public AttributeAnalyser(List<DataObject> data){
        this.data = (ArrayList<DataObject>) data;
        execute();
    }
    public void checkWordCount(){

    }
    public void execute() {
    	data.forEach((object)->{
            String text = object.getText();
    		object.setAverageSentenceLength(averageSentenceLength(text));
            object.setSpecialCharacterCount(countRegexMatches(text, "[^A-Za-z0-9\s]"));
    	});
    }
    
    public static double averageSentenceLength(String text) {
        Matcher matcher = Pattern.compile("(\\s(\\.|!|\\?|:)|\\[\\.{1,3}\\]|(Mr|Ms|Mrs)\\.|(\\.|!|\\?|:)[^\\s\\.\\?!:]|\\d\\.|[^.!?:\\n\\r])+(\\.|!|\\?|:|\\n|\\r)")
                .matcher(text);
        int numberOfMatches = 0;
        int lengthOfText = 0;
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

    private int countRegexMatches(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return (int) matcher.results().count();
    }
    private int anzahlNrCount(String text) {
        Pattern nr_PATTERN = Pattern.compile("[0-9]");
        Matcher nrMatcher = nr_PATTERN.matcher(text);
        return (int) nrMatcher.results().count();
    }

}
