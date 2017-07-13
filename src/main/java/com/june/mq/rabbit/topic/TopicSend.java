/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.topic.TopicSend.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.topic;

import static com.june.mq.rabbit.Consts.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * TopicSend <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午3:03:24
 * @version 1.0.0
 */
public class TopicSend {

	/**
	 * @param args
	 * @throws TimeoutException
	 * @throws IOException
	 * @date 2017年7月13日 下午3:03:24
	 * @writer junehappylove
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			factory.setUsername(username);
			factory.setPassword(password);
			factory.setPort(port);
			factory.setVirtualHost(virtualHost);
			connection = factory.newConnection();
			channel = connection.createChannel();

			// 声明一个匹配模式的交换机
			channel.exchangeDeclare(EXCHANGE_NAME_TOPIC, "topic");
			// 待发送的消息
			String[] routingKeys = new String[] { "quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
					"lazy.brown.fox", "quick.brown.fox", "quick.orange.male.rabbit", "lazy.orange.male.rabbit" };
			// 发送消息
			for (String severity : routingKeys) {
				String message = "From " + severity + " routingKey' s message!";
				channel.basicPublish(EXCHANGE_NAME_TOPIC, severity, null, message.getBytes());
				System.out.println("TopicSend Sent '" + severity + "':'" + message + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (connection != null) {
				channel.close();
				connection.close();
			}
		} finally {
			if (connection != null) {
				channel.close();
				connection.close();
			}
		}

	}

}
