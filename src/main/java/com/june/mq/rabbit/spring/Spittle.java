/**
 * 中科方德软件有限公司<br>
 * june.mq:com.june.mq.rabbit.spring.Spittle.java
 * 日期:2017年7月13日
 */
package com.june.mq.rabbit.spring;

import java.io.Serializable;
import java.util.Date;

/**
 * Spittle <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @blog https://www.github.com/junehappylove
 * @date 2017年7月13日 下午3:42:21
 * @version 1.0.0
 */
public class Spittle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Spitter spitter;
	private String message;
	private Date postedTime;

	public Spittle(Long id, Spitter spitter, String message, Date postedTime) {
		this.id = id;
		this.spitter = spitter;
		this.message = message;
		this.postedTime = postedTime;
	}

	public Long getId() {
		return this.id;
	}

	public String getMessage() {
		return this.message;
	}

	public Date getPostedTime() {
		return this.postedTime;
	}

	public Spitter getSpitter() {
		return this.spitter;
	}
}
