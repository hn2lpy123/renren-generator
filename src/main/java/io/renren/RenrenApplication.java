package io.renren;

import io.renren.utils.GenUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
@MapperScan("io.renren.dao")
public class RenrenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenrenApplication.class, args);
	}

	@PostConstruct
	private void init() {
		GenUtils.initData.put(GenUtils.PACKAGE, GenUtils.getConfig().getString(GenUtils.PACKAGE));
		GenUtils.initData.put(GenUtils.AUTHOR, GenUtils.getConfig().getString(GenUtils.AUTHOR));
		GenUtils.initData.put(GenUtils.EMAIL, GenUtils.getConfig().getString(GenUtils.EMAIL));
	}
}
