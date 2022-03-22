#### 1. [1805. 字符串中不同整数的数目](https://leetcode-cn.com/problems/number-of-different-integers-in-a-string/)

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





#### 2. [剑指 Offer 30. 包含min函数的栈](https://leetcode-cn.com/problems/bao-han-minhan-shu-de-zhan-lcof/)

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

   

#### 3. [剑指 Offer 09. 用两个栈实现队列](https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/)



```java
class CQueue {
    Stack<Integer> stack = new Stack<>();
    Stack<Integer> assistStack = new Stack<>();

    public CQueue() {

    }

    public void appendTail(int value) {
        if(!assistStack.empty()){
            while(!assistStack.empty()){
                stack.push(assistStack.pop());
            }
        }

        stack.push(value);


    }

    public int deleteHead() {
        if(!stack.empty()){
            while(!stack.empty()){
                assistStack.push(stack.pop());
            }
        }
        if(assistStack.empty()){
            return -1;
        }
        return assistStack.pop();

    }
}

/**
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */
```

思路：

1. 两个栈，出的时候将A栈内容弹出到B栈，返回B栈最顶端元素，如果要入栈，则将B元素转至A，再入栈。
2. 中规中矩，但是速度很慢，因为Stack继承了Vector接口，而Vector底层是一个Object[]数组，那么就要考虑空间扩容和移位的问题了
3. 可以考虑用LinkList代替stack



#### 4. [剑指 Offer 06. 从尾到头打印链表](https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/)

0ms，100%

39.1MB，33%

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public int[] reversePrint(ListNode head) {
        int size = 0;
        ListNode temp = head;
        while(temp!=null){
            size++;
            temp = temp.next;
        }
        int[] result = new int[size];
        for(int i=0;i<size;i++){
            result[size-i-1] = head.val;
            head = head.next;
        }
        return result;
    }
}
```

思路：

一开始打算使用arraylist存储元素，后来发现没必要，直接遍历即可得到size，然后倒序存入即可。



也可使用ArrayList，往前插入，这样可以减少一次遍历。

#### 5. [剑指 Offer 24. 反转链表](https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/)

0ms，100%

38MB， 85.51%

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head==null){
            return null;
        }
        
        int size = 1;
        ListNode temp = head;
        while(temp.next!=null){
            size++;
            temp = temp.next;
        }
        int[] result = new int[size];
        for(int i=0;i<size;i++){
            result[size-i-1] = head.val;
            head = head.next;
        }
        int index=0;
        ListNode newHead = new ListNode(result[index]);
        ListNode indexNode = newHead;
        index++;
        while(index<size){
            temp = new ListNode(result[index]);
            indexNode.next = temp;
            index++;
            indexNode = indexNode.next;
        }
        
        return newHead;

    }
}
```

思路：

沿用上一题代码，保存逆序的值，然后重新构建一个链表



