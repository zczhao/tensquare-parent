package zzc.springcloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.power.doc.builder.ApiDocBuilder;
import com.power.doc.model.ApiConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmartDocApplicationTest {
	/**
	 * 测试生成文档
	 */
	@Test
	public void testBuilderControllersApiSimple() {
		ApiConfig config = new ApiConfig();
		// 服务地址
		config.setServerUrl("http://localhost:8010");
		// 生成到一个文档
		config.setAllInOne(true);
		// 采用严格模式
		config.isStrict();
		// 文档输出路径
		config.setOutPath("C:\\workspace\\logs\\");
		ApiDocBuilder.builderControllersApi(config);
		// 将生成的文档输出到/Users/dujf/Downloads/md目录下，严格模式下api-doc会检测Controller的接口注释
	}
}
