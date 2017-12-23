package com.gusi.demo.book;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import IceBox.Service;

import com.ice.demo.book.Message;
import com.ice.demo.book.OnlineBookPrx;
import com.ice.demo.book.OnlineBookPrxHelper;
import com.ice.demo.book._OfflineBookDisp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yydeng on 2017/12/11.
 */
public class OfflineBookImpl2 extends _OfflineBookDisp implements Service {
	private static Logger logger = LoggerFactory.getLogger(OfflineBookImpl2.class);
	private ObjectAdapter _adapter;

	@Override
	public void start(String s, Communicator communicator, String[] strings) {
		_adapter = communicator.createObjectAdapter(s);

		Ice.Object object = this;
		_adapter.add(object, communicator.stringToIdentity(s));
		_adapter.activate();
		System.out.println("staring...");
	}

	@Override
	public void stop() {
		System.out.println("stoping...");
	}

	@Override
	public boolean[] bookTrance(Message[] msg, Current __current) {
		System.out.println("receive offline book request!" + msg.length);
		// 注意这里不能直接用_adapter属性对象获取communicator对象，_adapter为空
		Ice.ObjectPrx base = __current.adapter.getCommunicator().stringToProxy("OnlineBookIdentity");
		OnlineBookPrx prxy = OnlineBookPrxHelper.checkedCast(base);
		for (Message m : msg) {
			prxy.bookTick(m);
		}
		return new boolean[msg.length];
	}
}
