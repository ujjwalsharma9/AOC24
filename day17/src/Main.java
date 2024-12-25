import java.io.File;
import java.util.Map;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static long A = 22817223;
    private static long B = 0;
    private static long C = 0;
    private static final int[] op = new int[]{2,4,1,2,7,5,4,5,0,3,1,7,5,5,3,0};

    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        //getOutput();
        long aVal = 0;
        for(int i = op.length - 1; i >= 0; i--){
            aVal = aAns(op[i], aVal * 8, (aVal + 1) * 8);
        }

        System.out.println(aVal);

        A = aVal;
        getOutput();

    }

    private static void getOutput(){

        int i = 0;

        while (i < op.length){
            int opcode = op[i];
            int operand = op[i + 1];

            switch (opcode){
                case 0: A = (long) (A / Math.pow(2, combo(operand)));
                        break;
                case 1: B = B ^ operand;
                        break;
                case 2: B = combo(operand) % 8;
                        break;
                case 3: if(A != 0){
                            i = operand;
                            continue;
                        }
                        break;
                case 4: B = B ^ C;
                        break;
                case 5: System.out.print((combo(operand) % 8) + ",");
                        break;
                case 6: B = (long) (A / Math.pow(2, combo(operand)));
                        break;
                case 7: C = (long) (A / Math.pow(2, combo(operand)));
            }

            i = i + 2;
        }
    }

    private static long combo(int operand){
        if(operand <= 3){
            return operand;
        }
        else{
            switch (operand){
                case 4: return A;
                case 5: return B;
                case 6: return C;
            }
        }

        return 0;
    }


    private static long aAns(long val, long st, long en){
        for(long a = st; a < en; a++){
            long b = a % 8;
            b = b ^ 2;
            long c = (long)(a / Math.pow(2, b));
            b = b ^ c;
            b = b ^ 7;

            long out = b % 8;


            if(out == val){
                return a;
            }
        }

        return -1;
    }
}