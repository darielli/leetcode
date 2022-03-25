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





#### 20. [剑指 Offer 54. 二叉搜索树的第k大节点](https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-di-kda-jie-dian-lcof/)



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
    int res,k;
    public int kthLargest(TreeNode root, int k) {
        this.k = k;
        dsf(root);
        return res;


    }

    public void dsf(TreeNode node){
        if(node == null){
            return;
        }
        dsf(node.right);
        k--;
        if(k == 0){
            this.res = node.val;
            return;
        }
        dsf(node.left);


    }

}
```

思路：二叉搜索树的中序遍历为递增序列，所以中旬遍历倒序为递减序列，记录一个公共变量k，然后中旬倒序遍历，当k为0时，该节点的val值就是所要求的的值。

Tips：如何保存公共变量是一个需要注意的问题，java对于数值是直接复制值，所以如果传递int值每次递归实际上访问的不是用一个数值，此处使用类的变量是一个解决方法，如果题目是直接给定一个函数没有这个类的话，我们可以定义一个只有1个变量的数组，用传递数组的方式来实现传递引用。

[更多详细解析](https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-di-kda-jie-dian-lcof/solution/mian-shi-ti-54-er-cha-sou-suo-shu-de-di-k-da-jie-d/)



#### 21. [剑指 Offer 53 - I. 在排序数组中查找数字 I](https://leetcode-cn.com/problems/zai-pai-xu-shu-zu-zhong-cha-zhao-shu-zi-lcof/)

思路分析：

显然在排序树组中查找一个数值的复杂度不会超过O(n)，同时不会低于O(logn)，直接遍历是一个可以解决问题的方法，所以我们应该尽可能地使复杂度靠近O(logn).

因此，思路显然为：二分查找找到首尾两个值，然后利用下标差得到该数字的个数

```java
class Solution {
    public int search(int[] nums, int target) {
        if(nums.length == 0){
            return 0;
        }
        int left = binarySearch(nums, 0, nums.length - 1, target, 0);
        if(left == -1){
            return 0;
        }
        int right = binarySearch(nums, 0, nums.length - 1, target, 1);
        return right - left + 1;

    }

    //flag为0表示查找左边target，1表示查找右边target
    public int binarySearch(int[] nums, int left, int right,int target, int flag){
        int middle = (left + right) / 2;
        if(target < nums[left]){
            return -1;
        }
        if(target > nums[right]){
            return -1;
        }
        if(left == right && nums[left] == target){
            return left;
        }
        else if(left == right){
            return -1;
        }
        if(target < nums[middle]){
            return binarySearch(nums, left, middle - 1, target, flag);
        }
        else if(nums[middle] < target){
            return binarySearch(nums, middle + 1, right, target, flag);
        }
        else{
            if(flag == 0){//查找左边target
                if(middle > left && nums[middle - 1] == target){
                    return binarySearch(nums, left, middle - 1, target, flag);
                }
                else{
                    return middle;
                }

            }
            else{//查找右边target
                if(middle < right && nums[middle + 1] == target){
                    return binarySearch(nums, middle + 1, right, target, flag);
                }
                else{
                    return middle;
                }

            }
        }
    }

}
```

0ms，100%

出现的问题：

1. 误以为target一定在数组中，导致出错
2. if-else太多
3. 查左边和查右边的代码重复的较多，可以合并

[**官方题解：**](https://leetcode-cn.com/problems/zai-pai-xu-shu-zu-zhong-cha-zhao-shu-zi-lcof/solution/zai-pai-xu-shu-zu-zhong-cha-zhao-shu-zi-wl6kr/)

```java
class Solution {
    public int search(int[] nums, int target) {
        int leftIdx = binarySearch(nums, target, true);
        int rightIdx = binarySearch(nums, target, false) - 1;
        if (leftIdx <= rightIdx && rightIdx < nums.length && nums[leftIdx] == target && nums[rightIdx] == target) {
            return rightIdx - leftIdx + 1;
        } 
        return 0;
    }

