package io.renren;

import io.renren.bean.ExcelRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RenrenApplicationTests {

	@Autowired
	DataSource dataSource;

	@Test
	public void reflectionsTest() throws IllegalAccessException, InstantiationException {
		Reflections reflections = new Reflections("io.renren");
		Set<Class<? extends ExcelRow>> subTypes = reflections.getSubTypesOf(ExcelRow.class);
		for (Class clazz : subTypes) {
			System.out.println(clazz.getDeclaredFields().length);
		}
	}

	@Test
	public void testDataSource() throws SQLException {
		System.out.println(dataSource.getConnection());
	}

}
