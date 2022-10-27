import java.util.ArrayList;
import java.util.Arrays;

public class DeleteTest {

    public static void main(String[] args) {

        ArrayList<ArrayList<String>> wordLists = new ArrayList<>();

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("Hallo");
        list1.add("Baum");
        list1.add("Sonne");
        list1.add("Person");
        wordLists.add(list1);

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("Hallo");
        list2.add("Sonne");
        list2.add("Baum");
        wordLists.add(list2);

        ArrayList<String> list3 = new ArrayList<>();
        list3.add("Nix");
        list3.add("Haus");
        list3.add("Sonne");
        list3.add("Tonne");
        list3.add("Paul");

        wordLists.add(list3);

        ArrayList<ArrayList<String>> originalFiles = new ArrayList<>(wordLists);

        for(int i = 0; i<wordLists.size(); i++){
            for(int b = 0; b<originalFiles.get(i).size(); b++){
                String s= originalFiles.get(i).get(b);
                for(int x = 0; x<wordLists.size(); x++){
                    if(i!=x){
                        while(wordLists.get(x).contains(s)){
                            wordLists.get(i).remove(s);
                            wordLists.get(x).remove(s);
                        }
                    }
                }
            }
        }

        System.out.println(wordLists);

        String text = "Hallo wie Earthorbit.";

        System.out.println(text.contains("orbit"));



    }

}
