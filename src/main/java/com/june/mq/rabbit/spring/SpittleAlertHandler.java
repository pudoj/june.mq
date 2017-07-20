package com.june.mq.rabbit.spring;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 
 * SpittleAlertHandler <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 */
public class SpittleAlertHandler implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			String body = new String(message.getBody(), "UTF-8");
			System.out.println(body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
