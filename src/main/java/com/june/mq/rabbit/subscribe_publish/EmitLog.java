/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.subscribe_publish.EmitLog.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.subscribe_publish;

import static com.june.mq.rabbit.Consts.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * EmitLog <br>
 * 发送端
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午2:37:37
 * @version 1.0.0
 */
public class EmitLog {

	/**
	 * @param args
	 * @throws TimeoutException
	 * @throws IOException
	 * @date 2017年7月13日 下午2:37:37
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

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");// fanout表示分发，所有的消费者得到同样的队列信息
		// 分发信息
		for (int i = 0; i < 5; i++) {
			String message = "Hello World" + i;
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println("EmitLog Sent '" + message + "'");
		}
		channel.close();
		connection.close();

	}

}
