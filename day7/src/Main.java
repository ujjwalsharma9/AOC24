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


        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day7\\input.txt");

        Scanner scn = new Scanner(file);

        long sum = 0;

        int i = 0;

        while (scn.hasNext()) {
            String inp = scn.nextLine();
            String[] inps = inp.split(": ");
            long num = Long.parseLong(inps[0]);
            List<Integer> li = Arrays.stream(inps[1].split(" "))
                    .map(Integer::parseInt).collect(Collectors.toList());
            System.out.println(i);
            if(checkValid(num, li)){
                sum += num;
            }
            i++;
        }

        System.out.println("Ans: " + sum);
    }

    private static boolean checkValid(long res, List<Integer> li){
        return isValid(1, li, li.get(0), res);
    }

    private static boolean isValid(int idx, List<Integer> li, long currRes, long res){
        if(currRes > res){
            return false;
        }

        if(idx == li.size()){
            return currRes == res;
        }

        return isValid(idx + 1, li, currRes + li.get(idx), res)
                || isValid(idx + 1, li, currRes * li.get(idx), res)
                || isValid(idx + 1, li, combine(currRes, li.get(idx)), res);
    }

    private static long combine(long num1, int num2){

        return Long.parseLong(String.valueOf(num1) + num2);
    }
}