思路二：

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head == null){
            return head;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        ListNode next = null;
        pre.next = null;
        while(cur != null){
            next = cur.next;
            cur.next = pre;

            pre = cur;
            cur = next;

        }
        return pre;

    }
}
```

0ms，100%

上一种做法引入了别的数据结构，所以不算是很好，这一种用三指针的方式实现链表反转

#### 6. [剑指 Offer 35. 复杂链表的复制](https://leetcode-cn.com/problems/fu-za-lian-biao-de-fu-zhi-lcof/)

5ms，6.11%

37.9MB，78.52%

```java
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/
class Solution {
    public Node copyRandomList(Node head) {
                if(head==null){
            return null;
        }
        Node temp = head;
        Node newHead = new Node(temp.val);
        Node newTemp = newHead;
        while(temp.next != null){
            temp = temp.next;
            newTemp.next=new Node(temp.val);
            newTemp = newTemp.next;
        }
        
        temp = head;

        newTemp = newHead;
        
        while(temp!=null){
            Node temp2 =head;
            Node newTemp2 = newHead;
            if(temp.random!=null){
                while(temp.random != temp2){
                    temp2 = temp2.next;
                    newTemp2 = newTemp2.next;
                }
                newTemp.random = newTemp2;
            }

            temp = temp.next;
            newTemp = newTemp.next;
            
        }
        return newHead;
        
    }
}
```

思路：

没有想到简单的方法，直接遍历求解

其他思路：

回溯 + 哈希表

思路及算法

本题要求我们对一个特殊的链表进行深拷贝。如果是普通链表，我们可以直接按照遍历的顺序创建链表节点。而本题中因为随机指针的存在，当我们拷贝节点时，「当前节点的随机指针指向的节点」可能还没创建，因此我们需要变换思路。一个可行方案是，我们利用回溯的方式，让每个节点的拷贝操作相互独立。对于当前节点，我们首先要进行拷贝，然后我们进行「当前节点的后继节点」和「当前节点的随机指针指向的节点」拷贝，拷贝完成后将创建的新节点的指针返回，即可完成当前节点的两指针的赋值。

具体地，我们用哈希表记录每一个节点对应新节点的创建情况。遍历该链表的过程中，我们检查「当前节点的后继节点」和「当前节点的随机指针指向的节点」的创建情况。如果这两个节点中的任何一个节点的新节点没有被创建，我们都立刻递归地进行创建。当我们拷贝完成，回溯到当前层时，我们即可完成当前节点的指针赋值。注意一个节点可能被多个其他节点指向，因此我们可能递归地多次尝试拷贝某个节点，为了防止重复拷贝，我们需要首先检查当前节点是否被拷贝过，如果已经拷贝过，我们可以直接从哈希表中取出拷贝后的节点的指针并返回即可。

在实际代码中，我们需要特别判断给定节点为空节点的情况。

```java
class Solution {
    Map<Node, Node> cachedNode = new HashMap<Node, Node>();

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        if (!cachedNode.containsKey(head)) {
            Node headNew = new Node(head.val);
            cachedNode.put(head, headNew);
            headNew.next = copyRandomList(head.next);
            headNew.random = copyRandomList(head.random);
        }
        return cachedNode.get(head);
    }
}
```





**鸽了好久之后又来更新了**





#### 7. [240. 搜索二维矩阵 II](https://leetcode-cn.com/problems/search-a-2d-matrix-ii/)

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length == 0){
            return false;
        }

        for(int i = 0, j = matrix[0].length - 1; i < matrix.length && j >= 0; ){
            if(matrix[i][j] > target){
                j--;
            }
            else if(matrix[i][j] < target){
                i++;
            }
            else{
                return true;
            }
        }
        
        return false;
    }
        
    
}
```

主要思路：抓住每行递增，每列递增的特点，每个矩阵右上角的数是每行的最大值，每列的最小值，所以用右上角的值和target比较，从右上角比较到左下角，就可以得到结果了！复杂度O(n)

用时 100%



#### 8. [剑指 Offer 05. 替换空格](https://leetcode-cn.com/problems/ti-huan-kong-ge-lcof/)

```java
class Solution {
    public String replaceSpace(String s) {
        StringBuilder res = new StringBuilder();
        for(int i = 0;i < s.length(); i++){
            if(s.charAt(i) == ' '){
                res.append("%20");
            }
            else{
                res.append(s.charAt(i));
            }
        }
        return res.toString();

    }
}
```

遍历，遇到空格填上%20，否则就加上原字符。

使用StringBuilder原因：StringBuilder不定长，且效率较高

用时 100%



#### 9. [剑指 Offer 07. 重建二叉树](https://leetcode-cn.com/problems/zhong-jian-er-cha-shu-lcof/)

我的题解

```java
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder,inorder,0,preorder.length-1,0,inorder.length-1);

    }


    public TreeNode buildTree(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd){
        if(preStart == preEnd){
            return new TreeNode(preorder[preStart]);
        }
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTree(preorder,inorder,0,preorder.length-1,0,inorder.length-1);


    }


    public TreeNode buildTree(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd){
        if(preStart == preEnd){
            return new TreeNode(preorder[preStart]);
        }
        int val = preorder[preStart];
        int index;
        for(index = inStart;index <= inEnd;index++){
            if(inorder[index] == val){
                break;
            }
        }
        int leftLength = index - inStart;
        int rightLength = inEnd - index;
        TreeNode node = new TreeNode(val);
        node.left = buildTree(preorder,inorder,preStart+1,preStart+leftLength,inStart,inStart+leftLength - 1);
        node.right = buildTree(preorder,inorder,preStart+leftLength+1,preEnd, index+1,inEnd);
        return node;
    }
        int index;
        for(index = inStart;index <= inEnd;index++){
            if(inorder[index] == val){
                break;
            }
        }
        int leftLength = index - inStart;
        int rightLength = inEnd - index;
        TreeNode node = new TreeNode(val);
        node.left = buildTree(preorder,inorder,preStart+1,preStart+leftLength,inStart,inStart+leftLength - 1);
        node.right = buildTree(preorder,inorder,preStart+leftLength+1,preEnd, index+1,inEnd);
        return node;
    }
```



