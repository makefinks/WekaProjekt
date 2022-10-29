import java.util.ArrayList;
import java.util.HashMap;

public class DataObject {

    public record Pair(String name, String type){}
    private int id;
    private String text;
    private int groupId;

    private int AtheismCount;

    private int GraphicsCount;

    private int ReligionCount;

    private int SpaceCount;

    private int textLenght;
    private int specialCharacters;

    public static ArrayList<Pair> atts = new ArrayList<>();

    static {
        atts.add(new Pair("id", "numeric"));
        atts.add(new Pair("text", "string"));
        atts.add(new Pair("groupId", "{0,1,2,3}"));
        atts.add(new Pair("atheismCount", "numeric"));
        atts.add(new Pair("graphicsCount", "numeric"));
        atts.add(new Pair("spaceCount", "numeric"));
        atts.add(new Pair("ReligionCount", "numeric"));
    }

    //possible new Attributes -> see Attribute Ideas
    public DataObject(int id, String text, int groupId) {
        this.id = id;
        this.text = text;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
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
                '}';
    }
}
