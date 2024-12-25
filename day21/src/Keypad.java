import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Keypad {

    char[][] numpad = new char[][]{{'7', '8', '9'}, {'4', '5', '6'}, {'1', '2', '3'}, {'X', '0', 'A'}};

    char[][] dirpad = new char[][]{{'X', '^', 'A'}, {'<', 'v', '>'}};



    Map<Character, Map<Character, List<StringBuilder>>> numMemo = new HashMap<>();
    Map<Character, Map<Character, List<StringBuilder>>> dirMemo = new HashMap<>();

    Keypad(){

        for(int i = 0; i < numpad.length; i++){
            for(int j = 0; j < numpad[0].length; j++){
                char c1 = numpad[i][j];
                numMemo.put(c1, new HashMap<>());
                for(int k = 0; k < numpad.length; k++){
                    for(int l = 0; l < numpad[0].length; l++){
                        char c2 = numpad[k][l];

                        List<StringBuilder> strings = new ArrayList<>();
                        generate(i, j, k, l, numpad, new StringBuilder(), strings);
                        numMemo.get(c1).put(c2, strings);
                    }
                }
            }
        }

        for(int i = 0; i < dirpad.length; i++){
            for(int j = 0; j < dirpad[0].length; j++){
                char c1 = dirpad[i][j];
                dirMemo.put(c1, new HashMap<>());
                for(int k = 0; k < dirpad.length; k++){
                    for(int l = 0; l < dirpad[0].length; l++){
                        char c2 = dirpad[k][l];

                        List<StringBuilder> strings = new ArrayList<>();
                        generate(i, j, k, l, dirpad, new StringBuilder(), strings);
                        dirMemo.get(c1).put(c2, strings);
                    }
                }
            }
        }

    }

    private void generate(int srcRow, int srcCol, int destRow, int destCol, char[][] arr, StringBuilder curr, List<StringBuilder> all ){
        if(srcRow == destRow && srcCol == destCol){
            all.add(curr);
            return;
        }

        if(arr[srcRow][srcCol] == 'X'){
            return;
        }

        if(srcRow < destRow){
            StringBuilder newSb = new StringBuilder(curr);
            newSb.append("v");
            generate(srcRow + 1, srcCol, destRow, destCol, arr, newSb, all);
        }
        else if(srcRow > destRow){
            StringBuilder newSb = new StringBuilder(curr);
            newSb.append("^");
            generate(srcRow - 1, srcCol, destRow, destCol, arr, newSb, all);
        }

        if(srcCol < destCol){
            StringBuilder newSb = new StringBuilder(curr);
            newSb.append(">");
            generate(srcRow, srcCol + 1, destRow, destCol, arr, newSb, all);
        }
        else if(srcCol > destCol){
            StringBuilder newSb = new StringBuilder(curr);
            newSb.append("<");
            generate(srcRow, srcCol - 1, destRow, destCol, arr, newSb, all);
        }


    }
}
