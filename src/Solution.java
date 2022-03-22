import java.util.Deque;
import java.util.LinkedList;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length == 0 || k == 0){
            return new int[0];
        }
        Deque<Integer> deque = new LinkedList<>();
        int[] result = new int[nums.length - k + 1];

        for(int j = 0, i = 1 - k; j < nums.length; i++, j++){
            //如果不在窗口内删掉第一个
            if(i > 0 && deque.peekFirst() == nums[i-1]){
                deque.removeFirst();
            }
            //保持递减
            while(!deque.isEmpty() && deque.peekLast() < nums[j]){
                deque.removeLast();
            }
            deque.addLast(nums[j]);

            //记录最大值
            if(i >= 0){
                result[i] = deque.peekFirst();
            }
        }
        return result;

    }
}