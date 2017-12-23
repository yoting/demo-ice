package com.gusi.demo.book;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import IceBox.Service;
import com.ice.demo.book.Message;
import com.ice.demo.book.OnlineBookPrx;
import com.ice.demo.book.OnlineBookPrxHelper;
import com.ice.demo.book._OfflineBookDisp;

/**
 * Created by yydeng on 2017/12/11.
 */
public class OfflineBookImpl extends _OfflineBookDisp implements Service {
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
		System.out.println("receive:offline book server!");
		Ice.ObjectPrx base = _adapter.getCommunicator().stringToProxy("OnlineBookServer@OnlineBookAdapter");
		OnlineBookPrx prxy = OnlineBookPrxHelper.checkedCast(base);
		for (Message m : msg) {
			prxy.bookTick(m);
		}
		return new boolean[msg.length];
	}
}
