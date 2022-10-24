import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApp {

    public static void main(String[] args) throws IOException {
            FileParser parser = new FileParser("20newsgroups.arff");


            ArrayList<DataObject> objects = parser.parseFile();

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

        }

    }

