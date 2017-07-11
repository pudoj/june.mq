/**
 * 中科方德软件有限公司<br>
 * june.activemq:com.june.mq.active.Receiver.java
 * 日期:2017年3月26日
 */
package com.june.mq.active;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Receiver <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年3月26日 上午1:26:00
 * @version 1.0.0
 */
public class Receiver {
	
	// port:61616是activemq的默认的端口
	public static final String brokerURL = "tcp://10.50.200.38:61616";
	
	public static void main(String[] args) throws JMSException {
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
        ConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        Session session;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // 消费者，消息接收者
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, brokerURL);
        // 构造从工厂得到连接对象
        connection = connectionFactory.createConnection();
        // 启动
        connection.start();
        // 获取操作连接
        session = connection.createSession(Boolean.FALSE,
                Session.AUTO_ACKNOWLEDGE);
        // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
        destination = session.createQueue("foo.bar");
        consumer = session.createConsumer(destination);
        while (true) {
            //设置接收者接收消息的时间，为了便于测试，这里设定为10s
            TextMessage message = (TextMessage) consumer.receive(100000);
            if (null != message) {
                System.out.println("收到消息:" + message.getText());
            } else {
                break;
            }
        }
        connection.close();
	}
}
