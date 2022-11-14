import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WordCalculation {

    HashMap<Integer, HashMap<String,Integer>> allWords = new HashMap<>();
    HashMap<Integer, ArrayList<String>> wordlist = new HashMap<>();
    HashMap<String,Integer> mapOfAllWords=new HashMap<>();


    public WordCalculation(){
        allWords.put(0,new HashMap<>());
        allWords.put(1,new HashMap<>());
        allWords.put(2,new HashMap<>());
        allWords.put(3,new HashMap<>());
        wordlist.put(0,new ArrayList<>());
        wordlist.put(1,new ArrayList<>());
        wordlist.put(2,new ArrayList<>());
        wordlist.put(3,new ArrayList<>());
    }


    public void init(ArrayList<DataObject> texte){

        texte.forEach((ob)-> {
            String text= ob.getText();
            String[] words=text.split(" ");
            for(String word:words){
                HashMap group= allWords.get(ob.getGroupId());
               if(group.containsKey(word)) {
                   group.put(word, (int) group.get(word) + 1);
               }else{
                   group.put(word,1);
               }

               if(mapOfAllWords.containsKey(word)){
                   mapOfAllWords.put(word, (int) mapOfAllWords.get(word) + 1);
               }else{
                   mapOfAllWords.put(word,1);
               }
            }
        } );
    }
    public List<String> getListOfGroup(int groupID){
        return wordlist.get(groupID);
    }
    public void calculate(){
        int minimalCount=10;
        double percentValue=0.4;
        Iterator i=mapOfAllWords.keySet().iterator();
        while(i.hasNext()){
            String word=(String)i.next();
            int g1=0,g2=0,g3=0,g0=0;
            double p0,p1,p2,p3;
            int sum=mapOfAllWords.get(word);
            if(sum<=minimalCount){
                if(allWords.get(0).containsKey(word)){
                    g0=allWords.get(0).get(word);
                }
                if(allWords.get(1).containsKey(word)){
                    g1=allWords.get(1).get(word);
                }
                if(allWords.get(2).containsKey(word)){
                    g2=allWords.get(2).get(word);
                }
                if(allWords.get(3).containsKey(word)){
                    g3=allWords.get(3).get(word);
                }


                p0=g0/sum;
                p1=g1/sum;
                p2=g2/sum;
                p3=g3/sum;
                if(p0>percentValue){
                    wordlist.get(0).add(word);
                }
                if(p1>percentValue){
                    wordlist.get(1).add(word);

                }if(p2>percentValue){
                    wordlist.get(2).add(word);

                }if(p3>percentValue){
                    wordlist.get(3).add(word);

                }
            }
        }
    }
}
