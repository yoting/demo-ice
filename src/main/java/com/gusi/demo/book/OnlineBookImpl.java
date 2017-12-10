package com.gusi.demo.book;

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
    private ObjectAdapter _adapter;

    @Override
    public Message bookTick(Message msg, Current __current) {
        System.out.println("receive:" + msg.name);
        return msg;
    }

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
        _adapter.destroy();
    }
}
