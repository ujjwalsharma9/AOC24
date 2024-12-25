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



        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day11\\input.txt");

        Scanner scn = new Scanner(file);

        String inp = scn.nextLine();

        List<Long> li = Arrays.stream(inp.split(" ")).map(Long::parseLong).collect(Collectors.toList());

        System.out.println("Ans: " + getCount(li));

    }

    private static long getCount(List<Long> li){
        long sum = 0;

        Map<Long, Map<Integer, Long>> map = new HashMap<>();

        for(long x: li){
            sum += count(x, 75, map);
        }

        return sum;
    }

    private static long count(long num, int times, Map<Long, Map<Integer, Long>> map){
        if(times == 0) return 1;

        if(map.containsKey(num) && map.get(num).containsKey(times)){
            return map.get(num).get(times);
        }

        long ans;

        if(num == 0){
            ans = count(1, times - 1, map);
        }
        else{
            String str = String.valueOf(num);
            System.out.println(num + "," + str.length());

            if(str.length() % 2 == 0){
                ans = count(Long.parseLong(str.substring(0, str.length() / 2)), times - 1, map)
                        + count(Long.parseLong(str.substring(str.length() / 2)), times - 1, map);
            }
            else ans = count(num * 2024, times - 1, map);
        }

        map.putIfAbsent(num, new HashMap<>());
        map.get(num).put(times, ans);

        return ans;
    }
}