    public int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target || (lower && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
}
```

两种做法的异同：总的思路一致，二分查找实现的方法不同，对于第一种，为了避免陷入死循环，就必须判断很多特殊情况，导致if-else结构很多，同时，很多相同的if-else结构可以合并，但第一种返回的就是target的下标，否则返回-1，而第二种实际上返回的是最接近target的数的下标，将特殊情况抛给二分法之外判断。





优解：

```java
class Solution {
    public int search(int[] nums, int target) {
        return helper(nums, target) - helper(nums, target - 1);
    }
    int helper(int[] nums, int tar) {
        int i = 0, j = nums.length - 1;
        while(i <= j) {
            int m = (i + j) / 2;
            if(nums[m] <= tar) i = m + 1;
            else j = m - 1;
        }
        return i;
    }
}
```

思路：不是求target的左右边界，而是求target和target-1的右边界相减

此外，这个算法求出来的不是target的最右边的值的下标，而是上述下标+1，这样其实可以避免一些相等时的特殊情况的判断。



#### 22. [剑指 Offer 48. 最长不含重复字符的子字符串](https://leetcode-cn.com/problems/zui-chang-bu-han-zhong-fu-zi-fu-de-zi-zi-fu-chuan-lcof/)

看到题目的一个思路：双向栈，将字母入栈，如果有重复字母，出栈至 去掉重复的那一个，max = max（max，length）

问题：字母是无序的，每次都要重复判断，且栈内内容不好判断。



优解：

```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }

        HashMap<Character, Integer> map = new HashMap<>();
        char[] chars = s.toCharArray();

        int res = 0, start = 0;
        for(int i = 0; i < s.length(); i++){
            if(map.containsKey(chars[i])){
                start = Math.max(map.put(chars[i], i) + 1, start);
            }
            map.put(chars[i], i);
            res = Math.max(res, i - start + 1);
        }
        return res;

    }
}
```

利用hashMap来实现不重复，类似之前的复制随机指针链表

tips：hashMap函数的put方法如果重复了会返回value，否则返回null

4ms，92.01%

todo：动态规划&滑动窗口也可解

[动态规划](https://leetcode-cn.com/problems/zui-chang-bu-han-zhong-fu-zi-fu-de-zi-zi-fu-chuan-lcof/solution/mian-shi-ti-48-zui-chang-bu-han-zhong-fu-zi-fu-d-9/)可解，但是并没有简单很多，时间复杂度一致



#### 23. [剑指 Offer 47. 礼物的最大价值](https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof/)

思路：动态规划题，往右或往下移动，所以为了取得最大值，最后一次一定是右下角的值，开始时左上角的值。假设M（i,j）是坐标（i,j）到右下角的礼物的最大值，所以D(i, j)是(i, j)坐标处的最大值，所以M（i, j）=  D(i, j) + max(M(i+1, j), M(i, j+1))

```java
class Solution {
    public int maxValue(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;//提示中说明存在且不为零
        int[][] res = new int[m][n];
        res[m - 1][n - 1] = grid[m - 1][n- 1];
        for(int i = m - 1; i >= 0; i--){
            for(int j = n - 1; j >= 0; j--){
                if(j == n - 1 && i == m -1){
                    continue;
                }
                else if(j == n - 1){
                    res[i][j] = res[i + 1][j] + grid[i][j];
                }
                else if(i == m -1){
                    res[i][j] = res[i][j + 1] + grid[i][j];
                }
                else{
                    res[i][j] = Math.max(res[i][ j + 1], res[i + 1][j]) + grid[i][j];
                }
            }
        }
        return res[0][0];
    }
}
```

problems:

1. 最低级的错误是，将（i，j）坐标的值写成res[i,j]

效率：3ms，27.79%

几乎同样的思路

```java
class Solution {
    public int maxValue(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(i == 0 && j == 0) continue;
                if(i == 0) grid[i][j] += grid[i][j - 1] ;
                else if(j == 0) grid[i][j] += grid[i - 1][j];
                else grid[i][j] += Math.max(grid[i][j - 1], grid[i - 1][j]);
            }
        }
        return grid[m - 1][n - 1];
    }
}
```

2ms，效率98%

我的代码修改为

```java
class Solution {
    public int maxValue(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;//提示中说明存在且不为零
        for(int i = m - 1; i >= 0; i--){
            for(int j = n - 1; j >= 0; j--){
                if(j == n - 1 && i == m -1){
                    continue;
                }
                else if(j == n - 1){
                    grid[i][j] += grid[i + 1][j];
                }
                else if(i == m -1){
                    grid[i][j] += grid[i][j + 1];
                }
                else{
                    grid[i][j] += Math.max(grid[i][ j + 1], grid[i + 1][j]);
                }
            }
        }
        return grid[0][0];
    }
}
```

仍然是3ms！！

**！！！！！**

是否是for循环++和--的区别？

单独跑了一下，差别不明显，时大时小，忽略这个情况；

#### 24. [剑指 Offer 46. 把数字翻译成字符串](https://leetcode-cn.com/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof/)

思路：也是动态规划，假设到第i个字母的翻译方法有D(i)种，那么D(i+1) = D(i) + D(i - 1)(当第i-1和第i个字母组成的数小数等于25时)，否则D(i+1) = D(i)

```java
class Solution {
    public int translateNum(int num) {
        int length = String.valueOf(num).length();
        int[] nums = new int[length];
        int[] res = new int[length];
        for(int i = length - 1; i >= 0; i--){
            nums[i] = num % 10;
            num = num / 10;
        }
        for(int i = 0; i < length; i++){
            if( i == 0){
                res[i] = 1;
            }
            else if(nums[i - 1] * 10 + nums[i] <=25 && nums[i - 1] != 0){
                res[i] = res[i - 1] + (i > 1 ? res[i - 2] : 1);
            }
            else{
                res[i] = res[i-1];
            }
        }
        return res[length - 1];

    }
}
```

问题：

1. 06只有1种翻译方法
2. 数组不要越界

0ms，100%

#### 25. [剑指 Offer 44. 数字序列中某一位的数字](https://leetcode-cn.com/problems/shu-zi-xu-lie-zhong-mou-yi-wei-de-shu-zi-lcof/)

思路：求出这个数，然后得出对应位数的数字

```java
class Solution {
    public int findNthDigit(int n) {
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
}
```

0ms 100%（时而1ms）

待优化地方：

1. `Integer.parseInt("" + String.valueOf(res).charAt(reminder - 1));`

可以优化为`String.valueOf(res).charAt(reminder - 1) - ‘0’`

2. 求n是几位数的某一位时复杂了，可以简单的



简化后的代码

```java
class Solution {
    public int findNthDigit(int n) {
        if(n <= 9){
            return n;
        }
        long r = 1, count = 9;
        int right = 1;
        while(n > count){
            right++;
            n -= count;
            r = 10 * r;
            count = right * r * 9;
        }
        int reminder = (n-1) % right;
        int quotient = (n-1) / right;
        long res = r + quotient;
        return String.valueOf(res).charAt(reminder) - '0';
    }
}
```

100%

#### 26. [剑指 Offer 41. 数据流中的中位数](https://leetcode-cn.com/problems/shu-ju-liu-zhong-de-zhong-wei-shu-lcof/)

思路：插入时排序，然后维护两个指针表示中位数，

代码：

```java
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
```

108ms ，13.81%

问题：二分法求解当数组中不存在时只能说是在该数附近，所以左右两边都要比较



优化解法：（使用优先队列）

```java
class MedianFinder {
    Queue<Integer> A,B;
    public MedianFinder() {
        A = new PriorityQueue<Integer>();
        B = new PriorityQueue<Integer>((x,y) -> (y - x));

    }
    public void addNum(int num) {
        if(A.size() == B.size()){
            B.add(num);
            A.add(B.poll());
        }
        else{
            A.add(num);
            B.add(A.poll());
        }

    }
    public double findMedian() {
        if(A.size() == B.size()){
            return (A.peek() + B.peek()) / 2.0;
        }
        else{
            return A.peek();
        }
    }
}

```

63ms 91%

使用一个大顶堆和一个小顶堆来分别存储较大的数和较小的数，中位数就是某一个堆堆顶的数或者两个堆堆顶数的平均数；

Question：如何确定插入的数应该放在A还是B？

1. 先定义规则m=n的时候插入A，此时先将数字插入另一个堆中，然后将堆顶的数字弹出（此时已排序）加入到A中。同理，插入B的时候先插入A



