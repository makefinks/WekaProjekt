import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataObject {

    public record Pair(String name, String type){}
    private int id;
    private String text;
    private int groupId;
    private int AtheismCount;

    private int GraphicsCount;

    private int ReligionCount;

    private int SpaceCount;

    private int textLength;

    private double averageSentenceLength;

    private long specialCharacterCount;

    private int numberCount;

    private int emailCount;

    private double avgSpecialCharacters;


    private int AtheismCalCount;

    private int GraphicsCalCount;

    private int ReligionCalCount;

    private int SpaceCalCount;

    public static ArrayList<Pair> atts = new ArrayList<>();

    static {
        atts.add(new Pair("id", "numeric"));
        atts.add(new Pair("text", "string"));
        atts.add(new Pair("atheismCount", "numeric"));
        atts.add(new Pair("graphicsCount", "numeric"));
        atts.add(new Pair("spaceCount", "numeric"));
        atts.add(new Pair("religionCount", "numeric"));
        atts.add(new Pair("textLength", "numeric"));
        atts.add(new Pair("averageSentenceLength", "numeric"));
        atts.add(new Pair("specialCharacterCount", "numeric"));
        atts.add(new Pair("numberCount", "numeric"));
        atts.add(new Pair("emailCount", "numeric"));
        atts.add(new Pair("atheismCalCount","numeric"));
        atts.add(new Pair("graphicsCalCount","numeric"));
        atts.add(new Pair("spaceCalCount","numeric"));
        atts.add(new Pair("religionCalCount","numeric"));

        atts.add(new Pair("groupId", "{'Atheism', 'Graphics', 'Space', 'Religion'}"));
        atts.add(new Pair("avgSpecialCharacters", "numeric"));
    }

    //possible new Attributes -> see Attribute Ideas
    public DataObject(int id, String text, int groupId) {
        this.id = id;
        this.text = text;
        this.groupId = groupId;
        textLength = text.length();
    }

    public int getId() {
        return id;
    }
    public void setTextLength(int textLength) {
        this.textLength = textLength;
    }

    public void setAverageSentenceLength(double averageSentenceLength) {
        this.averageSentenceLength = averageSentenceLength;
    }

    public void setSpecialCharacterCount(long specialCharacterCount) {
        this.specialCharacterCount = specialCharacterCount;
    }

    public int getTextLength() {
        return textLength;
    }

    public long getSpecialCharacterCount() {
        return specialCharacterCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getAtheismCount() {
        return AtheismCount;
    }

    public void setAtheismCount(int atheismCount) {
        AtheismCount = atheismCount;
    }

    public int getGraphicsCount() {
        return GraphicsCount;
    }

    public void setGraphicsCount(int graphicsCount) {
        GraphicsCount = graphicsCount;
    }

    public int getReligionCount() {
        return ReligionCount;
    }

    public void setReligionCount(int religionCount) {
        ReligionCount = religionCount;
    }

    public int getSpaceCount() {
        return SpaceCount;
    }

    public void setSpaceCount(int spaceCount) {
        SpaceCount = spaceCount;
    }

    public double getAverageSentenceLength() {
        return averageSentenceLength;
    }

    public int getNumberCount() {
        return numberCount;
    }

    public void setNumberCount(int numberCount) {
        this.numberCount = numberCount;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(int emailCount) {
        this.emailCount = emailCount;
    }

    public void setAvgSpecialCharacters(double avgSpecialCharacters) {
        this.avgSpecialCharacters = avgSpecialCharacters;
    }

    public double getAvgSpecialCharacters() {
        return avgSpecialCharacters;
    }
    public int getAtheismCalCount() {
        return AtheismCalCount;
    }

    public void setAtheismCalCount(int atheismCalCount) {
        AtheismCalCount = atheismCalCount;
    }

    public int getGraphicsCalCount() {
        return GraphicsCalCount;
    }

    public void setGraphicsCalCount(int graphicsCalCount) {
        GraphicsCalCount = graphicsCalCount;
    }

    public int getReligionCalCount() {
        return ReligionCalCount;
    }

    public void setReligionCalCount(int religionCalCount) {
        ReligionCalCount = religionCalCount;
    }

    public int getSpaceCalCount() {
        return SpaceCalCount;
    }

    public void setSpaceCalCount(int spaceCalCount) {
        SpaceCalCount = spaceCalCount;
    }


    @Override
    public String toString() {
        return "DataObject{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", groupId=" + groupId +
                ", AtheismCount=" + AtheismCount +
                ", GraphicsCount=" + GraphicsCount +
                ", ReligionCount=" + ReligionCount +
                ", SpaceCount=" + SpaceCount +
                ", textLength=" + textLength +
                ", averageSentenceLength=" + averageSentenceLength +
                ", specialCharacterCount=" + specialCharacterCount +
                ", numberCount=" + numberCount +
                ", emailCount=" + emailCount +
                ", atheismCalCount=" +this.AtheismCalCount +
                ", graphicsCalCount=" +this.GraphicsCalCount +
                ", spaceCalCount=" +this.SpaceCalCount +
                ", reigionCalCount=" +this.ReligionCalCount +
                '}';
    }
}
