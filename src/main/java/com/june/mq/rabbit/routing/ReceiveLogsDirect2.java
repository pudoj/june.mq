/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.routing.ReceiveLogsDirect2.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.routing;

import static com.june.mq.rabbit.Consts.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * ReceiveLogsDirect2 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午2:57:32
 * @version 1.0.0
 */
public class ReceiveLogsDirect2 {

	/**
	 * @param args
	 * @throws IOException
	 * @throws TimeoutException
	 * @date 2017年7月13日 下午2:57:32
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
		// 声明交换器
		channel.exchangeDeclare(EXCHANGE_NAME_ROUTING, "direct");
		// 获取匿名队列名称
		String queueName = channel.queueDeclare().getQueue();
		// 根据路由关键字进行多重绑定
		for (String severity : routingKeys2) {
			channel.queueBind(queueName, EXCHANGE_NAME_ROUTING, severity);
			System.out.println("ReceiveLogsDirect2 exchange:" + EXCHANGE_NAME_ROUTING + ", queue:" + queueName
					+ ", BindRoutingKey:" + severity);
		}
		System.out.println("ReceiveLogsDirect2 Waiting for messages");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws UnsupportedEncodingException {
				String message = new String(body, "UTF-8");
				System.out.println("ReceiveLogsDirect2 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);

	}

}
