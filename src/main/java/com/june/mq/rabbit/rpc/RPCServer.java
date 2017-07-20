/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.rpc.RPCServer.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.rpc;

import static com.june.mq.rabbit.Consts.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * RPCServer <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午3:18:15
 * @version 1.0.0
 */
@SuppressWarnings("deprecation")
public class RPCServer {

	/**
	 * @param args
	 * @throws TimeoutException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ConsumerCancelledException
	 * @throws ShutdownSignalException
	 */
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException,
			ConsumerCancelledException, InterruptedException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setPort(port);
		factory.setVirtualHost(virtualHost);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
		channel.basicQos(1);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

		System.out.println("RPCServer Awating RPC request");
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			BasicProperties props = delivery.getProperties();
			BasicProperties replyProps = new AMQP.BasicProperties.Builder().correlationId(props.getCorrelationId())
					.build();

			String message = new String(delivery.getBody(), "UTF-8");
			int n = Integer.parseInt(message);

			System.out.println("RPCServer fib(" + message + ")");
			String response = "" + fib(n);
			channel.basicPublish("", props.getReplyTo(), replyProps, response.getBytes());
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}

	private static int fib(int n) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		return fib(n - 1) + fib(n - 1);
	}
}
