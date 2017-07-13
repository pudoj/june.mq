/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.routing.RoutingSendDirect.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.routing;

import static com.june.mq.rabbit.Consts.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RoutingSendDirect <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午2:49:49
 * @version 1.0.0
 */
public class RoutingSendDirect {

	/**
	 * @param args
	 * @throws TimeoutException
	 * @throws IOException
	 * @date 2017年7月13日 下午2:49:49
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
		// 声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME_ROUTING, "direct");// 注意是direct
		// 发送信息
		for (String routingKey : routingKeys) {
			String message = "RoutingSendDirect Send the message level:" + routingKey;
			channel.basicPublish(EXCHANGE_NAME_ROUTING, routingKey, null, message.getBytes());
			System.out.println("RoutingSendDirect Send" + routingKey + "':'" + message);
		}
		channel.close();
		connection.close();
	}

}
