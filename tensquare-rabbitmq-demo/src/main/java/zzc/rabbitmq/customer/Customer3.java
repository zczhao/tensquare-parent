package zzc.rabbitmq.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "zzc-3")
public class Customer3 {

	@RabbitHandler
	public void getMsg(String msg) {
		System.out.println("zzc-3：" + msg);
	}

}
