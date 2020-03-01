package zzc.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMQApplication.class)
public class RabbitMQProductTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * 直接模式(Direct)
	 */
	@Test
	public void sendMsgDirect() {
		rabbitTemplate.convertAndSend("zzc", "直接模式测试");
	}
	
	/**
	 * 分裂模式(Fanout)
	 */
	@Test
	public void sendMsgFanout() {
		rabbitTemplate.convertAndSend("zzc-fanout", "", "分裂模式测试");
	}
	
	/**
	 * 主题模式(Topic)
	 * # 匹配任意多个字符
	 * * 匹配一个字符
	 * #.log(可匹配：good.abc.log)
	 * good.#(可匹配：good.abc good.abc.log good.abc.efg)
	 * good.*(可匹配：good.a)
	 * good.log(可匹配：good.log)
	 */
	@Test
	public void sendMsgTopic() {
		rabbitTemplate.convertAndSend("zzc-topic", "good.log", "主题模式测试");
	}
}
