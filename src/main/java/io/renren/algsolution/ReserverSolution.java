package io.renren.algsolution;

import java.util.Scanner;

/*
题目描述
对字符串中的所有单词进行倒排。

说明：

1、每个单词是以26个大写或小写英文字母构成；

2、非构成单词的字符均视为单词间隔符；

3、要求倒排后的单词间隔符以一个空格表示；如果原字符串中相邻单词间有多个间隔符时，倒排转换后也只允许出现一个空格间隔符；

4、每个单词最长20个字母；

输入描述:
输入一行以空格来分隔的句子

输出描述:
输出句子的逆序

示例1
输入
I am a student
输出
student a am I
 */
public class ReserverSolution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String resStr = reserverStr(str);
        String[] res = resStr.split(" ");
        for (int i = res.length - 1; i >= 0; i--) {
            if (i == 0) {
                if (resStr.startsWith(" ")) {
                    System.out.println(res[i] + " ");
                } else {
                    System.out.println(res[i]);
                }
            } else  {
                if (resStr.endsWith(" ")) {
                    System.out.print(" ");
                }
                System.out.print(res[i] + " ");
            }
        }
    }

    private static String reserverStr(String str) {
        String res = "";
        char lastChar = '1';
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch < 'A' || (ch >'Z' && ch < 'a') || ch > 'z') {
                ch = ' ';
            }
            if (!(Character.isSpaceChar(ch) && Character.isSpaceChar(lastChar))) {
                res += ch;
            }
            lastChar = ch;
        }
        return res;
    }
}
