package io.renren.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 封装导出excel工具类
 *
 * @author dengshuai
 * @version v1.0
 * Copyright (c) 2019 by dengshuai. All rights reserved.
 * @date 2019/10/19 14:25
 * 修饰METHOD只对无参PUBLIC METHOD有效 updated by lipingyu
 */
public class ExcelExportUtil<T> {

    private int rowIndex;
    private int styleIndex;
    private String templatePath;
    private Class clazz;
    private  Field fields[];
    private Method methods[];

    public ExcelExportUtil(Class clazz,int rowIndex,int styleIndex) {
        this.clazz = clazz;
        this.rowIndex = rowIndex;
        this.styleIndex = styleIndex;
        fields = clazz.getDeclaredFields();
        methods = clazz.getMethods();
    }

    /**
     * 基于注解导出
     */
    public void export(HttpServletResponse response, InputStream is, List<T> objs, String fileName) throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        CellStyle[] styles = getTemplateStyles(sheet.getRow(styleIndex));

        AtomicInteger datasAi = new AtomicInteger(rowIndex);
        for (T t : objs) {
            Row row = sheet.createRow(datasAi.getAndIncrement());
            for(int i=0;i<styles.length;i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(styles[i]);
                for (Field field : fields) {
                    if(field.isAnnotationPresent(ExcelAttribute.class)){
                        field.setAccessible(true);
                        ExcelAttribute ea = field.getAnnotation(ExcelAttribute.class);
                        if(i == ea.sort()) {
                            cell.setCellValue(field.get(t).toString());
                        }
                    }
                }
                for (Method method : methods) {
                    if(method.isAnnotationPresent(ExcelAttribute.class) && method.getParameterCount() == 0){
                        ExcelAttribute ea = method.getAnnotation(ExcelAttribute.class);
                        if(i == ea.sort()) {
                            cell.setCellValue(method.invoke(t).toString());
                        }
                    }
                }
            }
        }
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/json");
        response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("ISO8859-1")));
        response.setHeader("filename", fileName);
        workbook.write(response.getOutputStream());
    }

    public CellStyle[] getTemplateStyles(Row row) {
        CellStyle [] styles = new CellStyle[row.getLastCellNum()];
        for(int i=0;i<row.getLastCellNum();i++) {
            styles[i] = row.getCell(i).getCellStyle();
        }
        return styles;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getStyleIndex() {
        return styleIndex;
    }

    public void setStyleIndex(int styleIndex) {
        this.styleIndex = styleIndex;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }
}
