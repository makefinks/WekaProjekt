import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;

public class AttributeAnalyser {

    private ArrayList<DataObject> data;
    WordCalculation wc=MainApp.wc;

    private final Matcher MATCHER_AVG_SENTENCELENGTH = Pattern
            .compile(".+?(\\.|\\n|\\r|\\?|!|:|;)\\s"
                    , Pattern.CASE_INSENSITIVE)
            .matcher("");
    private final Matcher MATCHER_SPECIAL_CHAR_COUNT = Pattern
            .compile("[^A-Za-z0-9\s]",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_NUMBER_COUNT = Pattern
            .compile("[0-9]",
                    Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_TIME_COUNT = Pattern
            .compile("([0-6][0-9]:[0-6][0-9].[0-9]\\s|((1|0)[0-9]|" +
                    "2[0-4])(:[0-6][0-9]){1,2}\\s|\\s\\d+\\s?(" +
                    "hours|h|minutes|min|seconds|s|milliseconds|ms|nanoseconds|ns)\\s)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_DATE_COUNT = Pattern
            .compile("((?:((0|[1-2])?[0-9]|3(1|0))?\\s(jan(uary)?|feb(ruary)?|mar(ch)?|" +
                            "apr(il)?|may|june?|july?|aug(ust)?|sep(tember)?|oct(ober)?|nov(ember)?|" +
                            "dec(ember)?)\\s(((0|[1-2])?[0-9]|3(1|0)|of)\\s)?([1-2][0-9]{3}|[1-9][0-9])?)|" +
                            "(?:\\s((((0|[1-2])?[0-9]|3(1|0))(\\.|/|-)(0?[1-9]|1[0-2])(\\.|/|-)" +
                            "[1-2][0-9]{3}))\\s)(?:\\s(([1-2][0-9]{3}|[1-9][0-9])(\\.|/|-)(0?[1-9]|1[0-2])" +
                            "(\\.|/|-)((0|[1-2])?[0-9]|3(1|0)))\\s))",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_MONEY_COUNT = Pattern
            .compile("\\$\\d+",
                    Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_PHONE_NR_COUNT = Pattern
            .compile("(\\(\\d{3}\\)|\\d{3})(-|\\s)?\\d{3}-\\d{4}",
                    Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_URL_COUNT = Pattern
            .compile("(https?|ftp)://([a-z0-9]+?[a-z\\.\\-_0-9]+?)(/([a-z0-9]+[a-z\\.\\-_0-9]+))+",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_IP_COUNT = Pattern
            .compile("(\\s(([0-1]?[0-9]?[1-9]|2?[0-5][1-5])(\\.([0-1]?[0-9]?[1-9]|2?[0-5][1-5])){3})\\s)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_STORAGE_UNITS_COUNT = Pattern
            .compile("\\d+\\s?(bytes?|kilobytes?|megabytes?|gigabytes?|terabytes?|kb|mb|gb|tb)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_QUESTION_MARK = Pattern
            .compile("\\w+(?:\\s+\\w+)*\\s*\\?",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
            .matcher("");
    private final Matcher MATCHER_EXCLAMATION_MARK = Pattern
            .compile("\\w+(?:\\s+\\w+)*\\s*\\!",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE)
            .matcher("");
    List<String> fNames = Arrays.asList("Atheism", "Graphics", "Religion", "Space");

    public AttributeAnalyser(List<DataObject> data) throws IOException {
        this.data = (ArrayList<DataObject>) data;
        execute();
    }


    public void execute() throws IOException {
        //wc.init(data);
        //wc.calculate();
        deleteEmptyText();
        data.forEach((object) -> {
            String text = object.getText();
            //object.setAverageSentenceLength(averageSentenceLength(text));
            int sCharCount = countRegexMatches(text, MATCHER_SPECIAL_CHAR_COUNT);
            object.setSpecialCharacterCount(sCharCount);
            object.setPersonalExpression(countPersonalExpression(text));
            object.setNegativeView(countNegativeViews(text));


            double avgSpecialCharacters = avgSpecialCharacters(text, sCharCount);
            object.setAvgSpecialCharacters(avgSpecialCharacters);
            avgSpecialCharacters=avgSpecialCharacters-(avgSpecialCharacters%0.01);

            keyWordCalculation(object);
            object.setCountQuestionMark(countRegexMatches(text, MATCHER_QUESTION_MARK));
            object.setExclamationMark(countRegexMatches(text, MATCHER_EXCLAMATION_MARK));
            object.setNumberCount(countRegexMatches(text, MATCHER_NUMBER_COUNT));
            //email regex inspiration
            // https://stackoverflow.com/questions/201323/how-can-i-validate-an-email-address-using-a-regular-expression
            object.setEmailCount(countRegexMatches(text, MATCHER_AVG_SENTENCELENGTH));
        });


        setKeyWordCount();
    }

    public double averageSentenceLength(String text) {
        MATCHER_AVG_SENTENCELENGTH.reset(text);
        double numberOfMatches = 0;
        double lengthOfText = 0;
        //fuer jeden match (satz) die satzlaenge berechnen
        //und addieren
        while (MATCHER_AVG_SENTENCELENGTH.find()) {
            String sentence = MATCHER_AVG_SENTENCELENGTH.group();
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

    private static int countRegexMatches(String text, Matcher matcher) {
        matcher.reset(text);
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

        String t1=t.toLowerCase();
		String [] textWords=t1.split("\\s");
		ArrayList<String> wordList=new ArrayList();
		wordList.add("i");
		wordList.add("my");
		wordList.add("me");
		wordList.add("our");
		wordList.add("we");
		wordList.add("us");
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

        public static int countNegativeViews(String t) {
            String t1=t.toLowerCase();
            String[] textWords = t1.split("\\s");
            ArrayList<String> nWords = new ArrayList();
            nWords.add("no");
            nWords.add("none");
            nWords.add("not");
            int count = 0;
            for (String word : nWords) {
                for (int i = 0; i < textWords.length; i++) {
                    if (textWords[i].equals(word)) {
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
