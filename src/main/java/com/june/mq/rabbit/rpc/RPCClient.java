/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.rpc.RPCClient.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.rpc;

import static com.june.mq.rabbit.Consts.RPC_QUEUE_NAME;
import static com.june.mq.rabbit.Consts.host;
import static com.june.mq.rabbit.Consts.password;
import static com.june.mq.rabbit.Consts.port;
import static com.june.mq.rabbit.Consts.username;
import static com.june.mq.rabbit.Consts.virtualHost;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * RPCClient <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午3:22:10
 * @version 1.0.0
 */
@SuppressWarnings({ "deprecation" })
public class RPCClient {
	private Connection connection;
	private Channel channel;
	private String requestQueueName = RPC_QUEUE_NAME;
	private String replyQueueName;
	// private QueueingConsumer consumer;
	private QueueingConsumer consumer;

	public RPCClient() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setPort(port);
		factory.setVirtualHost(virtualHost);
		connection = factory.newConnection();
		channel = connection.createChannel();

		replyQueueName = channel.queueDeclare().getQueue();
		// consumer = new QueueingConsumer(channel);
		consumer = new QueueingConsumer(channel);
		channel.basicConsume(replyQueueName, true, consumer);
	}

	public String call(String message) throws IOException, InterruptedException {
		String response;
		String corrID = UUID.randomUUID().toString();
		AMQP.BasicProperties props = new AMQP.BasicProperties().builder().correlationId(corrID).replyTo(replyQueueName)
				.build();
		channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			if (delivery.getProperties().getCorrelationId().equals(corrID)) {
				response = new String(delivery.getBody(), "UTF-8");
				break;
			}
		}
		return response;
	}

	public void close() throws Exception {
		connection.close();
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		RPCClient rpcClient = null;
		String response;
		try {
			rpcClient = new RPCClient();
			System.out.println("RPCClient  Requesting fib(20)");
			response = rpcClient.call("20");
			System.out.println("RPCClient  Got '" + response + "'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rpcClient != null) {
				rpcClient.close();
			}
		}

	}

}
