/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.NewTask.java
 * 日期:2017年7月11日
 */
package com.june.mq.rabbit;

import static com.june.mq.rabbit.Consts.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * NewTask <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月11日 下午5:53:02
 * @version 1.0.0
 */
public class NewTask {

	/**
	 * @param args
	 * @throws IOException
	 * @throws TimeoutException
	 * @date 2017年7月11日 下午5:53:02
	 * @writer junehappylove
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setPort(port);
		factory.setVirtualHost(virtualHost);
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		// 分发信息
		for (int i = 0; i < 20; i++) {
			String message = "Hello RabbitMQ" + i;
			channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println("NewTask send '" + message + "'");
		}
		channel.close();
		connection.close();
	}

}
