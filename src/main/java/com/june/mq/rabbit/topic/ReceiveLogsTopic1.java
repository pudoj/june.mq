/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.topic.ReceiveLogsTopic1.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.topic;

import static com.june.mq.rabbit.Consts.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * ReceiveLogsTopic1 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午3:06:20
 * @version 1.0.0
 */
public class ReceiveLogsTopic1 {

	/**
	 * @param args
	 * @throws IOException
	 * @throws TimeoutException
	 * @date 2017年7月13日 下午3:06:20
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

		// 声明一个匹配模式的交换机
		channel.exchangeDeclare(EXCHANGE_NAME_TOPIC, "topic");
		String queueName = channel.queueDeclare().getQueue();
		// 路由关键字
		String[] routingKeys = new String[] { "*.orange.*" };
		// 绑定路由
		for (String routingKey : routingKeys) {
			channel.queueBind(queueName, EXCHANGE_NAME_TOPIC, routingKey);
			System.out.println("ReceiveLogsTopic1 exchange:" + EXCHANGE_NAME_TOPIC + ", queue:" + queueName
					+ ", BindRoutingKey:" + routingKey);
		}
		System.out.println("ReceiveLogsTopic1 Waiting for messages");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("ReceiveLogsTopic1 Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);

	}

}
