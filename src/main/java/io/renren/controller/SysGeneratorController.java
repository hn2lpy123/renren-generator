package io.renren.controller;

import com.alibaba.fastjson.JSON;
import io.renren.RenrenApplication;
import io.renren.entity.GeneratorInfo;
import io.renren.service.SysGeneratorService;
import io.renren.utils.GenUtils;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
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
	@RequestMapping("/updateGeneratorInfo")
	public void updateGeneratorInfo(@RequestBody GeneratorInfo generatorInfo) {
		GenUtils.initData.put(GenUtils.PACKAGE, generatorInfo.getPackageInfo());
		GenUtils.initData.put(GenUtils.AUTHOR, generatorInfo.getAuthor());
		GenUtils.initData.put(GenUtils.EMAIL, generatorInfo.getEmail());
	}

	/**
	 * 设置生成代码的作者信息和包信息
	 */
	@RequestMapping("/getGeneratorInfo")
	@ResponseBody
	public GeneratorInfo getGeneratorInfo() {
		return new GeneratorInfo(GenUtils.initData.get(GenUtils.PACKAGE).toString(),
				GenUtils.initData.get(GenUtils.AUTHOR).toString(), GenUtils.initData.get(GenUtils.EMAIL).toString());
	}
}
