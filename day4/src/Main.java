import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day4\\input.txt");

        Scanner scn = new Scanner(file);

        List<String> grid = new ArrayList<>();

        while (scn.hasNext()) {
            grid.add(scn.nextLine());
        }

        System.out.println("Ans:" + getCountX(grid));


    }

    static int[][] dir = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}, {1, 1}, {-1, -1}, {1, -1}, {-1, 1}};

    static String target = "XMAS";
    private static int getCount(List<String> grid){

        int count = 0;

        for(int i = 0; i < grid.size(); i++){
            for(int j = 0; j < grid.get(0).length(); j++){
                for(int d = 0; d < dir.length; d++){
                    if(dfs(i, j, grid, d, 0)){
                        count++;
                    }
                }
            }

        }

        return count;
    }

    private static boolean dfs(int i, int j, List<String> grid, int dirIdx, int tIdx){
        if(tIdx >= target.length()) return true;

        if(i < 0 || j < 0 || i >= grid.size() || j >= grid.get(0).length() || grid.get(i).charAt(j) != target.charAt(tIdx)){
            return false;
        }

        return dfs(i + dir[dirIdx][0], j + dir[dirIdx][1], grid, dirIdx, tIdx + 1);
    }

    private static int getCountX(List<String> grid){
        int count = 0;
        for(int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).length(); j++) {
                if(grid.get(i).charAt(j) == 'A'){
                    //leftDiag
                    int tlI = i - 1;
                    int tlJ = j - 1;

                    if(tlI < 0 || tlJ < 0){
                        continue;
                    }

                    int brI = i + 1;
                    int brJ = j + 1;

                    if(brI >= grid.size() || brJ >= grid.get(0).length()){
                        continue;
                    }

                    if(((grid.get(tlI).charAt(tlJ) == 'M' && grid.get(brI).charAt(brJ) == 'S') ||
                            (grid.get(tlI).charAt(tlJ) == 'S' && grid.get(brI).charAt(brJ) == 'M')) &&
                            ((grid.get(brI).charAt(tlJ) == 'M' && grid.get(tlI).charAt(brJ) == 'S') ||
                                    (grid.get(brI).charAt(tlJ) == 'S' && grid.get(tlI).charAt(brJ) == 'M'))){
                        count++;
                    }


                }
            }
        }

        return count;
    }
}