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

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day15\\input1.txt");

        Scanner scn = new Scanner(file);

        List<String> grid = new ArrayList<>();

        while (scn.hasNext()){
            grid.add(scn.nextLine());
;        }

        int[] iniPos = new int[2];

        Map<Integer, Map<Integer, Character>> map = convertToMap(grid, iniPos);

        StringBuilder mov  = new StringBuilder();

        File file1 = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day15\\input2.txt");

        Scanner scn1 = new Scanner(file1);

        while (scn1.hasNext()){
            mov.append(scn1.nextLine());
        }


        System.out.println("Ans: " + getGps(map, mov, iniPos));

    }

    private static long getGps(Map<Integer, Map<Integer, Character>> map, StringBuilder sb, int[] iniPos){
        applyMovements(map, sb, iniPos);

        long res = 0;

        for(int row: map.keySet()){
            for(int col: map.get(row).keySet()){
                System.out.print(map.get(row).get(col));
                if(map.get(row).get(col) == '['){
                    res += 100L * row + col;
                }
            }
            System.out.println();
        }

        return res;
    }

    private static void print(Map<Integer, Map<Integer, Character>> map){
        for(int row: map.keySet()){
            for(int col: map.get(row).keySet()){
                System.out.print(map.get(row).get(col));

            }
            System.out.println();
        }
    }

    private static void applyMovements(Map<Integer, Map<Integer, Character>> map, StringBuilder sb, int[] iniPos){
        int i = iniPos[0];
        int j = iniPos[1];

        Map<Character, int[]> dirMap = Map.of('>', new int[]{0, 1}, '<', new int[]{0, -1}, '^'
                , new int[]{-1, 0}, 'v', new int[]{1, 0});


        for(int c = 0; c < sb.length(); c++){
            char ch = sb.charAt(c);

            int[] pos = getNewPos(i, j, dirMap.get(ch), map);

            i = pos[0];
            j = pos[1];

            System.out.println(ch);

        }

    }

    private static int[] getNewPos(int i, int j, int[] d, Map<Integer, Map<Integer, Character>> map){
        Queue<int[]> qu = new ArrayDeque<>();

        qu.add(new int[]{i, j});

        Map<Integer, Map<Integer, Character>> new_map = new HashMap<>();



        boolean isValid = true;

        Map<Integer, Set<Integer>> processed = new HashMap<>();

        while(!qu.isEmpty()){
            int[] node = qu.remove();

            if(processed.containsKey(node[0]) && processed.get(node[0]).contains(node[1])){
                continue;
            }

            processed.putIfAbsent(node[0], new HashSet<>());
            processed.get(node[0]).add(node[1]);

            if(!(new_map.containsKey(node[0]) && new_map.get(node[0]).containsKey(node[1]))){

                new_map.putIfAbsent(node[0], new HashMap<>());
                new_map.get(node[0]).put(node[1], '.');
            }

            char ch = map.get(node[0]).get(node[1]);

            int rr = node[0] + d[0];
            int cc = node[1] + d[1];

            new_map.putIfAbsent(rr, new HashMap<>());
            new_map.get(rr).put(cc, ch);

            char next_ch = map.get(rr).get(cc);

            if(next_ch == '#'){
                isValid = false;
                break;
            }
            else if (next_ch == '[') {
                qu.add(new int[]{rr, cc});
                qu.add(new int[]{rr, cc + 1});
            }
            else if (next_ch == ']') {
                qu.add(new int[]{rr, cc});
                qu.add(new int[]{rr, cc - 1});
            }
        }

        if(!isValid){
            return new int[]{i, j};
        }

        for(int row: new_map.keySet()){
            for(int col: new_map.get(row).keySet()){
                map.get(row).put(col, new_map.get(row).get(col));
            }
        }

        return new int[]{i + d[0], j + d[1]};
    }

    private static Map<Integer, Map<Integer, Character>> convertToMap(List<String> grid, int[] iniPos){
        List<StringBuilder> sbList = new ArrayList<>();

        for (String s : grid) {
            StringBuilder sb = new StringBuilder();
            for (char ch: s.toCharArray()) {
                switch (ch) {
                    case '#':
                        sb.append("##");
                        break;
                    case 'O':
                        sb.append("[]");
                        break;
                    case '.':
                        sb.append("..");
                        break;
                    case '@':
                        sb.append("@.");
                }
            }

            sbList.add(sb);
        }

        Map<Integer, Map<Integer, Character>> map = new HashMap<>();

        for(int i = 0; i < sbList.size(); i++){
            map.put(i, new HashMap<>());
            for(int j = 0; j < sbList.get(0).length(); j++){
                map.get(i).put(j, sbList.get(i).charAt(j));

                if(sbList.get(i).charAt(j) == '@'){
                    iniPos[0] = i;
                    iniPos[1] = j;
                }
            }
        }

        return map;
    }
}