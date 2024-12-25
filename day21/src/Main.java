import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    static Solution s = new Solution();
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        List<String> strings = List.of("341A",
                "083A",
                "802A",
                "973A",
                "780A");

        long ans = 0;

//        for(String str: strings){
//            int min = minAns(str);
//            System.out.println(str + ", " + min);
//            //ans += (long) Integer.parseInt(str.substring(0, 3)) * min;
//        }

        System.out.println();

        Map<String, Long> minLenMap = s.getMinLength(strings, 26);

        for(Map.Entry<String, Long> e: minLenMap.entrySet()){
            System.out.println(e.getKey() + "," + e.getValue());
            ans += (long) Integer.parseInt(e.getKey().substring(0, 3)) * e.getValue();
        }

        System.out.println("Ans: " + ans);

    }

    private static int minAns(String code){

        int minLen = Integer.MAX_VALUE;

        for(StringBuilder sb: s.numSequence(code)){
            for(StringBuilder sb1: s.dirSequence(sb.toString())){
                for(StringBuilder sb2: s.dirSequence(sb1.toString())){
                    //System.out.println(code + ":" + sb2.length());
                    minLen = Math.min(minLen, sb2.length());
                }
            }
        }

      return minLen;


    }


}