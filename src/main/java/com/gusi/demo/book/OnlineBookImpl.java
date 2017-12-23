package com.gusi.demo.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import IceBox.Service;

import com.ice.demo.book.Message;
import com.ice.demo.book._OnlineBookDisp;

/**
 * Created by yydeng on 2017/12/8.
 */
public class OnlineBookImpl extends _OnlineBookDisp implements Service {
	Logger logger = LoggerFactory.getLogger(OnlineBookImpl.class);
	private ObjectAdapter _adapter;

	@Override
	public void start(String s, Communicator communicator, String[] strings) {
		_adapter = communicator.createObjectAdapter(s);

		Ice.Object object = this;
		_adapter.add(object, communicator.stringToIdentity(s));
		_adapter.activate();

		logger.info("start onlinebook server completed!");
	}

	@Override
	public void stop() {
		_adapter.destroy();
	}

	@Override
	public Message bookTick(Message msg, Current __current) {
		System.out.println("receive: online book server!" + msg.name);
		return msg;
	}
}
