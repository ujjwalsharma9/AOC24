import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day6\\input.txt");

        Scanner scn = new Scanner(file);

        List<String> grid = new ArrayList<>();

        while (scn.hasNext()) {
            grid.add(scn.nextLine());
        }

        System.out.println(countCyclePositions(grid));

    }

    private static int countPositions(List<String> grid){
        Map<Integer, Set<Integer>> posSet = new HashMap<>();

        int I = -1;
        int J = -1;


        for(int i = 0; i < grid.size(); i++){
            boolean found = false;
            for(int j = 0; j < grid.get(0).length(); j++){
                if(grid.get(i).charAt(j) == '^'){
                    found = true;
                    I = i;
                    J = j;
                    break;
                }
            }
            if(found) break;
        }

        int[][] dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int d = 0;

        posSet.putIfAbsent(I, new HashSet<>());
        posSet.get(I).add(J);

        while(true){
            int rr = I + dir[d][0];
            int cc = J + dir[d][1];

            if(rr < 0 || cc < 0 || rr >= grid.size() || cc >= grid.get(0).length()){
                break;
            }
            else if (grid.get(rr).charAt(cc) != '#') {
                I = rr;
                J = cc;
                posSet.putIfAbsent(I, new HashSet<>());
                posSet.get(I).add(J);
            }
            else {
                d = (d + 1) % 4;
            }
        }

        int count = 0;

        for(Set<Integer> set: posSet.values()){
            count += set.size();
        }



        return count;

    }

    private static int countCyclePositions(List<String> grid){

        int I = -1;
        int J = -1;


        for(int i = 0; i < grid.size(); i++){
            boolean found = false;
            for(int j = 0; j < grid.get(0).length(); j++){
                if(grid.get(i).charAt(j) == '^'){
                    found = true;
                    I = i;
                    J = j;
                    break;
                }
            }
            if(found) break;
        }

        int[][] dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int d = 0;
        int count = 0;

        int iniX = I;
        int iniY = J;

        Map<Integer, Set<Integer>> posSet = new HashMap<>();

        while(true){
            int rr = I + dir[d][0];
            int cc = J + dir[d][1];

            if(rr < 0 || cc < 0 || rr >= grid.size() || cc >= grid.get(0).length()){
                break;
            }
            else if (grid.get(rr).charAt(cc) != '#') {
                I = rr;
                J = cc;

                if(!(I == iniX && J == iniY) && checkCycle(iniX, iniY, I, J, grid, dir)){
                    posSet.putIfAbsent(I, new HashSet<>());
                    posSet.get(I).add(J);
                }
            }
            else {
                d = (d + 1) % 4;
            }
        }

        for(Set<Integer> set: posSet.values()){
            count += set.size();
        }


        return count;

    }

    private static boolean checkCycle(int iniX, int iniY, int obsX, int obsY, List<String> grid, int[][] dir){


        Map<Integer, Map<Integer, Set<Integer>>> posMap = new HashMap<>();
        //1 step
        int I = iniX;
        int J = iniY;
        int d = 0;

        while (true){
            posMap.putIfAbsent(I, new HashMap<>());
            posMap.get(I).putIfAbsent(J, new HashSet<>());
            posMap.get(I).get(J).add(d);

            int rr = I + dir[d][0];
            int cc = J + dir[d][1];

            if(rr < 0 || cc < 0 || rr >= grid.size() || cc >= grid.get(0).length()){
                return false;
            }
            else if (posMap.containsKey(rr) &&
                    posMap.get(rr).containsKey(cc) && posMap.get(rr).get(cc).contains(d)) {
                return true;
            } else if (grid.get(rr).charAt(cc) != '#' && !(rr == obsX && cc == obsY)) {
                I = rr;
                J = cc;
            }
            else {
                d = (d + 1) % 4;
            }
        }
    }
}