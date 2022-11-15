import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WordCalculation {

    HashMap<Integer, HashMap<String,Integer>> allWordsCountByGroup = new HashMap<>();
    HashMap<Integer, ArrayList<String>> wordlistOfGroup = new HashMap<>();
    HashMap<String,Integer> allWordsCount =new HashMap<>();


    public WordCalculation(){
        allWordsCountByGroup.put(0,new HashMap<>());
        allWordsCountByGroup.put(1,new HashMap<>());
        allWordsCountByGroup.put(2,new HashMap<>());
        allWordsCountByGroup.put(3,new HashMap<>());
        wordlistOfGroup.put(0,new ArrayList<>());
        wordlistOfGroup.put(1,new ArrayList<>());
        wordlistOfGroup.put(2,new ArrayList<>());
        wordlistOfGroup.put(3,new ArrayList<>());
    }


    public void init(ArrayList<DataObject> texte){

        texte.forEach((ob)-> {
            String text= ob.getText();
            String[] words=text.split(" ");
            for(String word:words){
                HashMap group= allWordsCountByGroup.get(ob.getGroupId());
               if(group.containsKey(word)) {
                   group.put(word, (int) group.get(word) + 1);
               }else{
                   group.put(word,1);
               }

               if(allWordsCount.containsKey(word)){
                   allWordsCount.put(word, (int) allWordsCount.get(word) + 1);
               }else{
                   allWordsCount.put(word,1);
               }
            }
        } );
    }
    public List<String> getListOfGroup(int groupID){
        return wordlistOfGroup.get(groupID);
    }
    public void calculate(){
        int minimalCount=10;
        double percentValue=0.4;
        Iterator i= allWordsCount.keySet().iterator();
        while(i.hasNext()){
            String word=(String)i.next();

            int g1=0,g2=0,g3=0,g0=0;
            double p0,p1,p2,p3;
            int sum= allWordsCount.get(word);
            if(sum<=minimalCount){

                if(allWordsCountByGroup.get(0).containsKey(word)){
                    g0= allWordsCountByGroup.get(0).get(word);
                }
                if(allWordsCountByGroup.get(1).containsKey(word)){
                    g1= allWordsCountByGroup.get(1).get(word);
                }
                if(allWordsCountByGroup.get(2).containsKey(word)){
                    g2= allWordsCountByGroup.get(2).get(word);
                }
                if(allWordsCountByGroup.get(3).containsKey(word)){
                    g3= allWordsCountByGroup.get(3).get(word);
                }


                p0=g0/sum;
                p1=g1/sum;
                p2=g2/sum;
                p3=g3/sum;
                if(p0>percentValue){
                    wordlistOfGroup.get(0).add(word);
                }
                if(p1>percentValue){
                    wordlistOfGroup.get(1).add(word);

                }if(p2>percentValue){
                    wordlistOfGroup.get(2).add(word);

                }if(p3>percentValue){
                    wordlistOfGroup.get(3).add(word);

                }
            }
        }
        System.out.println("word calculation fertig");
    }
}
