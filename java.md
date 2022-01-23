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

