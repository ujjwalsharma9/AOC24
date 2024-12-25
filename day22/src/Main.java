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

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day22\\input.txt");

        Scanner scn = new Scanner(file);

        List<Long> numbers = new ArrayList<>();

        while (scn.hasNext()){
            numbers.add(Long.parseLong(scn.nextLine()));
        }

        System.out.println("Ans: " + getMostBananas(numbers, 2000));


    }

    private static long sumSecret(List<Long> numbers, int times){
        long sum = 0;
        for(long num: numbers){
            long secret = num;
            for(int i = 0; i < times; i++){
                secret = (secret ^ (secret * 64)) % 16777216;
                secret = (secret ^ (secret / 32)) % 16777216;
                secret = (secret ^ (secret * 2048)) % 16777216;
            }

            System.out.println(num + ": " + secret);

            sum += secret;
        }

        return sum;

    }

    private static int getMostBananas(List<Long> numbers, int times){
        List<List<int[]>> pricesList = new ArrayList<>();


        for(long num: numbers){
            long secret = num;
            List<int[]> temp = new ArrayList<>();
            pricesList.add(temp);
            for(int i = 0; i < times; i++){
                int prev = (int)(secret % 10);
                secret = (secret ^ (secret * 64)) % 16777216;
                secret = (secret ^ (secret / 32)) % 16777216;
                secret = (secret ^ (secret * 2048)) % 16777216;
                int price = (int)(secret % 10);
                temp.add(new int[]{price, price - prev});
            }

            System.out.println(num + ": " + secret);



        }

        return getMostBananas(pricesList);
    }

    private static int getMostBananas(List<List<int[]>> pricesList){
        int max = 0;

        Map<Integer, Map<List<Integer>, Integer>> map = new HashMap<>();

        for(int i = 0; i < pricesList.size(); i++){
            map.put(i, getMap(pricesList.get(i)));
        }

        for(int d1 = -9; d1 <= 9; d1++){
            for(int d2 = -9; d2 <= 9; d2++){
                for(int d3 = -9; d3<= 9; d3++){
                    for(int d4 = -9; d4 <= 9; d4++){
                        int sum = 0;
                        List<Integer> li = List.of(d1, d2, d3, d4);
                        for(int i = 0; i < pricesList.size(); i++){

                            sum += map.get(i).getOrDefault(li, 0);
                        }

                        max = Math.max(max, sum);
                    }
                }
            }
        }

        return max;
    }

    private static Map<List<Integer>, Integer> getMap(List<int[]> prices){
        Map<List<Integer>, Integer> priceMap = new HashMap<>();

        for(int i = 0; i <= prices.size() - 4; i++){
            List<Integer> li = List.of(prices.get(i)[1], prices.get(i + 1)[1], prices.get(i + 2)[1],
                    prices.get(i + 3)[1]);

            if(!priceMap.containsKey(li)){
                priceMap.put(li, prices.get(i + 3)[0]);
            }
        }

        return priceMap;

    }
}