/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rocket.PushConsumer.java
 * 日期:2017年3月26日
 */
package com.june.mq.rocket;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * PushConsumer <br>
 * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br>
 * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年3月26日 上午10:14:15
 * @version 1.0.0
 */
public class PushConsumer {

	public static final String group = "ConsumerGroupName";
	public static final String url = "10.50.200.239:9876";
	public static final String instanceName = "Consumber";

	/**
	 * @param args
	 * @throws MQClientException 
	 * @throws InterruptedException 
	 * @date 2017年3月26日 上午10:14:15
	 * @writer junehappylove
	 */
	public static void main(String[] args) throws MQClientException, InterruptedException {
		// TODO
		/**
		 * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
		 * 注意：ConsumerGroupName需要由应用来保证唯一
		 */
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
		consumer.setNamesrvAddr(url);
		consumer.setInstanceName(instanceName);

		/**
		 * 订阅指定topic下tags分别等于TagA或TagC或TagD
		 */
		consumer.subscribe("TopicTest1", "TagA || TagC || TagD");
		/**
		 * 订阅指定topic下所有消息<br>
		 * 注意：一个consumer对象可以订阅多个topic
		 */
		consumer.subscribe("TopicTest2", "*");
		//consumer.setConsumeTimeout(10000);//设置过时时间10s
		
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			
			/**
			 * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
			 */
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());

				MessageExt msg = msgs.get(0);
				
				if (msg.getTopic().equals("TopicTest1")) {
					System.out.println("执行TopicTest1的消费逻辑开始");
					// 执行TopicTest1的消费逻辑
					if (msg.getTags() != null && msg.getTags().equals("TagA")) {
						// 执行TagA的消费
						System.out.println("执行TagA的消费开始");
						System.out.println(new String(msg.getBody()));
					} else if (msg.getTags() != null && msg.getTags().equals("TagC")) {
						System.out.println("执行TagC的消费开始");
						// 执行TagC的消费
					} else if (msg.getTags() != null && msg.getTags().equals("TagD")) {
						// 执行TagD的消费
						System.out.println("执行TagD的消费开始");
					}
				} else if (msg.getTopic().equals("TopicTest2")) {
					System.out.println("执行TopicTest2的消费逻辑开始");
					System.out.println(new String(msg.getBody()));
				}

				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		/**
		 * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		 */
		consumer.start();

		System.out.println("Consumer Started.");
		Thread.sleep(5000);//延时5s后结束
		System.out.println("Consumer Shutdown.");
		consumer.shutdown();
	}

}
