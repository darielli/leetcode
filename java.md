#### [1805. 字符串中不同整数的数目](https://leetcode-cn.com/problems/number-of-different-integers-in-a-string/)

代码：

16ms

```java
public int numDifferentIntegers(String word) {
    word = word + 'a';
    ArrayList<String> ints = new ArrayList<>();
    int index = 0;
    String temp = "";


    while(index <word.length()){
        if(word.charAt(index) >= '0' && word.charAt(index) <= '9'){
            temp += word.charAt(index);
        }
        else{
            if(temp.equals("")){
                index++;
                continue;
            }
            temp = temp.replaceFirst("^0*", "");
            if(!ints.contains(temp)){
                ints.add(temp);
            }
            temp = "";
        }
        index++;

    }

    return ints.size();
}
```

思路：将字符串形式的数字存入ArrayList，通过ArrayList大小来统计数量

注意点

1. 没考虑相同数字的情况（利用正则表达式去掉数字前多余的0）
2. 没考虑数字大小超出int范围（存储字符串而非int数字）
3. 使用 Spring ，str += ‘a’的运行效率明显低于使用SpringBuffer和SpringBuilder
4. equals和length判断字符串是否为空在这题里没有明显区别



所见最优做法

5ms：

```java
public int numDifferentIntegers(String word) {
    String[] words = word.split("[a-z]+");
    Set<String> set = new HashSet<>();
    for(int i=0;i<words.length;i++)
    {
        if(words[i].length()==0) continue;
        int j =0;
        while (words[i].charAt(j)=='0'&&j<words[i].length()-1){
            j++;
        }
        set.add(words[i].substring(j));
    }
    return set.size();
}
```

feature：

* 用正则表达式直接切割字符串
* 用集合排除重复
* substring效率并不算太低（因为必须要用到这个字符串，而非中间变量）（比正则高）
* split效率较低





1ms：

```java
    public int numDifferentIntegers(String word) {
        char[] nums = word.toCharArray();
        HashSet<String> set = new HashSet();
        for(int i = 0;i < nums.length;i++){
            if(nums[i] - '0' <= 9 && nums[i] - '0' >= 0){
                //用一个left记录左边的位置，当左边的字符是 '0' 时，更新i，left
                int left = i;
                while(i < nums.length && nums[left] == '0'){
                    left++;
                    i++; 
                }
                //遍历到的元素是数字时，i++
                while(i < nums.length && nums[i] - '0' >= 0 && nums[i] - '0' <= 9){
                    i++;
                }
                //将子字符串保存下来，添加到HashSet中
                String s = word.substring(left,i);
                set.add(s);
            }
        }
        return set.size();
    }
```





#### [剑指 Offer 30. 包含min函数的栈](https://leetcode-cn.com/problems/bao-han-minhan-shu-de-zhan-lcof/)

16ms，99.95% 

40.1MB，78.12%

```java
import java.util.ArrayList;

class MinStack {
    ArrayList<Integer> stack;
    ArrayList<Integer> mins;
    int min;
    int top;

    /** initialize your data structure here. */
    public MinStack() {
        stack = new ArrayList<>();
        mins = new ArrayList<>();
        min = Integer.MAX_VALUE;
        top = -1;



    }

    public void push(int x) {
        stack.add(x);
        min = Integer.min(min,x);
        mins.add(min);
        top++;
//        System.out.println("push: " + x);

    }

    public void pop() {
//        System.out.println("pop");
//        System.out.println("value:" + stack.get(top));
        stack.remove(top);
        mins.remove(top);
        top--;
        if(top>=0){
            min = mins.get(top);
        }
        else{
            min = Integer.MAX_VALUE;
        }
//        System.out.println("top: " + top);
    }

    public int top() {
//        System.out.println("top");
//        System.out.print("top: " + top);
//        System.out.println(", value:" + stack.get(top));
        return stack.get(top);


    }

    public int min() {
//        System.out.println("min");
//        System.out.print("top: " + top);
//        System.out.println(", value:" + mins.get(top));
        return mins.get(top);

    }
}
```

思路：

既然要求min，pop，push时间复杂度都是O（1）,那就用空间换时间，定义一个mins数组，存储目前为止的最小值。

注意点：

* 一开始使用int数组表示栈，可能会浪费空间，改用ArrayList；
* min初始化应该使用Integer.MAX_VALUE，确保一定是合适的；
* push和pop时注意同步更新mins;
* min表示目前为止整个栈的最小值，所以当pop时注意更新，和mins[top]值一致。



其他思路：

1. B栈为非严格降序栈，则最小值为B栈栈顶元素（占用空间可以减少）

2. 在不占用额外空间的情况下，存差值，即原元素-当前（不包括自己）最小值

   * 例如 原元素序列： 2 0 -2 6
   * 则存入的为         ： 2 -2  -2 8
   * ​             当前最小值2 2 0 -2

   pop时，若栈顶为负，说明当前栈顶的数是原来的最小值，则原来的值为当前最小值，当前最小值应更新为当前最小值-栈顶的数

   若栈顶为正数，说明当前位置原来的数比当前最小值大，所以原来的值为当前最小值+栈顶的数，最小值为当前最小值。

   （未使用额外空间）

