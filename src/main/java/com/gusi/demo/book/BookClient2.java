package com.gusi.demo.book;

import com.ice.demo.book.Message;
import com.ice.demo.book.OfflineBookPrx;
import com.ice.demo.book.OfflineBookPrxHelper;
import com.ice.demo.book.OnlineBookPrx;
import com.ice.demo.book.OnlineBookPrxHelper;

import java.util.concurrent.TimeUnit;

/**
 * Created by yydeng on 2017/12/8.
 */
public class BookClient2 {
	public static void main(String[] args) throws InterruptedException {
		String[] initPrames = new String[] {
				"--Ice.Default.Locator=IceGrid/Locator:tcp -h 10.168.18.144 -p 4061" };

		online(initPrames);
		TimeUnit.SECONDS.sleep(2);
		offline(initPrames);
	}

	public static void online(String[] initParams) {
		int status = 0;
		Ice.Communicator communicator = null;
		try {
			// 获取communicator通信器
			communicator = Ice.Util.initialize(initParams);
			// 构造一个Proxy对象，通过传入远程服务单元的名称、网络协议、Ip和端口
			Ice.ObjectPrx base = communicator.stringToProxy("OnlineBookServer@OnlineBookAdapter");

			OnlineBookPrx prxy = OnlineBookPrxHelper.checkedCast(base);
			if (prxy == null) {
				throw new NullPointerException("prxy is null!");
			}

			Message msg = new Message();
			msg.name = "gusi";
			msg.type = 1;
			msg.price = 99.9;
			msg.valid = true;
			msg.content = "this is a good book!";

			Message result = prxy.bookTick(msg);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			communicator.destroy();
			// System.exit(status);
		}
	}

	public static void offline(String[] initParams) {
		int status = 0;
		Ice.Communicator communicator = null;
		try {
			// 获取communicator通信器
			communicator = Ice.Util.initialize(initParams);
			// 构造一个Proxy对象，通过传入远程服务单元的名称、网络协议、Ip和端口
			Ice.ObjectPrx base = communicator.stringToProxy("OfflineBookServer@OfflineBookAdapter");

			OfflineBookPrx prxy = OfflineBookPrxHelper.checkedCast(base);
			if (prxy == null) {
				throw new NullPointerException("prxy is null!");
			}

			Message msg = new Message();
			msg.name = "gusi";
			msg.type = 1;
			msg.price = 99.9;
			msg.valid = true;
			msg.content = "this is a good book!";

			boolean[] result = prxy.bookTrance(new Message[] { msg });
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			communicator.destroy();
			// System.exit(status);
		}
	}
}
