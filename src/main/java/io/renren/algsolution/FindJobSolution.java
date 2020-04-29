package io.renren.algsolution;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
为了找到自己满意的工作，牛牛收集了每种工作的难度和报酬。牛牛选工作的标准是在难度不超过自身能力值的情况下，牛牛选择报酬最高的工作。在牛牛选定了自己的工作后，牛牛的小伙伴们来找牛牛帮忙选工作，牛牛依然使用自己的标准来帮助小伙伴们。牛牛的小伙伴太多了，于是他只好把这个任务交给了你。
输入描述:
每个输入包含一个测试用例。
每个测试用例的第一行包含两个正整数，分别表示工作的数量N(N<=100000)和小伙伴的数量M(M<=100000)。
接下来的N行每行包含两个正整数，分别表示该项工作的难度Di(Di<=1000000000)和报酬Pi(Pi<=1000000000)。
接下来的一行包含M个正整数，分别表示M个小伙伴的能力值Ai(Ai<=1000000000)。
保证不存在两项工作的报酬相同。
 */
public class FindJobSolution {

    static class Job implements Comparable<Job> {
        Integer di;
        Integer pi;
        public Job(Integer di, Integer pi){
            this.di = di;
            this.pi = pi;
        }

        public Integer getDi() {
            return di;
        }

        public void setDi(Integer di) {
            this.di = di;
        }

        public Integer getPi() {
            return pi;
        }

        public void setPi(Integer pi) {
            this.pi = pi;
        }

        @Override
        public int compareTo(Job o) {
            return this.pi - o.getPi();
        }
    }

    static class Pel {
        Integer ai;
        Integer max = 0;
        public Pel(Integer ai){
            this.ai = ai;
        }
    }

    public static void main(String[] args) {
        InputReader scanner = new InputReader(System.in);
        PrintWriter writer = new PrintWriter(new BufferedOutputStream(System.out));
        int n= scanner.nextInt();
        int m= scanner.nextInt();
        List<Job> jobs = new ArrayList<>(n);
        List<Pel> ais = new ArrayList<>(m);
        for (int i=0; i < n; i++) {
            Integer di = scanner.nextInt();
            Integer pi = scanner.nextInt();
            boolean isAdd = true;
            for (Job job : jobs) {
                if (job.getDi() <= di && job.getPi() > pi) {
                    isAdd = false;
                    break;
                }
                if (job.getDi() > di && job.getPi() < pi) {
                    isAdd = false;
                    job.setDi(di);
                    job.setPi(pi);
                    break;
                }
            }
            if (isAdd) {
                jobs.add(new Job(di, pi));
            }
        }
        for (int i=0; i < m; i++) {
            ais.add(new Pel(scanner.nextInt()));
        }
        Collections.sort(jobs);
        for (Pel pel : ais) {
            for (int i = jobs.size() -1; i >= 0; i--) {
                Job job = jobs.get(i);
                if (job.di <= pel.ai && pel.max < job.pi) {
                    pel.max =  job.pi;
                    break;
                }
            }
        }
        for (Pel pel : ais) {
            writer.println(pel.max);
        }
        writer.flush();
    }

    static class InputReader {
        private InputStream stream;
        private byte[] inbuf = new byte[1024];
        private int lenbuf = 0;
        private int ptrbuf = 0;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int readByte() {
            if (lenbuf == -1)
                throw new UnknownError();
            if (ptrbuf >= lenbuf) {
                ptrbuf = 0;
                try {
                    lenbuf = stream.read(inbuf);
                } catch (IOException e) {
                    throw new UnknownError();
                }
                if (lenbuf <= 0) {
                    return -1;
                }
            }
            return inbuf[ptrbuf++];
        }

        public int nextInt() {
            int num = 0, b;
            boolean minus = false;
            while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
                ;
            if (b == '-') {
                minus = true;
                b = readByte();
            }

            while (true) {
                if (b >= '0' && b <= '9') {
                    num = num * 10 + (b - '0');
                } else {
                    return minus ? -num : num;
                }
                b = readByte();
            }
        }
    }
}
