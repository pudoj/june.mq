/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.spring.ConsumerMain.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.spring;

import org.springframework.context.ApplicationContext;

/**
 * ConsumerMain <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午3:47:50
 * @version 1.0.0
 */
public class ConsumerMain {

	static ApplicationContext context = null;

	/**
	 * @param args
	 * @date 2017年7月13日 下午3:47:50
	 * @writer junehappylove
	 */
	public static void main(String[] args) {
		context  = new org.springframework.context.support.ClassPathXmlApplicationContext("amqp/amqp-consumer.xml");
	}
}
