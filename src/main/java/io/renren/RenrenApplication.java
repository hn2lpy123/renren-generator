package io.renren;

import io.renren.utils.generator.GenUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class RenrenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenrenApplication.class, args);
	}

	@PostConstruct
	private void init() {
		GenUtils.initData.put(GenUtils.PACKAGE, GenUtils.getConfig().getString(GenUtils.PACKAGE));
		GenUtils.initData.put(GenUtils.AUTHOR, GenUtils.getConfig().getString(GenUtils.AUTHOR));
		GenUtils.initData.put(GenUtils.EMAIL, GenUtils.getConfig().getString(GenUtils.EMAIL));
		GenUtils.initData.put(GenUtils.TABLEPREFIX, GenUtils.getConfig().getString(GenUtils.TABLEPREFIX));
	}
}
