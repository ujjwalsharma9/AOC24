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

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day24\\input1.txt");

        Scanner scn = new Scanner(file);

        Map<String, Node> nodeMap = new HashMap<>();

        while (scn.hasNext()){
            String[] inp = scn.nextLine().split(": ");
            Node node = nodeMap.computeIfAbsent(inp[0], Node::new);
            node.value = Integer.parseInt(inp[1]);
        }


        File file1 = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day24\\input2.txt");

        Scanner scn1 = new Scanner(file1);

        while (scn1.hasNext()){
            String[] inp = scn1.nextLine().split(" -> ");
            Node node = nodeMap.computeIfAbsent(inp[1], Node::new);

            String[] operation = inp[0].split(" ");

            Node node1 = nodeMap.computeIfAbsent(operation[0], Node::new);
            Node node2 = nodeMap.computeIfAbsent(operation[2], Node::new);

            Node.Operation op = null;

            switch (operation[1]){
                case "AND": op = Node.Operation.AND;
                            break;
                case "OR": op = Node.Operation.OR;
                            break;
                case "XOR": op = Node.Operation.XOR;
            }

            node.node1 = node1;
            node.node2 = node2;
            node.operation = op;
        }

        //System.out.println("Ans: " + process(nodeMap));

        checkStr(nodeMap);


    }

    private static long process(Map<String, Node> nodeMap){
        Map<String, List<String>> edgeList = new HashMap<>();

        Map<String, Integer> inorderMap = new HashMap<>();

        Queue<String> qu = new ArrayDeque<>();

        int count = 0;

        for(Node node: nodeMap.values()){
            if(node.node1 == null || node.node2 == null){
                inorderMap.put(node.id, 0);
                qu.add(node.id);
            }
            else{
                inorderMap.put(node.id, 2);

                edgeList.putIfAbsent(node.node1.id, new ArrayList<>());
                edgeList.get(node.node1.id).add(node.id);

                edgeList.putIfAbsent(node.node2.id, new ArrayList<>());
                edgeList.get(node.node2.id).add(node.id);
            }

            if(node.id.charAt(0) == 'z'){
                count++;
            }
        }

        while (!qu.isEmpty()){
            String n = qu.remove();

            for(String dep: edgeList.getOrDefault(n, new ArrayList<>())){
                int new_inorder = inorderMap.get(dep) - 1;

                inorderMap.put(dep, new_inorder);

                if(new_inorder == 0){
                    compute(nodeMap.get(dep));
                    qu.add(dep);
                }

            }
        }

        long num = 0;

        for(int i = count - 1; i >= 0; i--){
            num = (num * 2L) + nodeMap.get("z" + ((i < 10) ? "0":"") + i).value;
        }

        return num;
    }

    private static void compute(Node node){


        switch (node.operation){
            case AND:   node.value = node.node1.value & node.node2.value;
                        break;
            case OR:    node.value = node.node1.value | node.node2.value;
                        break;
            case XOR:   node.value = node.node1.value ^ node.node2.value;
        }

    }

    private static void print(Map<String, Node> nodeMap){
        Solution s = new Solution();
        for(int i = 0; i <= 45; i++){
            System.out.print("z" + ((i < 10) ? "0":"") + i + " = ");
            s.dfs(nodeMap.get("z" + ((i < 10) ? "0":"") + i));
            System.out.println();
        }
    }

    private static void printStr(Map<String, Node> nodeMap){
        Solution s = new Solution();
        for(int i = 0; i <= 45; i++){
            System.out.print("z" + ((i < 10) ? "0":"") + i + " = ");
            System.out.println(s.dfsStr(nodeMap.get("z" + ((i < 10) ? "0":"") + i)).toString());
            System.out.println();
        }
    }

    private static void checkStr(Map<String, Node> nodeMap){
        Solution s = new Solution();
        StringBuilder s0 = new StringBuilder("x00 XOR y00");
        StringBuilder actS0 = s.dfsStr(nodeMap.get("z00"));
        if(!s0.toString().contentEquals(actS0)){
            System.out.println("z00");
            System.out.println(s0);
            System.out.println();
            System.out.println(actS0);
        }
        System.out.println();
        StringBuilder c = new StringBuilder("x00 AND y00");
        for(int i = 1; i <= 44; i++){
            String idx = ((i < 10) ? "0" : "") + i;
            s0 = new StringBuilder(c).append(" XOR x").append(idx).append(" XOR y").append(idx);
            actS0 = s.dfsStr(nodeMap.get("z" + idx));

            if(!s0.toString().contentEquals(actS0)){
                System.out.println("z"+idx);
                System.out.println(s0);
                System.out.println();
                System.out.println(actS0);
            }
            System.out.println();


            c.append(" AND x").append(idx).append(" XOR y").append(idx).append(" OR x").append(idx)
                    .append(" AND y").append(idx);

        }
    }

    private static void check7(Map<String, Node> nodeMap){
        String str = "x00 AND y00 AND x01 XOR y01 OR x01 AND y01 AND x02 XOR y02 OR x02 AND y02 AND x03 XOR y03 OR x03 AND y03 AND x04 XOR y04 OR x04 AND y04 AND x05 XOR y05 OR x05 AND y05 AND x06 XOR y06 OR x06 AND y06 XOR x07 XOR y07";
        Solution s = new Solution();

        for(Node node: nodeMap.values()){
            if(str.contentEquals(s.dfsStr(node))){
                System.out.println(node.id);
            }
        }
    }






}