超过 37%

主要存在的问题：

1. 直接写没有提示的时候会搞错一些常用函数/字段，比如数组获取长度是用.length，字段；字符串获取长度是用.length() 函数
2. ​        int val = preorder[preStart];直接写成了int val = preorder[0];，递归中不应该出现很多魔数



较好的题解：

```java
class Solution {
    private Map<Integer, Integer> indexMap;

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
        if (preorder_left > preorder_right) {
            return null;
        }

        // 前序遍历中的第一个节点就是根节点
        int preorder_root = preorder_left;
        // 在中序遍历中定位根节点
        int inorder_root = indexMap.get(preorder[preorder_root]);
        
        // 先把根节点建立出来
        TreeNode root = new TreeNode(preorder[preorder_root]);
        // 得到左子树中的节点数目
        int size_left_subtree = inorder_root - inorder_left;
        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_root - 1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }
}

```

时间：99.96%

与我的方法的改进之处：用hashMap提高了遍历的效率





此外，另一个题解：

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length == 0){
            return null;
        }
    Map<Integer, Integer> preIndex = new HashMap<>();
    for (int i = 0; i < preorder.length; i++) {
        preIndex.put(preorder[i], i);
    }

    return buildTree(preIndex, inorder, 0, inorder.length - 1);


    }


private TreeNode buildTree(Map<Integer, Integer> preIndex, int[] in, int start, int end) {
    if (start == end) {
        return new TreeNode(in[start]);
    }
    int indexOfRoot = start;
    for (int i = start; i <= end; i++) {
        if (preIndex.get(in[i]) < preIndex.get(in[indexOfRoot])) {
            indexOfRoot = i;
        }
    }
    TreeNode root = new TreeNode(in[indexOfRoot]);
    if (start <= indexOfRoot - 1) root.left = buildTree(preIndex, in, start, indexOfRoot - 1);
    if (indexOfRoot + 1 <= end) root.right = buildTree(preIndex, in, indexOfRoot + 1, end);
    return root;
}
}
```

时间：5%，效率很低

和上一题题解的区别，传递的不是数组而是hashMap，导致效率大幅下降





#### 10. [154. 寻找旋转排序数组中的最小值 II](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array-ii/)

我的解法：for循环遍历nums[i] >num[i+1]

速度：100%，复杂度O(n)



优解：

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > nums[right]) left = mid + 1;
            else if (nums[mid] < nums[right]) right = mid;
            else right = right - 1;
        }
        return nums[left];
    }
}
```

思路：由于数据存在一定的规律，显然可以找到效率更高的做法，这一题就可以使用二分法查找，

但难点在于出现重复值时如何处理，本解巧妙之处在于**`right--`**；既可以保证不越界、也可以保证不会丢失最小值，同时，可以缩小求解数组的长度，保证了不会死循环。

虽然for循环遍历也是0ms，100%，但是二分法的时间复杂度为O(logn)，显然要优于遍历的。

#### 11. [剑指 Offer 10- I. 斐波那契数列](https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/)

我的题解：



```java
class Solution {
    public int fib(int n) {
        if(n == 0){
            return 0;
        }
        else if(n == 1){
            return 1;
        }
        else{
            return fib(n - 1) + fib(n - 2);
        }

    }
}
```

问题：超时，一旦n较大，递归的方法速度就很慢

题解2：

```java
class Solution {
    public int fib(int n) {
        if(n == 0){
            return 0;
        }
        else if(n == 1){
            return 1;
        }
        int first = 0;
        int second = 1;
        int third = 1;
        for(int i = 0;i < n - 1;i++){
            third = (first + second) % 1000000007;
            first = second;
            second = third;
        }
        return third;

    }
}
```

不递归，也能实现斐波那契的加法，for足够

时间上O(n),0ms，100%

出现的问题：

