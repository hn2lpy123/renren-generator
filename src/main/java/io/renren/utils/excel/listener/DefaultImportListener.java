package io.renren.utils.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import io.renren.bean.ExcelRow;
import io.renren.utils.excel.handler.ExcelDataHandler;
import io.renren.utils.excel.validate.DefaultExcelValidator;
import io.renren.utils.excel.validate.ExcelValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认导入监听类
 *
 * @Author lipingyu
 * @Date 2019-12-09
 */
public class DefaultImportListener<T extends ExcelRow> extends BaseImportListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultImportListener.class);

    /**
     * 每隔2000条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final Integer BATCH_COUNT = 2000;

    private ExcelValidator excelValidator;

    private ExcelDataHandler excelDataHandler;

    public DefaultImportListener(ExcelDataHandler excelDataHandler) {
        this.excelValidator = new DefaultExcelValidator();
        this.excelDataHandler = excelDataHandler;
    }

    public DefaultImportListener(ExcelValidator excelValidator, ExcelDataHandler excelDataHandler) {
        this.excelValidator = excelValidator;
        this.excelDataHandler = excelDataHandler;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", t.toString());
        t.setRowNum(analysisContext.readRowHolder().getRowIndex() + analysisContext.readSheetHolder().getHeadRowNumber());
        if (excelValidator.validate(t)) {
            t.setValidateCode(ExcelRow.SUCCESS_CODE);
            excelDataHandler.dealInvorkSuccessRow(rows, t);
        } else {
            excelDataHandler.dealInvorkErrorRow(rows, t);
        }
        if (rows.size() >= BATCH_COUNT) {
            excelDataHandler.saveData(rows);
            rows.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        excelDataHandler.saveData(rows);
        rows.clear();
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        LOGGER.error("第{}行解析失败:{}", context.readRowHolder().getRowIndex(), exception.getMessage());
        excelDataHandler.onException(exception);
//      super.onException(exception, context);
    }
}
