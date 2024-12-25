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

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day2\\input.txt");

        Scanner scn = new Scanner(file);


        int count = 0;
        while (scn.hasNext()) {
            String inp = scn.nextLine();
            if (isValid2(Arrays.stream(inp.split(" ")).map(Integer::parseInt).collect(Collectors.toList()))) {
                count++;
            }
        }

        System.out.println("Ans:" + count);
    }

    private static boolean isValid(List<Integer> li) {
        if (li.size() <= 1) return true;
        boolean isInc = li.get(1) > li.get(0);
        int pCount = 0;

        for (int i = 1; i < li.size(); i++) {
            if ((isInc && li.get(i) > li.get(i - 1)) || (!isInc && li.get(i) < li.get(i - 1))) {
                if (Math.abs(li.get(i) - li.get(i - 1)) > 3) {
                    return false;
                }
            } else return false;
        }
        return true;
    }

    private static boolean isValid2(List<Integer> li){
        int[][][][] memo = new int[li.size()][li.size()][2][2];

        for(int[][][] arr: memo){
            for(int[][] arr1: arr){
                for(int[] arr2: arr1){
                    Arrays.fill(arr2, -1);
                }
            }
        }


        if(dp(1, 0, 0, 0, li, memo) == 1){
            return true;
        }

        for(int[][][] arr: memo){
            for(int[][] arr1: arr){
                for(int[] arr2: arr1){
                    Arrays.fill(arr2, -1);
                }
            }
        }


        return dp(1, 0, 0, 1, li, memo) == 1;



    }

    private static int dp(int idx, int prev, int isIgn, int isInc, List<Integer> li, int[][][][] memo){
        if(idx == li.size()){
            return 1;
        }

        if(memo[idx][prev][isIgn][isInc] == -1){
            int ans = 0;
            if(li.get(idx) >= li.get(prev)){
                if(isInc == 0 || (li.get(idx) - li.get(prev)) < 1 || (li.get(idx) - li.get(prev)) > 3){
                    if(isIgn == 0 && ((dp(idx + 1, prev, 1, isInc, li, memo) == 1) ||
                            (checkValid(prev - 1, idx, isInc, li) && (dp(idx + 1, idx, 1, isInc, li, memo) == 1)))){
                        ans = 1;
                    }
                }
                else{
                    ans = dp(idx + 1, idx, isIgn, isInc, li, memo);
                }

            }
            else{
                if(isInc == 1 || (li.get(prev) - li.get(idx)) < 1 || (li.get(prev) - li.get(idx)) > 3){
                    if(isIgn == 0 && ((dp(idx + 1, prev, 1, isInc, li, memo) == 1) ||
                            (checkValid(prev -1, idx, isInc, li) && (dp(idx + 1, idx, 1, isInc, li, memo) == 1)))){
                        ans = 1;
                    }
                }
                else{
                    ans = dp(idx + 1, idx, isIgn, isInc, li, memo);
                }
            }

            memo[idx][prev][isIgn][isInc] = ans;

        }

        return memo[idx][prev][isIgn][isInc];
    }

    private static boolean checkValid(int idx1, int idx2, int isInc, List<Integer> li){
        if(idx1 == -1) return true;

        return (isInc == 1 && li.get(idx2) - li.get(idx1) >= 1 && li.get(idx2) - li.get(idx1) <= 3)
                || (isInc == 0 && li.get(idx1) - li.get(idx2) >= 1 && li.get(idx1) - li.get(idx2) <= 3);
    }



}
