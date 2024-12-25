import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static int width = 101;
    static int length = 103;

    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day14\\input.txt");

        Scanner scn = new Scanner(file);

        List<Bot> botList = new ArrayList<>();

        while(scn.hasNext()){
            String inp = scn.nextLine();
            String[] inps = inp.split(" ");
            botList.add(new Bot(getValues(inps[0]), getValues(inps[1])));
        }

        System.out.println("Ans: " + getTree(botList));


    }

    private static long getFactor(List<Bot> botList){

        long q1 = 0;
        long q2 = 0;
        long q3 = 0;
        long q4 = 0;

        for(Bot bot: botList){
            int newX = (((bot.pos[0] + (100 * bot.velocity[0])) % width) + width) % width;
            int newY = (((bot.pos[1] + (100 * bot.velocity[1])) % length) + length) % length;

            if(newX < width / 2){
                if(newY < length / 2){
                    q1 += 1;
                }
                else if (newY > length / 2) {
                    q2 += 1;
                }
            }
            else if (newX > width / 2) {
                if(newY < length / 2){
                    q3 += 1;
                }
                else if (newY > length / 2) {
                    q4 += 1;
                }
            }

        }

        System.out.println(q1 + "," + q2 + "," + q3 + "," + q4);

        return q1 * q2 * q3 * q4;
    }

    private static int getTree(List<Bot> botList){


        for(int i = 1; i <= 10000; i++){
            System.out.println(i);
            Map<Integer, Set<Integer>> mapSet = new HashMap<>();
            boolean found = true;
            for(Bot bot: botList) {
                int newX = (((bot.pos[0] + (i * bot.velocity[0])) % width) + width) % width;
                int newY = (((bot.pos[1] + (i * bot.velocity[1])) % length) + length) % length;

                if(mapSet.containsKey(newX) && mapSet.get(newX).contains(newY)){
                    found = false;
                    break;
                }
                else{
                    mapSet.putIfAbsent(newX, new HashSet<>());
                    mapSet.get(newX).add(newY);
                }
            }

            if(found){
                printTree(mapSet);
                return i;
            }
        }

        return -1;
    }

    private static void printTree(Map<Integer, Set<Integer>> mapSet){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
                if(mapSet.containsKey(i) && mapSet.get(i).contains(j)){
                    System.out.print("*");
                }
                else System.out.print("-");
            }

            System.out.println();
        }

    }

    private static int[] getValues(String str){
        return  Arrays.stream(str.split("=")[1].split(",")).map(Integer::parseInt).mapToInt(x -> x).toArray();
    }
}