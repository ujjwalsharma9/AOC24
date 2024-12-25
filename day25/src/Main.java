import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        File file = new File("C:\\Users\\ujjwa\\Documents\\AOC24\\day25\\input.txt");

        Scanner scn = new Scanner(file);

        List<List<String>> gridList = new ArrayList<>();

        while (scn.hasNext()){
            List<String> grid = new ArrayList<>();
            String str = scn.nextLine();

            while (!str.isBlank() && scn.hasNext()){
                grid.add(str);
                str = scn.nextLine();
            }

            gridList.add(grid);

        }
        System.out.println(gridList.size());

        System.out.println("Ans: "+ getCount(gridList));


    }


    private static int getCount(List<List<String>> gridList){
        List<List<Integer>> lock = new ArrayList<>();
        List<List<Integer>> key = new ArrayList<>();

        for(List<String> grid: gridList){
            List<Integer> li = new ArrayList<>();
            for(int j = 0; j < grid.get(0).length(); j++){
                int count = 0;
                for (String s : grid) {
                    if (s.charAt(j) == '#') {
                        count++;
                    }
                }
                li.add(count);
            }
            if(grid.get(0).charAt(0) == '#'){
                lock.add(li);
            }
            else key.add(li);
        }

        int validCount = 0;
        int sz = gridList.get(0).size();

        for(List<Integer> li: lock){
            for(List<Integer> li1: key){
                boolean isValid = true;
                for(int i = 0; i < li.size(); i++){
                    if(li.get(i) + li1.get(i) > sz){
                        isValid = false;
                        break;
                    }
                }

                if(isValid){
                    validCount++;
                }
            }
        }

        return validCount;
    }
}