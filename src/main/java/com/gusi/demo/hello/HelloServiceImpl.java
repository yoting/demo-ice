package com.gusi.demo.hello;

import Ice.Current;
import com.ice.demo.hello._HelloServiceDisp;

/**
 * Created by yydeng on 2017/12/8.
 */
public class HelloServiceImpl extends _HelloServiceDisp {
	@Override
	public void say(String s, Current __current) {
		System.out.println("hello " + s);
	}

	@Override
	public int calculate(int a, int b, Current __current) {
		return a + b;
	}
}
