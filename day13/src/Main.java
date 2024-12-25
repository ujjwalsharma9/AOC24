import java.io.File;
import java.io.FileNotFoundException;
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

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day13\\input.txt");

        Scanner scn = new Scanner(file);

        long count  = 0;

        while(scn.hasNext()){
            String eqA = scn.nextLine();
            String eqB = scn.nextLine();
            String eqP= scn.nextLine();

            System.out.println(eqA + "\n"+ eqB + "\n"+ eqP + "\n");
            count += countTokens(eqA, eqB, eqP);
            if(scn.hasNext()) scn.nextLine();
        }

        System.out.println("Ans: "+count);

    }

    private static long countTokens(String eqA, String eqB, String eqP){
        List<Long> A = Arrays.stream(eqA.split("A: ")[1].split(", "))
                .map(str -> Long.parseLong(str.split("\\+")[1])).collect(Collectors.toList());
        List<Long> B = Arrays.stream(eqB.split("B: ")[1].split(", "))
                .map(str -> Long.parseLong(str.split("\\+")[1])).collect(Collectors.toList());

        List<Long> values = Arrays.stream(eqP.split(": ")[1].split(", "))
                .map(str -> 10000000000000L + Long.parseLong(str.split("=")[1])).collect(Collectors.toList());

        double aVal = (((double)values.get(0) * B.get(1)) - ((double) values.get(1) * B.get(0))) /
                      (((double) A.get(0) * B.get(1) - ((double) A.get(1) * B.get(0))));

        double bVal = (values.get(0) - (A.get(0) * aVal)) / B.get(0);

        if(!checkEq(A, B, values, aVal, bVal)){
            return 0;
        }

        return (long)(aVal) * 3 + (long) bVal;

    }

    private static boolean checkEq(List<Long> A, List<Long> B, List<Long> values, double aVal,
                                   double bVal){

        long aInt = (long) aVal;
        long bInt = (long) bVal;

        return ((A.get(0) * aInt + B.get(0) * bInt) == values.get(0) && (A.get(1) * aInt + B.get(1) * bInt) == values.get(1));

    }
}