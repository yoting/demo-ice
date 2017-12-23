package com.gusi.demo.clock;

import Ice.InitializationData;
import com.gusi.demo.utils.IceLogger;
import com.ice.demo.clock.ClockServicePrx;
import com.ice.demo.clock.ClockServicePrxHelper;

/**
 * 闹钟发布-相当于客户端<br>
 * 将消息往IceBox上发送即可，订阅者订阅了IceBox上的话题后就会收到推送调运
 *
 * @author yydeng
 * @create 2017-12-13 10:33
 */
public class Publisher extends Ice.Application {

	public int run(String[] args) {
		String topicMngEndpoints = "DemoIceStorm/TopicManager:default -p 10000";
		String topicName = "time";

		// IceStorm.TopicManagerPrx manager = IceStorm.TopicManagerPrxHelper
		// .checkedCast(communicator().propertyToProxy("TopicManager.Proxy"));
		IceStorm.TopicManagerPrx manager = IceStorm.TopicManagerPrxHelper
				.checkedCast(communicator().stringToProxy(topicMngEndpoints));
		if (manager == null) {
			System.err.println("invalid proxy");
			return 1;
		}

		// Retrieve the topic.
		IceStorm.TopicPrx topic;
		try {
			topic = manager.retrieve(topicName);
		} catch (IceStorm.NoSuchTopic e) {
			try {
				topic = manager.create(topicName);
			} catch (IceStorm.TopicExists ex) {
				System.err.println(appName() + ": temporary failure, try again.");
				return 1;
			}
		}

		Ice.ObjectPrx publisher = topic.getPublisher();
		ClockServicePrx clock = ClockServicePrxHelper.uncheckedCast(publisher);

		System.out.println("publishing tick events. Press ^C to terminate the application.");
		try {
			java.text.SimpleDateFormat date = new java.text.SimpleDateFormat("MM/dd/yy HH:mm:ss:SSS");
			while (true) {

				clock.tick(date.format(new java.util.Date()));

				try {
					Thread.currentThread().sleep(2000);
				} catch (java.lang.InterruptedException e) {
				}
			}
		} catch (Ice.CommunicatorDestroyedException ex) {
			// Ignore
		}

		return 0;
	}

	public static void main(String[] args) {
		Publisher app = new Publisher();
		InitializationData initData = new InitializationData();
		initData.logger = new IceLogger("iceStormLogger");
		int status = app.main("Publisher", args, initData);
		System.exit(status);
	}
}
