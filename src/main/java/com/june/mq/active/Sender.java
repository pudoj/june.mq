package com.june.mq.active;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Hello world!
 *
 */
public class Sender {
	
	private static final int SEND_NUMBER = 9;
	public static final String brokerURL = "tcp://10.50.200.38:61616";	// ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL

	public static void main(String[] args) throws JMSException {
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory;
		// Connection ：JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		// Session： 一个发送或接收消息的线程
		Session session;
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination;
		// MessageProducer：消息发送者
		MessageProducer producer;
		// TextMessage message;
		// 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, brokerURL);
		// 构造从工厂得到连接对象
		connection = connectionFactory.createConnection();
        // 启动
		connection.start();
        // 获取操作连接
		session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
		destination = session.createQueue("foo.bar");//
        // 得到消息生成者【发送者】
		producer = session.createProducer(destination);
        // 设置不持久化，此处学习，实际根据项目决定
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        // 构造消息，此处写死，项目就是参数，或者方法获取
		sendMessage(session,producer);
		// 执行
		session.commit();
	}

	private static void sendMessage(Session session, MessageProducer producer) throws JMSException {
		/*
		for (int i = 0; i < SEND_NUMBER; i++) {
			TextMessage message = session.createTextMessage("ActiveMQ Send Message:"+i);
			System.out.println("SendMessage:"+""+i);
			// 发送消息到目的地方
			producer.send(message);
		}//*/
		int i = 0;
		do{
			TextMessage message = session.createTextMessage("ActiveMQ Send Message:"+i);
			System.out.println("SendMessage:"+""+i);
			// 发送消息到目的地方
			producer.send(message);
			i++;
			if(i > SEND_NUMBER)
				break;
		}while(true);
	}
}
