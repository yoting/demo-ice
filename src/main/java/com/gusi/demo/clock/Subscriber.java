package com.gusi.demo.clock;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 闹钟订阅-相当于服务端
 *
 * @author yydeng
 * @create 2017-12-13 10:30
 */
public class Subscriber extends Ice.Application {
	public int run(String[] args) {

		String topicSource = "DemoIceStorm/TopicManager:default -h localhost -p 10000";// 该地址其实是服务端IceBox的地址
		String topicName = "time";

		// Ice.ObjectPrx base =
		// communicator().propertyToProxy("TopicManager.Proxy");
		Ice.ObjectPrx base = communicator().stringToProxy(topicSource);
		IceStorm.TopicManagerPrx manager = IceStorm.TopicManagerPrxHelper.checkedCast(base);
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

		// Ice.ObjectAdapter adapter =
		// communicator().createObjectAdapter("Clock.Subscriber");
		Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints("ClockSubscriber",
				"tcp -h localhost -p 10009:udp");// 也可以指定具体的endpoints地址

		String id = UUID.randomUUID().toString();
		Ice.Identity subId = new Ice.Identity(id, "");
		Ice.ObjectPrx subscriber = adapter.add(new ClockServiceImpl(), subId);

		try {
			Map qos = new HashMap();// 在map中可以添加设置订阅属性等
			qos.put("retryCount", "2");// retryCount:重试次数
			topic.subscribeAndGetPublisher(qos, subscriber);
		} catch (IceStorm.AlreadySubscribed e) {
			// If we're manually setting the subscriber id ignore.
			if (id == null) {
				e.printStackTrace();
				return 1;
			} else {
				System.out.println("reactivating persistent subscriber");
			}
		} catch (IceStorm.BadQoS e) {
			e.printStackTrace();
			return 1;
		}
		adapter.activate();

		shutdownOnInterrupt();
		communicator().waitForShutdown();

		topic.unsubscribe(subscriber);

		return 0;
	}

	public static void main(String[] args) {
		Subscriber app = new Subscriber();
		int status = app.main("Subscriber", args); // 此处可以传入配置文件，在订阅的时候也可以从配置文件中读取属性信息
		System.exit(status);
	}
}