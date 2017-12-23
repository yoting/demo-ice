package com.gusi.demo.node;

import IceBox.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gusi.demo.hello.HelloServiceImpl;

/**
 * Ice Grid Node 部署服务
 *
 * @author yydeng
 * @create 2017-12-12 13:35
 */
public class HelloServer extends Ice.Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloServer.class);

	@Override
	public int run(String[] args) {
		if (args.length > 0) {
			LOGGER.error(appName() + ": too many arguments");
			return 1;
		}

		// assemble adapter
		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("HelloAdapter");

		// create identity & servant
		Ice.Object helloServant = new HelloServiceImpl();
		adapter.add(helloServant, communicator().stringToIdentity("HelloService"));

		// activate
		adapter.activate();
		communicator().waitForShutdown();
		return 0;
	}

	public static void main(String[] args) {
		LOGGER.info("ready to start");
		HelloServer app = new HelloServer();
		int status = app.main("HelloServer", args, "iceGridNode1Config.properties");
		LOGGER.info("exit with status: " + status);
		System.exit(status);
	}
}
