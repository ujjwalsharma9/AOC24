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

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day23\\input.txt");

        Scanner scn = new Scanner(file);

        Map<String, Set<String>> connections = new HashMap<>();
        List<String> inpList = new ArrayList<>();

        while (scn.hasNext()){
            String inp = scn.nextLine();
            inpList.add(inp);
            String[] inps = inp.split("-");
            connections.putIfAbsent(inps[0], new HashSet<>());
            connections.get(inps[0]).add(inps[1]);

            connections.putIfAbsent(inps[1], new HashSet<>());
            connections.get(inps[1]).add(inps[0]);
        }
        System.out.println(connections.size());

        System.out.println("Ans: " + maxLenPath(connections));


    }

    private static int countTSets(Map<String, Set<String>> connections){
        Set<String> taken = new HashSet<>();

        Set<Set<String>> set = new HashSet<>();

        for(String str: connections.keySet()){
            if(str.charAt(0) == 't'){
                taken.add(str);

                for(String str1: connections.get(str)){
                    if(!taken.contains(str1)){
                        for(String str2: connections.get(str1)){
                            if(!taken.contains(str2) && connections.get(str2).contains(str)){
                                set.add(Set.of(str, str1, str2));
                            }
                        }
                    }
                }


            }
        }

        return set.size();
    }

    private static String maxLenPath(Map<String, Set<String>> connections){

        List<String> nodes = new ArrayList<>(connections.keySet());


        List<String> maxPath = new ArrayList<>();

        for(int i = 0; i < nodes.size(); i++){
            List<String> path = dfs(i, nodes, connections, new ArrayList<>(List.of(nodes.get(i))));

            if(path.size() > maxPath.size()){
                maxPath = path;
            }
        }

        Collections.sort(maxPath);

        return String.join(",", maxPath);


    }

    private static List<String> dfs(int idx, List<String> nodes, Map<String, Set<String>> connections, List<String> path){

        List<String> maxList = new ArrayList<>(path);

        for(int i = idx + 1; i < nodes.size(); i++){
            String node = nodes.get(i);

            boolean isValid = true;

            for(String n: path){
                if(!connections.get(n).contains(node)){
                    isValid = false;
                    break;
                }
            }

            if(!isValid) continue;

            path.add(node);

            List<String> curr = dfs(i, nodes, connections, path);

            if(curr.size() > maxList.size()){
                maxList = curr;
            }

            path.remove(path.size() - 1);
        }

        return maxList;
    }
}