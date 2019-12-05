package io.renren.utils.excel.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.google.common.collect.Lists;
import io.renren.entity.ExtraField;
import io.renren.utils.Exception.RRException;
import io.renren.utils.constant.CommonCodeType;
import io.renren.utils.generator.GenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ExtraFieldListener extends BaseImportListener<ExtraField> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtraFieldListener.class);

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        List<ExtraField> temp = Lists.newArrayList(GenUtils.extraFields);
        for (ExtraField extraField : datas) {
            for (ExtraField field : GenUtils.extraFields) {
                if (field.getExtraFieldName().equals(extraField.getExtraFieldName())) {
                    field.setExtraFieldType(extraField.getExtraFieldType());
                    field.setExtraFieldName(extraField.getExtraFieldName());
                    field.setExtraFieldValue(extraField.getExtraFieldValue());
                    return;
                }
            }
            GenUtils.extraFields.add(extraField);
        }
        datas.clear();
        if (GenUtils.extraFields.size() > GenUtils.EXTRA_FIELD_MAX) {
            GenUtils.extraFields.clear();
            GenUtils.extraFields.addAll(temp);
            throw new RRException(CommonCodeType.EXTRA_FIELD_MAX);
        }
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
    }

    @Override
    public void invoke(ExtraField extraField, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", extraField.toString());
        if (datas.size() > GenUtils.EXTRA_FIELD_MAX) {
            throw new RRException(CommonCodeType.EXTRA_FIELD_MAX);
        }
        datas.add(extraField);
    }
}
