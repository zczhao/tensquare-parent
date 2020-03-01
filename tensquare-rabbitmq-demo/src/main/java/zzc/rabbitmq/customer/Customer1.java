package zzc.rabbitmq.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "zzc-1")
public class Customer1 {

	@RabbitHandler
	public void getMsg(String msg) {
		//System.out.println("直接模式消费信息：" + msg);
		System.out.println("zzc-1：" + msg);
	}

}
