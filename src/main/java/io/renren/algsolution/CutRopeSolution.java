package io.renren.algsolution;

/**
 * 给一段长度位整数n的绳子，剪成若干段，每段长度也是整数，计算这些长度的乘积的最大值。
 */
public class CutRopeSolution {

    // f(n) = max
    private static Integer getMax(Integer n) {
        if (n == 1) return 0;
        if (n == 2) return 1;
        if (n == 3) return 2;
        return 1;
    }

    public static void main(String[] args) {
        System.out.println(getMax(9));
    }
}
