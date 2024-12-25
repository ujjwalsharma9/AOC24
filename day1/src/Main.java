import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day1\\input.txt");

        Scanner scn = new Scanner(file);

        List<Integer> li1 = new ArrayList<>();
        List<Integer> li2 = new ArrayList<>();

        while(scn.hasNext()){
            String[] nums = scn.nextLine().split("   ");
            li1.add(Integer.parseInt(nums[0]));
            li2.add(Integer.parseInt(nums[1]));
        }

        System.out.println("Ans: " + getSimilarity(li1, li2));


    }

    private static long getSum(List<Integer> li1, List<Integer> li2){
        Collections.sort(li1);
        Collections.sort(li2);

        long sum = 0;

        for(int i = 0; i < li1.size(); i++){
            sum += Math.abs((long)li1.get(i) - li2.get(i));
        }

        return sum;
    }

    private static long getSimilarity(List<Integer> li1, List<Integer> li2){
       Map<Integer, Integer> freqMap = new HashMap<>();

       for(int x: li2){
           freqMap.put(x, 1 + freqMap.getOrDefault(x, 0));
       }

        long score = 0;

        for(int x: li1){
            score += (long)x * freqMap.getOrDefault(x, 0);
        }

        return score;
    }
}
