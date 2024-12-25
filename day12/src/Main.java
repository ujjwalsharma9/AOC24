import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");


        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day12\\input.txt");

        Scanner scn = new Scanner(file);

        List<String> grid = new ArrayList<>();

        while (scn.hasNext()) {
            grid.add(scn.nextLine());
        }

        System.out.println("Ans: " + getCost1(grid));
    }

    private static long getCost(List<String> grid){
        long cost = 0 ;

        int m = grid.size();
        int n = grid.get(0).length();

        boolean[][] visited = new boolean[m][n];


        for(int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if(!visited[i][j]){
                    Shape s = dfs(i, j, grid, visited);
                    cost += s.area * s.perimeter;
                }
            }
        }

        return cost;
    }

    private static Shape dfs(int i, int j, List<String> grid, boolean[][] visited){
        char ch = grid.get(i).charAt(j);

        visited[i][j] = true;

        int[][] dir = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        Shape s = new Shape(1, 0);

        for(int[] d: dir){
            int rr = i + d[0];
            int cc = j + d[1];

            boolean nextVal = false;

            if(rr >= 0 && rr < grid.size() && cc >= 0 && cc < grid.get(0).length() && grid.get(rr).charAt(cc) == ch){
                nextVal = true;

                if(!visited[rr][cc]){
                    Shape s1 = dfs(rr, cc, grid, visited);
                    s.area += s1.area;
                    s.perimeter += s1.perimeter;
                }
            }

            if(!nextVal){
                s.perimeter++;
            }

        }

        return s;
    }

    private static long getCost1(List<String> grid){
        long cost = 0 ;

        int m = grid.size();
        int n = grid.get(0).length();

        boolean[][] visited = new boolean[m][n];


        for(int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                if(!visited[i][j]){
                    List<int[]> boundary = new ArrayList<>();
                    long area = dfs1(i, j, grid, visited, boundary);
                    long sides = getSides(grid, boundary);
                    System.out.println(grid.get(i).charAt(j) + "," + sides);
                    cost += area * sides;
                }
            }
        }

        return cost;
    }

    private static long dfs1(int i, int j, List<String> grid, boolean[][] visited, List<int[]> boundary){
        char ch = grid.get(i).charAt(j);

        visited[i][j] = true;

        int[][] dir = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        long area = 1;

        boolean isBoundary = false;

        for(int[] d: dir){
            int rr = i + d[0];
            int cc = j + d[1];

            boolean nextVal = false;

            if(rr >= 0 && rr < grid.size() && cc >= 0 && cc < grid.get(0).length() && grid.get(rr).charAt(cc) == ch){
                nextVal = true;

                if(!visited[rr][cc]){
                    long a = dfs1(rr, cc, grid, visited, boundary);
                    area += a;
                }
            }

            if(!nextVal){
                isBoundary = true;
            }

        }

        if(isBoundary){
            boundary.add(new int[]{i, j});
        }

        return area;
    }

    private static long getSides(List<String> grid, List<int[]> boundary){
        int m = grid.size();
        int n = grid.get(0).length();

        boolean[][][] visited = new boolean[m][n][4];

        int[][] dir = new int[][]{ {0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        //0->l, 1-r, 2-u, 3-b

        long count = 0;

        for(int[] cell: boundary){
            for(int k = 0; k < 4; k++){
                int i = cell[0];
                int j = cell[1];

                if(!visited[i][j][k]){
                    count += boundaryDFS(i, j, k, grid, dir, visited);
                }

            }
        }



        return count;


    }

    private static long boundaryDFS(int i, int j, int k, List<String> grid, int[][] dir, boolean[][][] visited){
        visited[i][j][k] = true;

        int rr1 = i + dir[k][0];
        int cc1 = j + dir[k][1];

        char ch = grid.get(i).charAt(j);

        if(rr1 >= 0 && rr1 < grid.size() && cc1 >= 0 && cc1 < grid.get(0).length() && grid.get(rr1).charAt(cc1) == ch){
            return 0;
        }

        int st;
        int en;

        if(k <= 1){
            st = 2;
            en = 3;
        }
        else {
            st = 0;
            en = 1;
        }

        for(int d = st; d <= en; d++){
            int rr = i + dir[d][0];
            int cc = j + dir[d][1];

            if(rr >= 0 && rr < grid.size() && cc >= 0 && cc < grid.get(0).length() && grid.get(rr).charAt(cc) == ch
                    && !visited[rr][cc][k]){
                boundaryDFS(rr, cc, k, grid, dir, visited);
            }
        }

        return 1;
    }
}