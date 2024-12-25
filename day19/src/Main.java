import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day19\\input1.txt");

        Scanner scn = new Scanner(file);

        List<String> available = new ArrayList<>();

        while (scn.hasNext()){
            String inp = scn.nextLine();
            available.addAll(Arrays.stream(inp.split(", ")).collect(Collectors.toList()));
        }

        File file1 = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day19\\input2.txt");

        Scanner scn1 = new Scanner(file1);


        List<String> target = new ArrayList<>();

        while (scn1.hasNext()){
            target.add(scn1.nextLine());
        }

        long count = 0;

        Solution solution = new Solution(available);

        for (String str: target){
            long ways = solution.countWays(str);
            System.out.println(str + ", " + ways);
            count += ways;
        }

        System.out.println("Ans: " + count);


    }
}