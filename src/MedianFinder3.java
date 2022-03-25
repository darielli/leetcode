import java.util.ArrayList;
public class MedianFinder3{
    public static void main(String[] args){
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(6);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(10);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(2);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(6);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(5);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(0);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(6);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(1);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(0);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(0);
        System.out.println(medianFinder.findMedian());
        for(int i=0;i<medianFinder.size;i++){
            System.out.print(medianFinder.nums.get(i) + " ");
        }

    }
}

class MedianFinder {

    /** initialize your data structure here. */
    int size;
    int left;
    int right;
    ArrayList<Integer> nums;
    public MedianFinder() {
        size = 0;
        left = -1;
        right = -1;
        nums = new ArrayList<>();
    }

    public void addNum(int num) {
        if(size == 0){
            left++;
            right++;
            size++;
            nums.add(num);
            return;
        }
        else if(left == right){
            right++;
        }
        else{
            left++;
        }
        int a = 0, b = size - 1;
        size++;
        if(num >= nums.get(b)){
            nums.add(num);
            return;
        }
        else if(num <= nums.get(a)){
            nums.add(0,num);
            return;
        }
        while(a < b){
            int middle = (a + b) / 2;
            if(nums.get(middle) < num ){
                a = middle + 1;
            }
            else if(nums.get(middle) > num){
                b = middle - 1;
            }
            else{
                b = middle;
                a = middle;
                break;
            }
        }
        if(num > nums.get(b)){
            nums.add(b + 1, num);
        }
        else{
            nums.add(b , num);
        }



    }

    public double findMedian() {
        if(left == -1 && right == -1){
            return 0;
        }
        else{

            return 1.0 * (nums.get(left) + nums.get(right)) / 2;
        }

    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */