package com.gusi.demo.hello;

import com.ice.demo.hello.HelloServicePrx;
import com.ice.demo.hello.HelloServicePrxHelper;

/**
 * Created by yydeng on 2017/12/8.
 */
public class HelloClient {
	public static void main(String[] args) {
		int status = 0;
		Ice.Communicator communicator = null;
		try {
			// 获取communicator通信器
			communicator = Ice.Util.initialize(args);
			// 构造一个Proxy对象，通过传入远程服务单元的名称、网络协议、Ip和端口
			Ice.ObjectPrx base = communicator.stringToProxy("HelloServer:default -p 10001");

			HelloServicePrx prxy = HelloServicePrxHelper.checkedCast(base);
			if (prxy == null) {
				throw new NullPointerException("prxy is null!");
			}
			prxy.say("ice");
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
