package com.gusi.demo.clock;

import Ice.Current;

import com.ice.demo.clock._ClockServiceDisp;

/**
 * 闹钟服务
 *
 * @author yydeng
 * @create 2017-12-13 10:31
 */
public class ClockServiceImpl extends _ClockServiceDisp {
	@Override
	public void tick(String time, Current __current) {
		Object endpoints = __current.adapter.getEndpoints()[0]._toString();
		System.out.println(endpoints + " now time is :" + time);
	}
}
