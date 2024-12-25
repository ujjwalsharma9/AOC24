import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static final int sz = 71;

    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day18\\input.txt");

        Scanner scn = new Scanner(file);

        List<int[]> corrupted = new ArrayList<>();

        while (scn.hasNext()){
            String inp = scn.nextLine();
            corrupted.add(Arrays.stream(inp.split(",")).mapToInt(Integer::parseInt).toArray());
        }


        //System.out.println(shortestPath(corrupted));

        int i = 1;

        while(i <= corrupted.size()){
            int shortestPath = shortestPath(corrupted, i);
            if(shortestPath == -1){
                System.out.println("Ans: " + Arrays.toString(corrupted.get(i - 1)));
                break;
            }
            i++;
        }
    }

    private static int shortestPath(List<int[]> corrupted, int obsCount){
        int[][] grid = new int[sz][sz];

        for(int i = 0; i < obsCount; i++){
            int[] node = corrupted.get(i);
            grid[node[1]][node[0]] = 1;
        }

        Queue<int[]> qu = new ArrayDeque<>();

        boolean[][] visited = new boolean[sz][sz];

        qu.add(new int[]{0, 0});
        visited[0][0] = true;

        int[][] dir = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        int t = 0;

        while (!qu.isEmpty()){
            int queue_size = qu.size();

            for(int i = 0; i < queue_size; i++){
                int[] node = qu.remove();

                if(node[0] == sz - 1 && node[1] == sz - 1){
                    return t;
                }

                for(int[] d: dir){
                    int rr = node[0] + d[0];
                    int cc = node[1] + d[1];

                    if(rr >= 0 && rr < sz && cc >= 0 && cc < sz && grid[rr][cc] != 1 && !visited[rr][cc]){
                        visited[rr][cc] = true;
                        qu.add(new int[]{rr, cc});
                    }
                }

            }

            t++;
        }

        return -1;

    }
}