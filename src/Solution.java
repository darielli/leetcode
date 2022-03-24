import java.util.HashMap;

class Solution {
    public static void main(String[] args){
        System.out.print(lengthOfLongestSubstring("pwkewt"));
    }
    public static int lengthOfLongestSubstring(String s) {
        String str = s;
        if (str == null || str.length() == 0) return 0;

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