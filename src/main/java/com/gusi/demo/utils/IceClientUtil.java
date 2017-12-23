package com.gusi.demo.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import Ice.InitializationData;
import Ice.ObjectPrx;

import com.ice.demo.hello.HelloServicePrx;
import com.ice.demo.hello.HelloServicePrxHelper;

/**
 * client工具封装
 *
 * @author yydeng
 * @create 2017-12-13 16:40
 */
public class IceClientUtil {
	private static volatile Ice.Communicator communicator = null;

	private static Map<Class, ObjectPrx> clazz2PrxMap = new HashMap<>();
	private static volatile long lastAccessTimestamp;
	private static volatile MonitorThread monitorThread;
	private static long idleTimeOutSeconds = 0;
	private static String iceLocator = null;
	private static final String locatorKey = "Ice.Default.Locator";

	private static String locatorValue = "IceGrid/Locator:tcp -h 10.168.18.144 -p 4061";

	public static Ice.Communicator getIceCommunicator() {
		if (communicator == null) {
			synchronized (IceClientUtil.class) {
				if (communicator == null) {
					if (iceLocator == null) {
						// ResourceBundle resourceBundle =
						// ResourceBundle.getBundle("clinet.properties");
						iceLocator = locatorValue;// resourceBundle.getString("");
						idleTimeOutSeconds = 100;
					}
					InitializationData initData = new InitializationData();
					initData.properties = Ice.Util.createProperties();
					initData.properties.setProperty(locatorKey, iceLocator);
					initData.logger = new IceLogger("iceSystemLogger");
					// 注意在initialize构建communicator的时候，如果是使用initData，其key是不需要--的。
					communicator = Ice.Util.initialize(initData);
					createMonitorThread();
				}
			}
		}

		lastAccessTimestamp = System.currentTimeMillis();
		return communicator;
	}

	private static void createMonitorThread() {
		monitorThread = new MonitorThread();
		monitorThread.setDaemon(true);
		monitorThread.start();
	}

	public static void closeCommunicator(boolean removeServiceCache) {
		synchronized (IceClientUtil.class) {
			if (communicator != null) {
				safeShutdown();
				monitorThread.interrupt();
				if (removeServiceCache && !clazz2PrxMap.isEmpty()) {
					try {
						clazz2PrxMap.clear();
					} catch (Exception e) {

					}
				}
			}
		}
	}

	private static void safeShutdown() {
		communicator.shutdown();

		communicator.destroy();
	}

	private static ObjectPrx createIceProxy(Ice.Communicator communicator, Class clazz) {
		ObjectPrx proxy = null;
		String className = clazz.getName();
		String serviceName = clazz.getSimpleName();
		int pos = serviceName.lastIndexOf("Prx");
		if (pos <= 0) {

		}
		String realServiceName = serviceName.substring(0, pos);// identityName
		try {
			Ice.ObjectPrx base = communicator.stringToProxy(realServiceName);
			proxy = (ObjectPrx) clazz.forName(className + "Helper").newInstance();
			Method method = proxy.getClass().getDeclaredMethod("checkedCast", ObjectPrx.class);
			proxy = (ObjectPrx) method.invoke(proxy, base);
			return proxy;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T> T getServicePrx(Class<T> clazz) {
		ObjectPrx proxy = clazz2PrxMap.get(clazz);
		if (proxy != null) {
			lastAccessTimestamp = System.currentTimeMillis();
			return (T) proxy;
		}
		proxy = createIceProxy(getIceCommunicator(), clazz);
		clazz2PrxMap.put(clazz, proxy);
		lastAccessTimestamp = System.currentTimeMillis();
		return (T) proxy;
	}

	static class MonitorThread extends Thread {
		public void run() {
			try {
				while (!Thread.currentThread().isInterrupted()) {
					Thread.sleep(5000L);

					if (lastAccessTimestamp + idleTimeOutSeconds * 1000L < System.currentTimeMillis()) {
						closeCommunicator(true);
					}
				}
			} catch (Exception e) {

			}
		}
	}

	public static void main(String[] args) {
		HelloServicePrx prx = IceClientUtil.getServicePrx(HelloServicePrx.class);
		System.out.println(prx.calculate(1, 2));

		IceClientUtil.getServicePrx(HelloServicePrx.class).say("gusi");
	}
}
