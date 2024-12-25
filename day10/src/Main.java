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


        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day10\\input.txt");

        Scanner scn = new Scanner(file);

        List<String> grid = new ArrayList<>();

        while (scn.hasNext()) {
            grid.add(scn.nextLine());
        }

        System.out.println("Ans: " + getCount2(grid));
    }

    private static int getCount(List<String> grid){
        int count = 0;

        for(int i = 0; i < grid.size(); i++){
            for(int j = 0; j < grid.get(0).length(); j++){
                if(grid.get(i).charAt(j) == '0'){
                    System.out.println(i + "," + j);
                    Set<List<Integer>> set = new HashSet<>();
                    dfs(i, j, grid, 0, set);
                    count += set.size();
                }
            }
        }

        return count;
    }

    private static int getCount2(List<String> grid){
        int count = 0;

        for(int i = 0; i < grid.size(); i++){
            for(int j = 0; j < grid.get(0).length(); j++){
                if(grid.get(i).charAt(j) == '0'){
                    System.out.println(i + "," + j);
                    count += dfs(i, j, grid, 0, new HashSet<>());
                }
            }
        }

        return count;
    }

    private static int dfs(int i, int j, List<String> grid, int curr, Set<List<Integer>> set){
        if(curr == 9){
            set.add(List.of(i, j));
            return 1;
        }

        int next = curr + 1;

        int[][] dir = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        int count = 0;

        for(int[] d: dir){
            int rr = i + d[0];
            int cc = j + d[1];

            if(rr >= 0 && cc >= 0 && rr < grid.size() && cc < grid.get(0).length()
                    && next == grid.get(rr).charAt(cc) - '0'){
                count += dfs(rr, cc, grid, next, set);
            }
        }

        return count;


    }
}