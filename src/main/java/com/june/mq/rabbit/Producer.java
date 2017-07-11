/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.Producer.java
 * 日期:2017年7月11日
 */
package com.june.mq.rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import static com.june.mq.rabbit.Consts.*;

/**
 * Producer <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月11日 下午5:21:46
 * @version 1.0.0
 */
public class Producer {

	/**
	 * @param args
	 * @throws TimeoutException
	 * @throws IOException
	 * @date 2017年7月11日 下午5:21:46
	 * @writer junehappylove
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host);
		factory.setUsername(username);
		factory.setPassword(password);
		factory.setPort(port);
		factory.setVirtualHost(virtualHost);
		Connection connection = null;
		Channel channel = null;
		try {
			// 创建一个新的连接
			connection = factory.newConnection();
			// 创建一个通道
			channel = connection.createChannel();
			// 声明一个队列
			// queueDeclare第一个参数表示队列名称
			//第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）
			//第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）
			//第四个参数为当所有消费者客户端连接断开时是否自动删除队列
			//第五个参数为队列的其他参数
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "{\"temperature\":100}";
			// 发送消息到队列中
			//basicPublish第一个参数为交换机名称
			//第二个参数为队列映射的路由key
			//第三个参数为消息的其他属性
			//第四个参数为发送信息的主体
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
			System.out.println("Producer Send +'" + message + "'");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭通道和连接
			channel.close();
			connection.close();
		}
	}

}
