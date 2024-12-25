import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day3\\input.txt");

        Scanner scn = new Scanner(file);


        long ans = 0;
        int count = 0;
        boolean isEnabled = true;
        while (scn.hasNext()) {
            String inp = scn.nextLine();
            ans += processString(inp, isEnabled);
            if(inp.lastIndexOf("do()") != inp.lastIndexOf("don't()")){
                isEnabled = inp.lastIndexOf("do()") > inp.lastIndexOf("don't()");
            }
            count++;
        }

        System.out.println("count:" + count);

        System.out.println("Ans:" + ans);

    }

    private static long processString(String inp, boolean isEnabled){
        int start = 0;

        long ans = 0;

        while(true){
            int pos = inp.indexOf("mul", start);

            if(pos == -1){
                break;
            }

            String lastString = inp.substring(start, pos);
            int doPos = lastString.lastIndexOf("do()");
            int dontPos = lastString.lastIndexOf("don't()");

            if(doPos != dontPos){
                isEnabled = doPos > dontPos;
            }

            if(isEnabled) ans += process(inp, pos + 3);

            start = pos + 1;
        }

        return ans;

    }

    private static long process(String str, int idx){
        if(str.charAt(idx) != '(') return 0;

        if(str.charAt(idx + 1) < '0' || str.charAt(idx + 1) > '9') return 0;

        int i = idx + 1;

        int num = 0;

        while(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
            num = (num * 10) + (str.charAt(i) - '0');
            i++;
        }

        if(num >= 1000) return 0;

        if(str.charAt(i) != ','){
            return 0;
        }

        i++;

        if(str.charAt(i) < '0' || str.charAt(i) > '9') return 0;

        int num2 = 0;

        while(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
            num2 = (num2 * 10) + (str.charAt(i) - '0');
            i++;
        }

        if(num2 >= 1000) return 0;

        if(str.charAt(i) != ')'){
            return 0;
        }

        System.out.println(num + "," + num2);

        return (long)num * num2;
    }


}