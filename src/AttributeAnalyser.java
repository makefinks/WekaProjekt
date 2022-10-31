import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttributeAnalyser {

    private ArrayList<DataObject> data;

    public AttributeAnalyser(List<DataObject> data) {
        this.data = (ArrayList<DataObject>) data;
        execute();
    }

    public void checkWordCount() {

    }

    public void execute() {
        data.forEach((object) -> {
            String text = object.getText();
            object.setAverageSentenceLength(averageSentenceLength(text));
            object.setSpecialCharacterCount(specialCharCount(text));
        });
    }

    private double averageSentenceLength(String text) {

        String[] saetze = text.split("/. | /:");
        int count = saetze.length;
        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += saetze[i].length();
        }
        return sum / count;

    }

    private int specialCharCount(String text) {
        Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile("[^A-Za-z0-9\s]");
        Matcher specialCharacterMatcher = SPECIAL_CHARACTER_PATTERN.matcher(text);
        return (int) specialCharacterMatcher.results().count();
    }

    private int anzahlNrCount(String text) {
        Pattern nr_PATTERN = Pattern.compile("[0-9]");
        Matcher nrMatcher = nr_PATTERN.matcher(text);
        return (int) nrMatcher.results().count();
    }

    private void deleteLeerTexte(String text) {
        String inputFileName = text;
        String outputFileName = "NewText.txt";

        try (BufferedReader inputFile = new BufferedReader(new FileReader(inputFileName));
             PrintWriter outputFile = new PrintWriter(new FileWriter(outputFileName))) {

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
}
