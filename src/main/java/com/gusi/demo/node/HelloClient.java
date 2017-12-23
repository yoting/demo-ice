package com.gusi.demo.node;

import com.ice.demo.hello.HelloServicePrx;
import com.ice.demo.hello.HelloServicePrxHelper;

import java.util.Date;

/**
 * node1 client
 *
 * @author yydeng
 * @create 2017-12-12 18:54
 */
public class HelloClient {
	public static void main(String[] args) {
		String[] initPrames = new String[] {
				"--Ice.Default.Locator=IceGrid/Locator:tcp -h 10.168.18.144 -p 4061" };
		int status = 0;
		Ice.Communicator communicator = null;
		try {
			// 获取communicator通信器
			communicator = Ice.Util.initialize(initPrames);
			// 构造一个Proxy对象，通过传入远程服务单元的名称、网络协议、Ip和端口
			Ice.ObjectPrx base = communicator.stringToProxy("HelloService");

			HelloServicePrx prxy = HelloServicePrxHelper.checkedCast(base);
			if (prxy == null) {
				throw new NullPointerException("prxy is null!");
			}
			prxy.say(""+new Date());
			int result = prxy.calculate(1, 2);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			communicator.destroy();
			System.exit(status);
		}
	}
}
