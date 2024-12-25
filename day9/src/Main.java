import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day9\\input.txt");

        Scanner scn = new Scanner(file);

        String inp = scn.next();

        System.out.println("Ans:" + getPosCount2(inp));
    }

    private static long getPosCount(String inp){
        PriorityQueue<int[]> pqFile = new PriorityQueue<>((p1, p2) -> p2[0] - p1[0]);

        PriorityQueue<Integer> pqFree = new PriorityQueue<>();

        int bId = 0;
        int dIdx = 0;

        for(int i = 0; i < inp.length(); i++){
            int num = inp.charAt(i) - '0';
            if((i % 2) == 0){
                for(int j = 0; j < num; j++){
                    pqFile.add(new int[]{dIdx++, bId});
                }
                bId++;
            }
            else{
                for(int j = 0; j < num; j++){
                    pqFree.add(dIdx++);
                }
            }
        }

        while(!pqFree.isEmpty() && pqFree.peek() < pqFile.peek()[0]){
            int sp = pqFree.remove();
            int[] last = pqFile.remove();

            pqFile.add(new int[]{sp, last[1]});
        }

        long sum = 0;

        while (!pqFile.isEmpty()){
            int[] block = pqFile.remove();
            sum += (long) block[0] * block[1];
        }

        return sum;
    }

    private static long getPosCount2(String inp){

        List<Block> files = new ArrayList<>(); //start, sz

        List<Block> freeSpace = new ArrayList<>(); //start, sz

        int dIdx = 0;

        for(int i = 0; i < inp.length(); i++){
            int num = inp.charAt(i) - '0';
            if((i % 2) == 0){
               files.add(new Block(dIdx, num));
            }
            else{
                freeSpace.add(new Block(dIdx, num));
            }
            dIdx += num;
        }

        int sz = files.size();



        for(int i = sz - 1; i >= 0; i--){
            Block b = files.get(i);

            for (Block f : freeSpace) {
                if(f.start > b.start) break;

                if (f.size >= b.size) {
                    f.size -= b.size;
                    b.start = f.start;
                    f.start += b.size;
                    break;
                }
            }
        }

        long sum = 0;

        for(int i = 0; i < sz; i++){
            Block b = files.get(i);

            for(int j = b.start; j < b.start + b.size; j++){
                sum += (long) i * j;
            }
        }

        return sum;
    }
}