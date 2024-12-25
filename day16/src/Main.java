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

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day16\\input.txt");

        Scanner scn = new Scanner(file);

        List<String> grid = new ArrayList<>();

        while (scn.hasNext()){
            grid.add(scn.nextLine());
        }

        System.out.println("Ans: "+ lowestScore(grid));

    }

    private static int lowestScore(List<String> grid){
        PriorityQueue<Info> pq = new PriorityQueue<>((p1, p2) -> {
            if(p1.dist < p2.dist){
                return -1;
            }
            else if (p1.dist > p2.dist) {
                return 1;
            }
            return 0;
        });

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
                else if (grid.get(i).charAt(j) == 'E') {
                    destRow = i;
                    destCol = j;
                }
            }
        }

        int[][] dir = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};



        //boolean[][][] visited = new boolean[m][n][4];

        pq.add(new Info(srcRow, srcCol, 0, 0));
        long[][][] distArr = new long[m][n][4];
        for(long[][] arr: distArr){
            for(long[] arr1: arr){
                Arrays.fill(arr1, Long.MAX_VALUE);
            }
        }
        distArr[srcRow][srcCol][0] = 0;

        Map<List<Integer>, List<List<Integer>>> parentMap = new HashMap<>();

        while(!pq.isEmpty()){
            Info node = pq.remove();
            long dist = node.dist;

            int rr = node.row + dir[node.direction][0];
            int cc = node.col + dir[node.direction][1];

            if(grid.get(rr).charAt(cc) != '#'){
                long new_dist = dist + 1;

                if(new_dist < distArr[rr][cc][node.direction]){
                    distArr[rr][cc][node.direction] = new_dist;
                    pq.add(new Info(rr, cc, node.direction, new_dist));
                    parentMap.put(List.of(rr, cc, node.direction), new ArrayList<>());
                    parentMap.get(List.of(rr, cc, node.direction)).add(List.of(node.row, node.col, node.direction));
                }
                else if (new_dist == distArr[rr][cc][node.direction]) {
                    parentMap.get(List.of(rr, cc, node.direction))
                            .add(List.of(node.row, node.col, node.direction));
                }
            }

            int stIdx;
            int endIdx;

            if(node.direction <= 1){
                stIdx = 2;
                endIdx = 3;
            }
            else{
                stIdx = 0;
                endIdx = 1;
            }

            for(int d = stIdx; d <= endIdx; d++){
                long new_dist = dist + 1000;

                if(new_dist < distArr[node.row][node.col][d]){
                    distArr[node.row][node.col][d] = new_dist;
                    pq.add(new Info(node.row, node.col, d, new_dist));
                    parentMap.put(List.of(node.row, node.col, d), new ArrayList<>());
                    parentMap.get(List.of(node.row, node.col, d)).add(List.of(node.row, node.col, node.direction));
                }
                else if (new_dist == distArr[node.row][node.col][d]) {
                    parentMap.get(List.of(node.row, node.col, d)).add(List.of(node.row, node.col, node.direction));
                }
            }


        }


        Queue<List<Integer>> qu = null;

        boolean[][][] visited1 = new boolean[m][n][4];

        long minDist = Long.MAX_VALUE;

        for(int d=  0; d < 4; d++){
            if(distArr[destRow][destCol][d] < minDist){
                minDist = distArr[destRow][destCol][d];
                qu = new ArrayDeque<>();
                qu.add(List.of(destRow, destCol, d));
            }
            else if(distArr[destRow][destCol][d] == minDist){
                qu.add(List.of(destRow, destCol, d));
            }
        }

        Map<Integer, Set<Integer>> setMap = new HashMap<>();
        int count = 0;

        while(!qu.isEmpty()){
            List<Integer> node = qu.remove();


            if(!visited1[node.get(0)][node.get(1)][node.get(2)]){
                visited1[node.get(0)][node.get(1)][node.get(2)] = true;

                if(!(setMap.containsKey(node.get(0)) && setMap.get(node.get(0)).contains(node.get(1)))){
                    count++;
                    setMap.putIfAbsent(node.get(0), new HashSet<>());
                    setMap.get(node.get(0)).add(node.get(1));
                }


                if(parentMap.containsKey(node)){
                    qu.addAll(parentMap.get(node));
                }
            }
        }

        return count;
    }
}