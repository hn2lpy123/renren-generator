package io.renren.algsolution;


import java.util.Scanner;

/*
Catcher是MCA国的情报员，他工作时发现敌国会用一些对称的密码进行通信，
比如像这些ABBA，ABA，A，123321，但是他们有时会在开始或结束时加入一些无关的字符以防止别国破解。
比如进行下列变化 ABBA->12ABBA,ABA->ABAKK,123321->51233214　。
因为截获的串太长了，而且存在多种可能的情况（abaaab可看作是aba,或baaab的加密形式），
Cathcer的工作量实在是太大了，他只能向电脑高手求助，你能帮Catcher找出最长的有效密码串吗？
 */
public class PwdSolution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String res = "";
        for (int i = 0; i < input.length() - 1; i ++) {
            for (int j = input.length() - 1; j > 0 && j > i; j --) {
                String temp = input.substring(i, j + 1);
                if (isPwd(temp)) {
                    if (temp.length() > res.length() && temp.length() > 1) {
                        res = temp;
                    }
                }
            }
        }
        System.out.println(res);
        System.out.println(res.length() == 0 ? 1 : res.length());
        System.out.println(getResLength(input));
        System.out.println(getResLengthV2(input));
    }

    public static boolean isPwd(String str) {
        boolean isPwd = true;
        for (int i = 0; i < str.length()/2; i++) {
            if (str.charAt(i) != str.charAt((str.length() - 1 -i))) {
                isPwd = false;
                break;
            }
        }
        return isPwd;
    }

    public static int getResLength(String str) {
        char[] arr = str.toCharArray();
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int tmp1 = 1, tmp2 = 0;
            int res1 = 1, res2 = 0;
            while (i - tmp1 >= 0 && i + tmp1 < arr.length && arr[i - tmp1] == arr[i + tmp1]) {
                tmp1++;
                res1+=2;
            }
            while (i - tmp2 >= 0 && i + tmp2 + 1 < arr.length && arr[i - tmp2] == arr[i + tmp2 + 1]) {
                tmp2++;
                res2+=2;
            }
            res1 = Math.max(res1, res2);
            max = Math.max(res1, max);
        }
        return max;
    }

    public static int getResLengthV2(String s) {
        if (s.length() == 1) {
            return 1;
        }
        int[] a = new int[s.length()];
        a[0] = 1;
        for (int i = 1; i < s.length(); i++) {
            a[i] = a[i-1];
            for (int j = 0; j < i - a[i-1]; j++) {
                if (isPwd(s.substring(j, i))) {
                    if (a[i-1] < i - j + 1) {
                        a[i] = i - j + 1;
                    }
                    break;
                }
            }
        }
        return a[s.length() - 1];
    }
}
