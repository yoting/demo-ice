package com.gusi.demo.node;

import com.gusi.demo.book.OfflineBookImpl2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gusi.demo.book.OfflineBookImpl;
import com.gusi.demo.book.OnlineBookImpl;

/**
 * Ice Grid Node 部署服务
 *
 * @author yydeng
 * @create 2017-12-12 13:35
 */
public class BookServer extends Ice.Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloServer.class);

	@Override
	public int run(String[] args) {
		if (args.length > 0) {
			LOGGER.error(appName() + ": too many arguments");
			return 1;
		}

		// assemble adapter
		Ice.ObjectAdapter adapter = communicator().createObjectAdapter("BookAdapter");

		// create identity & servant
		Ice.Object onlineBookServant = new OnlineBookImpl();
		Ice.Object offlineBookServant = new OfflineBookImpl2();
		adapter.add(onlineBookServant, communicator().stringToIdentity("OnlineBook"));
		adapter.add(offlineBookServant, communicator().stringToIdentity("OfflineBook"));

		// activate
		adapter.activate();
		communicator().waitForShutdown();
		return 0;
	}

	public static void main(String[] args) {
		LOGGER.info("ready to start");
		BookServer app = new BookServer();
		int status = app.main("BookServer", args, "iceGridNode2Config.properties");
		LOGGER.info("exit with status: " + status);
		System.exit(status);
	}
}
