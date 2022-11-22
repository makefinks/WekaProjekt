import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;

public class AttributeAnalyser {

    private ArrayList<DataObject> data;
    WordCalculation wc=new WordCalculation();

    List<String> fNames = Arrays.asList("Atheism", "Graphics", "Religion", "Space");

    public AttributeAnalyser(List<DataObject> data) throws IOException {
        this.data = (ArrayList<DataObject>) data;
        execute();
    }


    public void execute() throws IOException {
        wc.init(data);
        wc.calculate();
        data.forEach((object) -> {
            String text = object.getText();
            //object.setAverageSentenceLength(averageSentenceLength(text));
            int sCharCount = countRegexMatches(text, "[^A-Za-z0-9\s]");
            object.setSpecialCharacterCount(sCharCount);
            object.setPersonalExpression(countPersonalExpression(text));

            double avgSpecialCharacters = avgSpecialCharacters(text, sCharCount);
            object.setAvgSpecialCharacters(avgSpecialCharacters);
            avgSpecialCharacters=avgSpecialCharacters-(avgSpecialCharacters%0.01);

            keyWordCalculation(object);
            object.setCountQuestionMark(countRegexMatches(text,"\\w+(?:\\s+\\w+)*\\s*\\?"));
            object.setExclamationMark(countRegexMatches(text,"\\w+(?:\\s+\\w+)*\\s*\\!"));

            object.setNumberCount(countRegexMatches(text, "[0-9]"));
            //email regex inspiration
            // https://stackoverflow.com/questions/201323/how-can-i-validate-an-email-address-using-a-regular-expression
            object.setEmailCount(countRegexMatches(text, "[a-z0-9!#$%&'*+\\/=?^_{|}~\\-]+[\\.a-z0-9!#$%&'*+\\/=?^_{|}~\\-]*@[a-z0-9\\-\\[\\]]+[\\.a-z0-9\\-\\[\\]]*"));
        });


        setKeyWordCount();
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
            if (!sentence.equals("")) {
                lengthOfText += sentence.length();
                numberOfMatches++;
            }
        }
        return lengthOfText / numberOfMatches;
    }

    private static double avgSpecialCharacters(String text, int charCount){
        return (double) text.length() / charCount;
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

    private void deleteEmptyText(){
       for (DataObject obj: data){
           if(obj.getText().isEmpty()){
               data.remove(obj);
           }
       }
    }


public static int countPersonalExpression(String t) {
		String [] textWords=t.split("\\s");
		ArrayList<String> wordList=new ArrayList();
		wordList.add("I");
		wordList.add("my");
		wordList.add("me");
		wordList.add("our");
		wordList.add("we");
		wordList.add("us");
		wordList.add("My");
		wordList.add("Me");
		wordList.add("Our");
		wordList.add("We");
		wordList.add("Us");
        wordList.add("ours");
		wordList.add("mine");
        wordList.add("myself");
		wordList.add("ourselves");
		int count=0;
		for(String word:wordList) {
			for(int i=0;i<textWords.length;i++) {
				if(textWords[i].equals(word)) {
					count++;
				}
			}
		}
		return count;
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
    private void keyWordCalculation(DataObject obj){
        int count0=0,count1=0,count2=0,count3=0;
        String[] words=obj.getText().toLowerCase().split("\\W+");
        for(int i=0;i<words.length;i++){
            String word=words[i];
            if(wc.getListOfGroup(0).contains(word)){
                count0++;
            }
            if(wc.getListOfGroup(1).contains(word)){
                count1++;
            }
            if(wc.getListOfGroup(2).contains(word)){
                count2++;
            }
            if(wc.getListOfGroup(3).contains(word)){
                count3++;
            }
        }
        obj.setAtheismCalCount(count0);
        obj.setGraphicsCalCount(count1);
        obj.setSpaceCalCount(count2);
        obj.setReligionCalCount(count3);
        System.out.println("zaehlung");
    }
}
