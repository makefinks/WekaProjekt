import javax.xml.crypto.Data;
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

        writer.write("@relation '20newsgroups'\n\n");

        System.out.println(DataObject.atts.size());
        for(int i = 0; i<DataObject.atts.size(); i++){
            writer.write("@attribute ");
            writer.write(DataObject.atts.get(i).name() + " " + DataObject.atts.get(i).type() + "\n");
        }

        writer.write("\n");

        writer.write("@data\n");

        for(DataObject obj : objects){
            String group = switch (obj.getGroupId()) {
                case 0 -> "Atheism";
                case 1 -> "Graphics";
                case 2 -> "Space";
                case 3 -> "Religion";
                default -> "";
            };
            writer.write(
                    + obj.getAtheismCount()         + ","
                    + obj.getGraphicsCount()            + ","
                    + obj.getSpaceCount()               + ","
                    + obj.getReligionCount()            + ","
                    + obj.getTextLength()               + ","
                    + obj.getAverageSentenceLength()    + ","
                    + obj.getCountQuestionMark()        + ","
                    + obj.getPersonalExpression()       + ","
                    + obj.getExclamationMark()          + ","
                    + obj.getNegativeView()             + ","
                    + obj.getSpecialCharacterCount()    + ","
                    + obj.getNumberCount()              + ","
                    + obj.getEmailCount()               + ","
                    + obj.getAvgSpecialCharacters()     + ","

                    + obj.getAtheismCalCount()          + ","
                    + obj.getGraphicsCalCount()         + ","
                    + obj.getSpaceCalCount()            + ","
                    + obj.getReligionCalCount()         + ","

                    + obj.getDateCount()                + ","
                    + obj.getTimeCount()                + ","
                    + obj.getMoneyCount()               + ","
                    + obj.getPhoneNumberCount()         + ","
                    + obj.getUrlCount()                 + ","
                    + obj.getIpCount()                  + ","
                    + obj.getStorageUnitsCount()        + ","
                    + obj.getCalcSymbolsCount()         + ","
                    + obj.getUnitsCount()               + ","
                    + obj.getAvgUmlauts()             + ","
                    + obj.getAvgCapital()             + ","
                    + obj.getAvgLowerCase()           + ","
                    + obj.getAvgCommonLetters()       + ","
                    + obj.getAvgLeerzeichen()       + ","
                    + group                             + "\n"

            );
        }

        writer.close();
    }
}
