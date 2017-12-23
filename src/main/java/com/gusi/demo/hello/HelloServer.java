package com.gusi.demo.hello;

import com.ice.demo.hello.HelloService;

/**
 * 服务端启动类<br>
 * Created by yydeng on 2017/12/8.
 */
public class HelloServer {
	public static void main(String[] args) {
		int status = 0;
		// 创建通信器
		Ice.Communicator communicator = Ice.Util.initialize(args);
		// 通过通信器创建适配器，包括适配器的名称、适配器的通信协议（default=tcp）以及地址和端口
		Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("HelloServiceAdapter",
				"default -h localhost -p 10001");

		// 定义服务单元，并且将该服务单元加入到上面的适配器中
		HelloService helloService = new HelloServiceImpl();
		adapter.add(helloService, Ice.Util.stringToIdentity("HelloServer"));

		// 激活适配器，并且阻塞线程
		adapter.activate();
		System.out.println("started!");
		communicator.waitForShutdown();
	}
}
