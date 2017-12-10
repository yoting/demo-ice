package com.gusi.demo.hello;

import com.ice.demo.hello.HelloService;

/**
 * Created by yydeng on 2017/12/8.
 */
public class HelloServer {
	public static void main(String[] args) {
		int status = 0;
		Ice.Communicator communicator = null;
		communicator = Ice.Util.initialize(args);
		Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("HelloServiceAdapter",
				"default -p 10001");

		HelloService helloService = new HelloServiceImpl();
		adapter.add(helloService, Ice.Util.stringToIdentity("HelloServer"));
		adapter.activate();
		System.out.println("started!");
		communicator.waitForShutdown();
	}
}
