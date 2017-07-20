/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.spring.direct.MQProducerImpl.java
 * 日期:2017年7月20日
 */
package com.june.mq.rabbit.spring.direct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MQProducerImpl <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月20日 下午4:33:25
 * @version 1.0.0
 */
public class MQProducerImpl implements MQProducer {

	private static final ApplicationContext context = new ClassPathXmlApplicationContext("amqp/amqp-producer.xml");
	private static final AmqpTemplate template = (AmqpTemplate) context.getBean("rabbitTemplate2");
	
	/* (non-Javadoc)
	 * @see com.june.mq.rabbit.spring.direct.MQProducer#sendDataToQueue(java.lang.String, java.lang.Object)
	 */
	@Override
	public void sendDataToQueue(String queueKey, Object obj) {
		System.out.println(queueKey);
		template.convertAndSend(queueKey, obj);
	}
	//convertAndSend方法: 将Java对象转换为消息发送到匹配Key的交换机中Exchange，由于配置了JSON转换，这里是将Java对象转换成JSON字符串的形式
}