1. 没看清题目，结果需要模1000000007
2. 只在最后一次取模，中间没有取模导致超出int范围
3. 本来中间使用long，但是long范围也不够，所以也会超出范围
4. 使用long，返回结果时没有类型转换成int，导致类型错误。





#### 12. [剑指 Offer 16. 数值的整数次方](https://leetcode-cn.com/problems/shu-zhi-de-zheng-shu-ci-fang-lcof/)

我的题解：

```java
class Solution {
    public double myPow(double x, int n) {
        double x2 = x;
        boolean negative = n < 0;
        n = negative ? -n : n;
        if(x == 0 || x == 1){
            return x ;
        }
        if(n == 0){
            return 1;
        }
        for(int i = 0; i < n - 1; i++){
            x2 = x2 * x;
        }
        return negative? 1 / x2 : x2;
    }
}
```

问题：超出时间限制，所以在for循环除可以改进

解法2：

```java
class Solution {
    public double myPow(double x, int n) {
        if(x == 0 || x == 1){
            return x;
        }
        boolean negative = n < 0 ? true : false;
        n = negative ? -n : n;
        double res = posiPow(x,n);
        return negative ? 1 / res : res;
    }

    public double posiPow(double x, int n){
        if(n == 0){
            return 1;
        }
        else if( n == 1){
            return x;
        }
        if(n % 2 == 0){
            return posiPow(x, n/2) * posiPow(x, n/2);
        }
        else{
            return posiPow(x,n/2) * posiPow(x, n/2) * x;
        }
    }
}
```

解决了for循环的问题，但是没有解决超时问题

复杂度从O(n) --> O((logn)2)

思路：posiPow(x, n/2) 不需要求那么多遍

优解：

```java
class Solution {
    public double myPow(double x, int n) {
        if(x == 0 || x == 1){
            return x;
        }
        boolean negative = n < 0 ? true : false;
        n = negative ? -n : n;
        double res = posiPow(x,n);
        return negative ? 1 / res : res;
    }

    public double posiPow(double x, int n){
        if(n == 0){
            return 1;
        }
        else if( n == 1){
            return x;
        }
        double t = posiPow(x, n/2);
        if(n % 2 == 0){
            return  t * t;
        }
        else{
            return t * t * x;
        }
    }
}
```

时间复杂度O(logn) 0ms 100%





#### 13. [剑指 Offer 15. 二进制中1的个数](https://leetcode-cn.com/problems/er-jin-zhi-zhong-1de-ge-shu-lcof/)



我的题解：

```java
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count = 0;
        while(n != 1){
            count += n % 2;
            n = n / 2;
        }
        count++;
        return count;
        
    }
}
```

问题：超时

优解：

```java
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
    int mask = 0x01;

    int res = 0;
    int t = n;
    while (t != 0) {
        if ((t & mask) == 1) {
            res++;
        }
        t = t >>> 1;
    }

    return res;
        
    }
}
```

0ms 100%，时间复杂度O(N),N是n的二进制位数

所以两种做法的区别就在于位运算和普通运算的效率上





