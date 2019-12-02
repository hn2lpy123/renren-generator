package io.renren.controller;

import com.alibaba.fastjson.JSON;
import io.renren.entity.CommonDto;
import io.renren.entity.ExtraField;
import io.renren.entity.GeneratorInfo;
import io.renren.service.SysGeneratorService;
import io.renren.utils.annotation.NoRepeatSubmit;
import io.renren.utils.constant.CommonCodeType;
import io.renren.utils.constant.ExtraFieldType;
import io.renren.utils.generator.GenUtils;
import io.renren.utils.generator.PageUtils;
import io.renren.utils.generator.Query;
import io.renren.utils.generator.R;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
	 * 获取
	 */
	@RequestMapping("/getExtraFields")
	@ResponseBody
	public CommonDto getExtraFields() {
		List<ExtraField> fields = new ArrayList<>();
		ExtraField field = new ExtraField(ExtraFieldType.STRING.getValue(), "test", "extraFieldValue");
		fields.add(field);
		return new CommonDto<>(CommonCodeType.SUCCESS, fields);
	}
}
