import java.util.ArrayList;
import java.util.Arrays;

public class LongestIncreasingSubsequence300 {

    public static int lengthOfLIS(int[] nums) {
        if(nums.length==0){
            return 0;
        }
        int[] length = new int[nums.length];
        Arrays.fill(length,0);
        length[0] = 1;
        for(int i=1;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[j]<nums[i]){
                    length[i] = Math.max(length[j],length[i]);
                }
            }
            length[i]++;
        }

        int max = 0;
        for(int i=0;i<nums.length;i++){
            max = Math.max(max,length[i]);
        }

        return max;

    }

    public static void main(String[] args){
        int[] temp = new int[]{0,1,0,3,2,3};
        System.out.print(lengthOfLIS(temp));
    }

}
