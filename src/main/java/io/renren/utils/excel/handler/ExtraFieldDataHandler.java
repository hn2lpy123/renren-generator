package io.renren.utils.excel.handler;

import com.google.common.collect.Lists;
import io.renren.bean.ExtraField;
import io.renren.utils.constant.CommonCodeType;
import io.renren.utils.exception.RRException;
import io.renren.utils.generator.GenUtils;

import java.util.List;

public class ExtraFieldDataHandler extends ExcelDataHandler<ExtraField> {

    public static final String ERROR_FORMAT = "【RowNum=%s,ValidateMessage=%s】\n";

    @Override
    public synchronized void saveData(List<ExtraField> rows) {
        List<ExtraField> temp = Lists.newArrayList(GenUtils.extraFields);
        for (ExtraField extraField : rows) {
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
        if (GenUtils.extraFields.size() > GenUtils.EXTRA_FIELD_MAX) {
            GenUtils.extraFields.clear();
            GenUtils.extraFields.addAll(temp);
            throw new RRException(CommonCodeType.EXTRA_FIELD_MAX);
        }
    }

    @Override
    public void dealInvorkErrorRow(List<ExtraField> rows, ExtraField extraField) {
        String.format(ERROR_FORMAT, extraField.getRowNum(), extraField.getValidateMessage());
    }

    @Override
    public void onException(Exception ex) throws Exception {
        if (ex instanceof RRException) {
            super.onException(ex);
        }
    }
}
