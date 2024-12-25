import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        List<Pair> edges = new ArrayList<>();

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day5\\input1.txt");

        Scanner scn = new Scanner(file);

        while (scn.hasNext()) {
            String inp = scn.nextLine();
            String[] inps = inp.split("\\|");
            edges.add(new Pair(Integer.parseInt(inps[0]), Integer.parseInt(inps[1])));
        }

        File file1 = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day5\\input2.txt");

        Scanner scn1 = new Scanner(file1);

        long sum = 0;

        while (scn1.hasNext()) {
            String inp = scn1.nextLine();
            List<Integer> li = Arrays.stream(inp.split(",")).map(Integer::parseInt).collect(Collectors.toList());
//            if(isValid(li, edges)){
//                sum += li.get(li.size() / 2);
//            }

            sum += reorder(li, edges);
        }

        System.out.println("Ans:" + sum);
    }

    private static boolean isValid(List<Integer> li, List<Pair> edges){
        Set<Integer> set = new HashSet<>(li);

        List<Pair> filter_edges = edges.stream().filter(p -> set.contains(p.a) && set.contains(p.b))
                .collect(Collectors.toList());

        Map<Integer, Integer> inorder = new HashMap<>();
        Map<Integer, List<Integer>> edgeList = new HashMap<>();

        for(Pair p: filter_edges){
            edgeList.putIfAbsent(p.a, new ArrayList<>());
            edgeList.get(p.a).add(p.b);
            inorder.put(p.b, inorder.getOrDefault(p.b, 0) + 1);
        }

        for(int node: li){
            if(inorder.getOrDefault(node, 0) != 0){
                return false;
            }

            for(int neigh: edgeList.getOrDefault(node, new ArrayList<>())){
                inorder.put(neigh, inorder.get(neigh) - 1);
            }
        }

        return true;
    }

    private static int reorder(List<Integer> li, List<Pair> edges){
        Set<Integer> set = new HashSet<>(li);

        List<Pair> filter_edges = edges.stream().filter(p -> set.contains(p.a) && set.contains(p.b))
                .collect(Collectors.toList());

        Map<Integer, Integer> inorder = new HashMap<>();
        Map<Integer, Integer> inorder1 = new HashMap<>();
        Map<Integer, List<Integer>> edgeList = new HashMap<>();

        for(int node: li){
            inorder.put(node, 0);
            inorder1.put(node, 0);
            edgeList.put(node, new ArrayList<>());
        }

        for(Pair p: filter_edges){
            edgeList.get(p.a).add(p.b);
            inorder.put(p.b, inorder.get(p.b) + 1);
            inorder1.put(p.b, inorder1.get(p.b) + 1);
        }

        for(int node: li){
            if(inorder.get(node) != 0){
                return topSort(inorder1, edgeList);
            }

            for(int neigh: edgeList.get(node)){
                inorder.put(neigh, inorder.get(neigh) - 1);
            }
        }

        return 0;
    }

    private static int topSort(Map<Integer, Integer> inorder, Map<Integer, List<Integer>> edgeList){
        List<Integer> order = new ArrayList<>();

        Queue<Integer> qu = new ArrayDeque<>();

        for(int node: inorder.keySet()){
            if(inorder.get(node) == 0){
                qu.add(node);
            }
        }

        while (!qu.isEmpty()){
            int node = qu.remove();
            order.add(node);

            for(int neigh: edgeList.get(node)){
                int in = inorder.get(neigh) - 1;
                inorder.put(neigh, in);
                if(in == 0){
                    qu.add(neigh);
                }
            }
        }

        return order.get(order.size() / 2);
    }
}