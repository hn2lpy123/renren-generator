package io.renren.algsolution;

import java.util.*;

public class ArraySolution {

    /*
    对两个数组：String[] str1 =[“22”,”44”,”11”,”22”]; String[] str2=[“33”,”4”,”6”,”199”,”11”] 合并去重并按数值从小到大排序后输出编程
     */
    public static Set<String> sorted(String[] str1, String[] str2) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < str1.length; i ++) {
            set.add(str1[i]);
        }
        for (int i = 0; i < str2.length; i ++) {
            set.add(str2[i]);
        }
        Set<String> sortSet = new TreeSet<String>(Comparator.reverseOrder());
        sortSet.addAll(set);
        return sortSet;
    }

    public static void quickSort(String[] str, Integer start, Integer end) {
        if (start >= end) {
            return;
        }
        String tmp = str[start];
        int left = start, right = end;

        while (left < right) {
            while (left < right && compare(str[right], tmp)) {
                right--;
            }
            str[left] = str[right];
            while (left < right && compare(tmp, str[left])) {
                left++;
            }
            str[right] = str[left];
        }
        str[left] = tmp;
        quickSort(str, start, left - 1);
        quickSort(str, left + 1, end);

    }



    private static boolean compare(String str1, String str2) {
        return Integer.parseInt(str1) >= Integer.parseInt(str2);
    }

    public static void main(String[] args) {
        String[] str1 ={"22","44","11","22"};
        String[] str2={"33","4","6","199","11"};
        System.out.println(sorted(str1, str2));
        quickSort(str2, 0 , str2.length - 1);
        for (int i = 0; i < str2.length - 1; i ++) {
            System.out.print(str2[i] + ",");
        }

    }
}
