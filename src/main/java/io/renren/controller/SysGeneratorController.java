package io.renren.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.renren.entity.CommonDto;
import io.renren.entity.ExtraField;
import io.renren.entity.GeneratorInfo;
import io.renren.service.SysGeneratorService;
import io.renren.utils.annotation.NoRepeatSubmit;
import io.renren.utils.constant.CommonCodeType;
import io.renren.utils.excel.Listener.ExtraFieldListener;
import io.renren.utils.generator.GenUtils;
import io.renren.utils.generator.PageUtils;
import io.renren.utils.generator.Query;
import io.renren.utils.generator.R;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@NoRepeatSubmit
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = sysGeneratorService.queryList(query);
		int total = sysGeneratorService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 生成代码
	 */
	@RequestMapping("/code")
	public void code(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[]{};
		String tables = request.getParameter("tables");
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		
		byte[] data = sysGeneratorService.generatorCode(tableNames);
		
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
  
        IOUtils.write(data, response.getOutputStream());  
	}

	/**
	 * 设置生成代码的作者信息和包信息
	 */
	@RequestMapping("/saveGeneratorInfo")
	@ResponseBody
	public CommonDto saveGeneratorInfo(@RequestBody GeneratorInfo generatorInfo) {
		GenUtils.setConfig(GenUtils.PACKAGE, generatorInfo.getPackageInfo());
		GenUtils.setConfig(GenUtils.AUTHOR, generatorInfo.getAuthor());
		GenUtils.setConfig(GenUtils.EMAIL, generatorInfo.getEmail());
		GenUtils.setConfig(GenUtils.TABLEPREFIX, generatorInfo.getTablePrefix());
		return new CommonDto(CommonCodeType.SUCCESS);
	}

	/**
	 * 设置生成代码的作者信息和包信息
	 */
	@RequestMapping("/getGeneratorInfo")
	@ResponseBody
	public CommonDto<GeneratorInfo> getGeneratorInfo() {
		return new CommonDto<>(CommonCodeType.SUCCESS, new GeneratorInfo(GenUtils.initData.get(GenUtils.PACKAGE).toString(),
				GenUtils.initData.get(GenUtils.AUTHOR).toString(), GenUtils.initData.get(GenUtils.EMAIL).toString(),
				GenUtils.initData.get(GenUtils.TABLEPREFIX).toString()));
	}

	/**
	 * 获取自定义模板参数
	 */
	@RequestMapping("/getExtraFields")
	@ResponseBody
	public CommonDto getExtraFields() {
		return new CommonDto<>(CommonCodeType.SUCCESS, GenUtils.extraFields);
	}

	/**
	 * 新增自定义模板参数
	 */
	@RequestMapping("/addExtraField")
	@ResponseBody
	public CommonDto addExtraField(@RequestBody ExtraField extraField) {
		if (GenUtils.extraFields.size() > GenUtils.EXTRA_FIELD_MAX) {
			return new CommonDto(CommonCodeType.EXTRA_FIELD_MAX);
		}
		GenUtils.extraFields.add(extraField);
		return new CommonDto<>(CommonCodeType.SUCCESS);
	}

	/**
	 * 修改自定义模板参数
	 */
	@RequestMapping("/editExtraField")
	@ResponseBody
	public CommonDto editExtraField(@RequestBody ExtraField extraField) {
		for (ExtraField field : GenUtils.extraFields) {
			if (field.getExtraFieldName().equals(extraField.getExtraFieldName())) {
				field.setExtraFieldType(extraField.getExtraFieldType());
				field.setExtraFieldValue(extraField.getExtraFieldValue());
				break;
			}
		}
		return new CommonDto<>(CommonCodeType.SUCCESS);
	}

	/**
	 * 批量删除自定义模板参数
	 */
	@RequestMapping("/batchDeleteField")
	@ResponseBody
	public CommonDto batchDeleteField(@RequestBody List<ExtraField> extraFields) {
		for (ExtraField field : extraFields) {
			Iterator<ExtraField> iterable = GenUtils.extraFields.iterator();
			while (iterable.hasNext()) {
				ExtraField extraField = iterable.next();
				if (extraField.getExtraFieldName().equals(field.getExtraFieldName())) {
					iterable.remove();
					break;
				}
			}
		}
		return new CommonDto<>(CommonCodeType.SUCCESS);
	}

	/**
	 * 导出自定义模板参数
	 */
	@RequestMapping("/exportExtraField")
	@ResponseBody
	public void exportExtraField(String filter, HttpServletResponse response) throws IOException {
		List<ExtraField> data = Lists.newArrayList(Iterables.filter(GenUtils.extraFields, input -> {
			if (StringUtils.isBlank(filter)) {
				return true;
			} else {
				return filter.equals(input.getExtraFieldName());
			}
		}));
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");

		String fileName = URLEncoder.encode("ExtraFields", "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		EasyExcel.write(response.getOutputStream(), ExtraField.class).sheet("ExtraField").doWrite(data);
	}

	/**
	 * 导入自定义模板参数
	 */
	@PostMapping("/importExtraField")
	@ResponseBody
	public CommonDto importExtraField(MultipartFile file) throws IOException {
		if (GenUtils.extraFields.size() > GenUtils.EXTRA_FIELD_MAX) {
			return new CommonDto(CommonCodeType.EXTRA_FIELD_MAX);
		}
		EasyExcel.read(file.getInputStream(), ExtraField.class, new ExtraFieldListener()).sheet().doRead();
		return new CommonDto(CommonCodeType.SUCCESS);
	}
}