[解析](https://leetcode-cn.com/problems/er-jin-zhi-zhong-1de-ge-shu-lcof/solution/er-jin-zhi-zhong-1de-ge-shu-by-leetcode-50bb1/)

```java
public class Solution {
    public int hammingWeight(int n) {
        int ret = 0;
        while (n != 0) {
            n &= n - 1;
            ret++;
        }
        return ret;
    }
}
```

这种解法主要是发现了n&(n-1)可以将n的最后一个1变成0，所以复杂度为O(log*n*)





#### 14. [剑指 Offer 17. 打印从1到最大的n位数](https://leetcode-cn.com/problems/da-yin-cong-1dao-zui-da-de-nwei-shu-lcof/)

我的题解：

```java
class Solution {
    public int[] printNumbers(int n) {
        int end = (int)Math.pow(10,n) - 1;
        int[] res = new int[end];
        for(int i=0;i<end;i++){
            res[i] = i+1;
        }
        return res;


    }
}
```

直接for循环，0ms，100%



#### 15. [剑指 Offer 18. 删除链表的节点](https://leetcode-cn.com/problems/shan-chu-lian-biao-de-jie-dian-lcof/)

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode deleteNode(ListNode head, int val) {
        ListNode head2 =head;
        if(head.val == val){
            return head.next;
        }
        ListNode before = null;
        while(head.next != null){
            if(head.val == val){
                break;
            }
            before = head;
            head = head.next;
        }
        before.next = head.next;
        head.next = null;
        return head2;


    }
}
```

0ms 100%

遍历

#### 16. [剑指 Offer 21. 调整数组顺序使奇数位于偶数前面](https://leetcode-cn.com/problems/diao-zheng-shu-zu-shun-xu-shi-qi-shu-wei-yu-ou-shu-qian-mian-lcof/)



我的题解

```java
class Solution {
    public int[] exchange(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while(left < right){
            if(nums[left] % 2 == 0 && nums[right] % 2 == 1){
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
            else if(nums[left] % 2 == 0){
                right--;
            }
            else if(nums[right] % 2 == 1){
                left++;
            }
            else{
                left++;
                right--;
            }

        }
        return nums;

    }
}
```

双指针，如果符合条件就交换；

这里没有保证奇数之间、偶数之间的顺序。如果需要保证，可以采用冒泡排序，或者两次遍历分别取出奇偶数，然后合到一起去

2ms 53.68%



**改进思路**

1. 位运算比取模的效率要高
2. 双指针不需要分这么多种情况

改进后代码：

```java
class Solution {
    public int[] exchange(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while(left < right){
            while((nums[left] & 1) != 0 && left < right ){
                left++;
            }
            while((nums[right] & 1) != 1 && left < right){
                right--;
            }
            if(left < right){
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] =temp;
            }

        }
        return nums;

    }
}
```

1ms 100%

一个地方指出：

```java
            if(left < right){
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] =temp;
            }
```

这里判断不判断其实都可以，因为前面两个while有`left <right`这个判断了，所以这里，要么是left不等于right，找到符合条件的两个数，要么left = right，那即便相等，互换也不会有问题。

#### 17. [剑指 Offer 22. 链表中倒数第k个节点](https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/)

双指针即可，常见问题，常见思路

题解：

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        if(head.next == null){//没有后继节点，此时按理k只能为1
            return head;
        }
        ListNode left = head;
        ListNode right = head;
        for(int i = 0; i < k - 1; i++){
            right = right.next;
        }
        while(right.next != null){
            left = left.next;
            right = right.next;
        }
        return left;

    }
}
```

0ms，100%





#### 18. [剑指 Offer 63. 股票的最大利润](https://leetcode-cn.com/problems/gu-piao-de-zui-da-li-run-lcof/)

题解：

```java
class Solution {
    public int maxProfit(int[] prices) {
        int left = 0;
        int right = 1;
        int res = 0;
        while(right < prices.length){
            res = Math.max(res,prices[right]-prices[left]);
            if(prices[right] < prices[left]){
                left = right;
            }
            right++;
        }
        return res;

    }
}
```

时间：69%





#### 19. [剑指 Offer 59 - I. 滑动窗口的最大值](https://leetcode-cn.com/problems/hua-dong-chuang-kou-de-zui-da-zhi-lcof/)

```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length == 0){
            int[] res = new int[0];
            return res;
        }
        int[] res = new int[nums.length - k + 1];
        int max = nums[0];
        for(int i = 0; i < k; i++){
            max = Math.max(max,nums[i]);
        }
        res[0] = max;

        for(int i = 1;i < nums.length - k + 1; i++){
            max = nums[i];
            for(int j = i; j < i + k; j++){
                max = Math.max(max,nums[j]);
            }
            res[i] = max;

        }

        return res;

    }
}
```

通过了但显然效率不高（22%），复杂度O(nk)

改进思路，如何将查询最大值复杂度降低，最好能从O(k)降低到O(1)

可选方法：单调队列

[解析](https://leetcode-cn.com/problems/hua-dong-chuang-kou-de-zui-da-zhi-lcof/solution/mian-shi-ti-59-i-hua-dong-chuang-kou-de-zui-da-1-6/)

```java
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
```

效率：78.2%

使用单调队列获得窗口内的最大值，复杂度O(nlogn)，不可能同时在添加删除和获得最大值时实现O(1)复杂度。

注意一点，队列为非严格递减队列，`deque.peekLast() < nums[j])` 此处是将小于滑动窗口右边的值的数去掉，而非把小于等于去掉，所以此处形成的队列是非严格递减的。