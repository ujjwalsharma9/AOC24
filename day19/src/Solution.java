import java.util.Arrays;
import java.util.List;

public class Solution {

    private final List<String> available;
    private String target;

    private long[] memo;

    public Solution(List<String> available){
        this.available = available;
    }

//   public Boolean isPossible(String target){
//        this.target = target;
//        this.memo = new Boolean[target.length()];
//        return dp(0);
//   }

    public long countWays(String target){
        this.target = target;
        this.memo = new long[target.length()];

        Arrays.fill(memo, -1);

        return dp(0);
    }

   private long dp(int idx){
        if(idx >= target.length()){
            return 1;
        }

        if(memo[idx] == -1){
            long ans = 0;

            for(String str: available){
                int I = idx;
                boolean isValid = true;
                for(int i = 0; i < str.length(); i++){
                    if(I >= target.length() || str.charAt(i) != target.charAt(I)){
                        isValid = false;
                        break;
                    }
                    I++;
                }

                if(isValid){
                    ans = ans + dp(I);
                }
            }

            memo[idx] = ans;
        }

        return memo[idx];
   }


}
