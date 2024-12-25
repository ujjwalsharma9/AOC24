import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException, ExecutionException, InterruptedException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day20\\input.txt");

        Scanner scn = new Scanner(file);

        List<String> grid = new ArrayList<>();

        while (scn.hasNext()){
            grid.add(scn.nextLine());
        }

        System.out.println("Ans: " + count(grid, 100));
    }

    private static long count(List<String> grid, int minSaved) throws ExecutionException, InterruptedException {
        int srcRow = -1;
        int srcCol = -1;
        int destRow = -1;
        int destCol = -1;

        int m = grid.size();
        int n = grid.get(0).length();

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(grid.get(i).charAt(j) == 'S'){
                    srcRow = i;
                    srcCol = j;
                }
                else if(grid.get(i).charAt(j) == 'E'){
                    destRow = i;
                    destCol = j;
                }
            }
        }

        Map<Integer, Map<Integer, Integer>> sMap = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> dMap = new HashMap<>();

        saveTime(grid, srcRow, srcCol, sMap);
        saveTime(grid, destRow, destCol, dMap);

        int time = sMap.get(destRow).get(destCol);
        System.out.println(time);

        long count = 0;

        List<int[]> points = new ArrayList<>();

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){

                if(grid.get(i).charAt(j) != '#'){
                    points.add(new int[]{i, j});
                 }
            }
        }

        for(int i = 0; i < points.size(); i++){
            for(int j = i + 1; j < points.size(); j++){
                int dist = Math.abs(points.get(i)[0] - points.get(j)[0]) +
                        Math.abs(points.get(i)[1] - points.get(j)[1]);
                if(dist <= 20){
                    int new_time = dist + sMap.get(points.get(i)[0]).get(points.get(i)[1])
                            + dMap.get(points.get(j)[0]).get(points.get(j)[1]);

                    if(time - new_time >= minSaved){
                        count++;
                    }

                    new_time = dist + sMap.get(points.get(j)[0]).get(points.get(j)[1])
                            + dMap.get(points.get(i)[0]).get(points.get(i)[1]);

                    if(time - new_time >= minSaved){
                        count++;
                    }
                }
            }
        }


        return count;
    }

    private static int dfs(List<String> grid, int srcRow, int srcCol, int destRow, int destCol, int igRow, int igCol){
        int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        Queue<int[]> qu = new ArrayDeque<>();

        qu.add(new int[]{srcRow, srcCol});

        int m = grid.size();
        int n = grid.get(0).length();

        boolean[][] visited = new boolean[m][n];
        visited[srcRow][srcCol] = true;

        int t = 0;

        while (!qu.isEmpty()){
            int sz = qu.size();

            for(int i = 0; i < sz; i++){
                int[] node = qu.remove();

                if(node[0] == destRow && node[1] == destCol){
                    return t;
                }

                for(int[] d: dir){
                    int rr = node[0] + d[0];
                    int cc = node[1] + d[1];

                    if(rr >= 0 && rr < m && cc >= 0 && cc < n && !visited[rr][cc]
                            && (grid.get(rr).charAt(cc) != '#' || (rr == igRow && cc == igCol))){
                        visited[rr][cc] = true;
                        qu.add(new int[]{rr, cc});
                    }
                }
            }

            t++;
        }

        return Integer.MAX_VALUE;
    }

    private static long dfsCount(List<String> grid, int time, int minSavedTime , int srcRow, int srcCol, int destRow, int destCol, int stRow, int stCol,
                                 Map<Integer, Map<Integer, Integer>> smap, Map<Integer, Map<Integer, Integer>> dmap){
        int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        Queue<int[]> qu = new ArrayDeque<>();

        qu.add(new int[]{stRow, stCol});

        int m = grid.size();
        int n = grid.get(0).length();

        boolean[][] visited = new boolean[m][n];
        visited[stRow][stCol] = true;

        int t = 0;
        long count = 0;

        while (!qu.isEmpty() && t <= 20){
            int sz = qu.size();

            for(int i = 0; i < sz; i++){
                int[] node = qu.remove();

                if(t > 0 && grid.get(node[0]).charAt(node[1]) != '#'){
                    int diff = time - (t + (smap.get(stRow).get(stCol)) + dmap.get(node[0]).get(node[1]));

                    if(diff >= minSavedTime){
                        count++;
                    }
                }



                if(grid.get(node[0]).charAt(node[1]) != 'E'){
                    for(int[] d: dir){
                        int rr = node[0] + d[0];
                        int cc = node[1] + d[1];

                        if(rr >= 0 && rr < m && cc >= 0 && cc < n && !visited[rr][cc]){
                            visited[rr][cc] = true;
                            qu.add(new int[]{rr, cc});
                        }
                    }
                }
            }

            t++;
        }

        return count;
    }

    private static void saveTime(List<String> grid, int srcRow, int srcCol, Map<Integer, Map<Integer, Integer>> distMap){
        int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        Queue<int[]> qu = new ArrayDeque<>();

        qu.add(new int[]{srcRow, srcCol});

        int m = grid.size();
        int n = grid.get(0).length();

        boolean[][] visited = new boolean[m][n];
        visited[srcRow][srcCol] = true;

        int t = 0;

        while (!qu.isEmpty()){
            int sz = qu.size();

            for(int i = 0; i < sz; i++){
                int[] node = qu.remove();

                distMap.putIfAbsent(node[0], new HashMap<>());
                distMap.get(node[0]).put(node[1], t);

                for(int[] d: dir){
                    int rr = node[0] + d[0];
                    int cc = node[1] + d[1];

                    if(rr >= 0 && rr < m && cc >= 0 && cc < n && !visited[rr][cc]
                            && grid.get(rr).charAt(cc) != '#'){
                        visited[rr][cc] = true;
                        qu.add(new int[]{rr, cc});
                    }
                }
            }

            t++;
        }

    }


}