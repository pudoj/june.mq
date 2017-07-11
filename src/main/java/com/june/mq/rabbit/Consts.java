/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.Consts.java
 * 日期:2017年7月11日
 */
package com.june.mq.rabbit;

/**
 * Consts <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月11日 下午5:33:31
 * @version 1.0.0
 */
public final class Consts {
	public final static String QUEUE_NAME = "rabbitmq_test";
	public final static String host = "10.50.200.234";
	public final static String username = "june";
	public final static String password = "june";
	public final static int port = 5672;
	public final static String virtualHost = "/";
	
	public static final String TASK_QUEUE_NAME="task_queue";
}
