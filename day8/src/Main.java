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

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day8\\input.txt");

        Scanner scn = new Scanner(file);

        List<String> grid = new ArrayList<>();

        while (scn.hasNext()) {
            grid.add(scn.nextLine());
        }

        System.out.println("Ans: "+getAntiCount(grid));
    }

    private static int getAntiCount(List<String> grid){
        Map<Character, List<int[]>> map = new HashMap<>();

        for(int i = 0; i < grid.size(); i++){
            for (int j = 0; j < grid.get(0).length(); j++){
                if(grid.get(i).charAt(j) != '.'){
                    map.putIfAbsent(grid.get(i).charAt(j), new ArrayList<>());
                    map.get(grid.get(i).charAt(j)).add(new int[]{i, j});
                }
            }
        }

        Map<Integer, Set<Integer>> mapSet = new HashMap<>();

        for(char ch: map.keySet()){
            List<int[]> li = map.get(ch);

            for(int i = 0; i < li.size(); i++){
                for (int j = i + 1; j < li.size(); j++){
//                    int x1 = 2 * li.get(i)[0] - li.get(j)[0];
//                    int y1 = 2 * li.get(i)[1] - li.get(j)[1];
//
//                    if(x1 >= 0 && y1 >= 0 && x1 < grid.size() && y1 < grid.get(0).length()){
//                        mapSet.putIfAbsent(x1, new HashSet<>());
//                        mapSet.get(x1).add(y1);
//
//                        System.out.println(x1 + "," + y1);
//                    }
//
//                    int x2 = 2 * li.get(j)[0] - li.get(i)[0];
//                    int y2 = 2 * li.get(j)[1] - li.get(i)[1];
//
//                    if(x2 >= 0 && y2 >= 0 && x2 < grid.size() && y2 < grid.get(0).length()){
//                        mapSet.putIfAbsent(x2, new HashSet<>());
//                        mapSet.get(x2).add(y2);
//                        System.out.println(x2 + "," + y2);
//                    }

                    int x1 = li.get(i)[0];
                    int y1 = li.get(i)[1];

                    int x2 = li.get(j)[0];
                    int y2 = li.get(j)[1];

                    mapSet.putIfAbsent(x1, new HashSet<>());
                    mapSet.get(x1).add(y1);

                    mapSet.putIfAbsent(x2, new HashSet<>());
                    mapSet.get(x2).add(y2);
                    System.out.println(x1 + "," + y1);
                    System.out.println(x2 + "," + y2);

                    putPoints(x1, y1, x2, y2, grid.size(), grid.get(0).length(), mapSet);
                    putPoints(x2, y2, x1, y1, grid.size(), grid.get(0).length(), mapSet);
                }
            }
        }

        int count = 0;

        for(Set<Integer> set: mapSet.values()){
            count += set.size();
        }

        return count;
    }

    private static void putPoints(int x1, int y1, int x2, int y2, int m, int n,
                                  Map<Integer, Set<Integer>> mapSet){
        int x = 2 * x1 - x2;
        int y = 2 * y1 - y2;

        if(x >= 0 && y >= 0 && x < m && y < n){
            System.out.println(x + "," + y);
            mapSet.putIfAbsent(x, new HashSet<>());
            mapSet.get(x).add(y);

            putPoints(x, y, x1, y1, m, n, mapSet);
        }
    }
}