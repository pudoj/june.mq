/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.spring.ProducerMain.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.spring;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.june.mq.rabbit.spring.direct.MQProducer;

/**
 * ProducerMain <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午3:44:02
 * @version 1.0.0
 */
public class ProducerMain {

	private static ApplicationContext context;

	/**
	 * @param args
	 * @throws InterruptedException
	 * @date 2017年7月13日 下午3:44:02
	 * @writer junehappylove
	 */
	public static void main(String[] args) throws InterruptedException {
		context = new ClassPathXmlApplicationContext("amqp/amqp-producer.xml");
		AmqpTemplate template = (AmqpTemplate) context.getBean("rabbitTemplate");
		for (int i = 0; i < 2; i++) {
			System.out.println("Sending message #" + i);
			Spittle spittle = new Spittle((long) i, null, "Hello world (" + i + ")", new Date());
			template.convertAndSend(spittle);
			Thread.sleep(2000);
		}
		System.out.println("Done!");
		
		System.out.println("Direct...");
		MQProducer mqProducer = (MQProducer) context.getBean("mqProducer");
		mqProducer.sendDataToQueue("spring.test.queueKey1", "Hello World spring.test.queueKey1");
		mqProducer.sendDataToQueue("spring.test.queueKey2", "Hello World spring.test.queueKey2");
		System.out.println("route done");
	}

}
