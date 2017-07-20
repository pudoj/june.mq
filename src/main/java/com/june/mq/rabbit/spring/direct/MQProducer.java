/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.spring.direct.MQProducer.java
 * 日期:2017年7月20日
 */
package com.june.mq.rabbit.spring.direct;

/**
 * MQProducer <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月20日 下午4:32:25
 * @version 1.0.0
 */
public interface MQProducer {
	
	/**
	 * 发送消息到指定队列
	 * 
	 * @param routingKey
	 * @param massage
	 */
	void sendDataToQueue(String routingKey, Object massage);
}
