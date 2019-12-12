package io.renren;

import io.renren.bean.ExcelRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RenrenApplicationTests {

	@Test
	public void reflectionsTest() throws IllegalAccessException, InstantiationException {
		Reflections reflections = new Reflections("io.renren");
		Set<Class<? extends ExcelRow>> subTypes = reflections.getSubTypesOf(ExcelRow.class);
		for (Class clazz : subTypes) {
			System.out.println(clazz.getDeclaredFields().length);
		}
	}

}
