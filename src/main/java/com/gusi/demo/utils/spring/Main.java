package com.gusi.demo.utils.spring;

import com.ice.demo.book.Message;
import com.ice.demo.book.OnlineBook;
import com.ice.demo.book.OnlineBookPrx;
import com.ice.demo.hello.HelloService;
import com.ice.demo.hello.HelloServicePrx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main
 *
 * @author yydeng
 * @create 2017-12-13 19:54
 */
public class Main {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-ice.xml");
		HelloServicePrx helloService = (HelloServicePrx) applicationContext.getBean("helloService");
		int result = helloService.calculate(1, 5);
		System.out.println(result);

		OnlineBookPrx onlineBookPrx = (OnlineBookPrx) applicationContext.getBean("onlineBook");
		System.out.println(onlineBookPrx.bookTick(new Message()));
	}
}
