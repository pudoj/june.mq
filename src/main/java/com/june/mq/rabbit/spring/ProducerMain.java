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

/**
 * ProducerMain <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午3:44:02
 * @version 1.0.0
 */
public class ProducerMain {

	/**
	 * @param args
	 * @date 2017年7月13日 下午3:44:02
	 * @writer junehappylove
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("amqp/amqp-producer.xml");
        AmqpTemplate template = (AmqpTemplate) context.getBean("rabbitTemplate");
        for (int i = 0; i < 20; i++) {
            System.out.println("Sending message #" + i);
            Spittle spittle = new Spittle((long) i, null, "Hello world (" + i + ")", new Date());
            template.convertAndSend(spittle);
            Thread.sleep(5000);
        }
        System.out.println("Done!");

	}

}
