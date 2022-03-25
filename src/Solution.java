import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;

class Solution {
    public static void main(String[] args){
//        System.out.print("当前时间：");
//        System.currentTimeMillis();
//        String s = "sdklasdjfhdlkjghdsg1ds3ag4165ds5g34asd2v1as35dg1sd32v13df1v32fd13g54fd32v13fd4g32fdvdfgafdvvfdagfdgf";
//        for(long i = 1; i < 100000000000L; i = i * 10){
//            s1(i);
//            s2(i);
//        }
//        System.out.println(findNthDigit(1000000000));
        ArrayList<Integer> t = new ArrayList<>();
        t.add(5);
        t.add(8);
        t.add(0,6);
        t.add(2,9);
        for(int i=0;i<t.size();i++){
            System.out.println(t.get(i));
        }

    }

    public static int findNthDigit(int n) {
        int maxIndex = 9;
        if(n <= 9){
            return n;
        }
        int[] nums = new int[maxIndex];
        nums[0] = 0;
        for(int i=1;i<maxIndex;i++){
            nums[i] = i * (int)Math.pow(10,i - 1) * 9 + nums[i - 1];
        }
        int right = 0;
        for(int i=0;i<maxIndex;i++){
            if(nums[right] < n){
                right++;
            }
        }

        int temp = n - nums[right - 1];
        int reminder = temp % right;
        int quotient = temp / right;
        if(reminder == 0){
            reminder = right;
            quotient--;
        }

        int res = (int)Math.pow(10,right - 1) + quotient;
        return Integer.parseInt("" + String.valueOf(res).charAt(reminder - 1));
    }

    public static void s1(long times){
        System.out.println("for 递增方法");
        System.out.print("开始时间：");
        Long t1= System.currentTimeMillis();
        System.out.println(t1);
        long a;
        for(long i=0;i<times;i++){
            a = i;
        }
        System.out.print("结束时间：");
        Long t2= System.currentTimeMillis();
        System.out.println(t2);
        System.out.println("for 递增方法：运行次数：" + times + "，总计用时：" + (t2-t1));
    }

    public static void s2(long times){
        System.out.println("for 递减方法");
        System.out.print("开始时间：");
        Long t1= System.currentTimeMillis();
        System.out.println(t1);
        long a;
        for(long i=times;i>=0;i--){
            a = i;
        }
        System.out.print("结束时间：");
        Long t2= System.currentTimeMillis();
        System.out.println(t2);
        System.out.println("for 递减方法：运行次数：" + times + "，总计用时：" + (t2-t1));
    }
    public static int lengthOfLongestSubstring(String s) {
        String str = s;
        if (str == null || str.length() == 0){ return 0;}

        HashMap<Character, Integer> temp = new HashMap<>();
        char[] chars = str.toCharArray();

        int res = 0, start = 0;
        for (int i = 0; i < chars.length; i++) {
            if (temp.containsKey(chars[i])) {
                start = Math.max(temp.put(chars[i], i) + 1, start);
            }

            temp.put(chars[i], i);
            res = Math.max(res, i - start + 1);
        }

        return res;

    }
}