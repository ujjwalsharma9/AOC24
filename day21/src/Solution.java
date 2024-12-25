import java.util.*;

public class Solution {

    Keypad keypad = new Keypad();

    public List<StringBuilder> numSequence(String seq){
        char start = 'A';
        List<StringBuilder> sbList = new ArrayList<>();
        sbList.add(new StringBuilder());

        for(char ch: seq.toCharArray()){
            List<StringBuilder> btn = keypad.numMemo.get(start).get(ch);
            List<StringBuilder> newSbList = new ArrayList<>();

            for(StringBuilder sb: sbList){
                for(StringBuilder sb1: btn){
                    StringBuilder newSb = new StringBuilder(sb);
                    newSb.append(sb1);
                    newSb.append("A");
                    newSbList.add(newSb);
                }
            }

            sbList = newSbList;
            start = ch;

        }

        return sbList;
    }

    public List<StringBuilder> dirSequence(String seq){
        char start = 'A';
        List<StringBuilder> sbList = new ArrayList<>();
        sbList.add(new StringBuilder());

        for(char ch: seq.toCharArray()){
            List<StringBuilder> btn = keypad.dirMemo.get(start).get(ch);
            List<StringBuilder> newSbList = new ArrayList<>();

            for(StringBuilder sb: sbList){
                for(StringBuilder sb1: btn){
                    StringBuilder newSb = new StringBuilder(sb);
                    newSb.append(sb1);
                    newSb.append("A");
                    newSbList.add(newSb);
                }
            }

            sbList = newSbList;
            start = ch;

        }

        return sbList;
    }

    Solution(){
        chArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', '>', '<', '^', 'v'};

        for(int i = 0; i < chArr.length; i++){
            idxMap.put(chArr[i], i);
        }
    }


    long[][][] memo;

    int targDepth;
    char[] chArr;

    Map<Character, Integer> idxMap  = new HashMap<>();

    public Map<String, Long> getMinLength(List<String> codes, int depth){
        this.targDepth = depth;

        memo = new long[chArr.length][chArr.length][targDepth + 1];

        for(long[][] arr: memo){
            for(long[] arr1: arr){
                Arrays.fill(arr1, -1);
            }
        }

        Map<String, Long> result = new HashMap<>();

        for(String code: codes){
            long len = dp(idxMap.get('A'), idxMap.get(code.charAt(0)), targDepth);
            for(int i = 1; i < code.length(); i++){
                len += dp(idxMap.get(code.charAt(i - 1)),idxMap.get(code.charAt(i)), targDepth);
            }

            result.put(code, len);
        }

        return result;

    }



    private long dp(int src, int tar, int depth){
        if(depth == 0){
            return 1;
        }

        if(memo[src][tar][depth] == -1){
            Map<Character, Map<Character, List<StringBuilder>>> map;
            if(depth == targDepth){
                map = keypad.numMemo;
            }
            else{
                map = keypad.dirMemo;
            }

            char c1 = chArr[src];
            char c2 = chArr[tar];

            List<StringBuilder> strList = map.get(c1).get(c2);

            long minLen = Long.MAX_VALUE;

            for(StringBuilder sb: strList){
                StringBuilder newSb = new StringBuilder(sb);
                newSb.append("A");
                long len = 0;
                char start = 'A';
                for(char ch: newSb.toString().toCharArray()){
                    len += dp(idxMap.get(start), idxMap.get(ch), depth - 1);
                    start = ch;
                }

                minLen = Math.min(minLen, len);
            }

            memo[src][tar][depth] = minLen;
        }

        return memo[src][tar][depth];
    }
}
