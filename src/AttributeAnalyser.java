import java.util.ArrayList;
import java.util.List;

public class AttributeAnalyser {

    private ArrayList<DataObject> data;
    public AttributeAnalyser(List<DataObject> data){
        this.data = (ArrayList<DataObject>) data;
    }
    public void checkWordCount(){

    }
    public void aveSentenceLenght() {
    	data.forEach((d)->{
    		durchschnittlicheSatzlaenge(d.getText());
    	});
    }
    
    private void durchschnittlicheSatzlaenge(String text) {
    	
    	String[] saetze=text.split(".|:");
    	int count=saetze.length;
    	int sum=0;
    	for(int i=0;i<count;i++) {
    		sum+=saetze[i].length();
    	}
    	double avg=sum/count;
    	
    }
}
