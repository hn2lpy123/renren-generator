package io.renren.utils.excel.test;

import com.alibaba.excel.EasyExcel;

import java.io.FileNotFoundException;

public class ExcelTest {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName ="D:\\test.xlsx";
        System.out.println("====start====");
        EasyExcel.read(fileName, new NotPropProjectDataListener()).sheet("地产公司").doRead();
        EasyExcel.read(fileName, new NotPropProjectDataListener()).sheet("其他产业集团").doRead();
        EasyExcel.read(fileName, new PropProjectDataListener()).sheet("物业公司").doRead();
        System.out.println("====end====");
    }
}
