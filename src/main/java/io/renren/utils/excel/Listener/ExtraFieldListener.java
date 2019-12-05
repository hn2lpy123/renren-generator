package io.renren.utils.excel.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.renren.entity.ExtraField;
import io.renren.utils.generator.GenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtraFieldListener extends AnalysisEventListener<ExtraField> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraFieldListener.class);

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
    }

    @Override
    public void invoke(ExtraField extraField, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", extraField.toString());
        boolean hasExist = false;
        for (ExtraField field : GenUtils.extraFields) {
            if (field.getExtraFieldName().equals(extraField.getExtraFieldName())) {
                hasExist = true;
            }
        }
    }
}
