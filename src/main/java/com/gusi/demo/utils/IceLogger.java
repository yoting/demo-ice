package com.gusi.demo.utils;

import Ice.Logger;
import org.slf4j.LoggerFactory;

/**
 * 重定向ice日志
 *
 * @author yydeng
 * @create 2017-12-21 15:19
 */
public class IceLogger implements Ice.Logger {
	private org.slf4j.Logger logger = null;

	public IceLogger(String loggerName) {
		logger = LoggerFactory.getLogger(loggerName);
	}

	@Override
	public void print(String s) {
		logger.info(s);
	}

	@Override
	public void trace(String s, String s1) {
		logger.trace(s, s1);
	}

	@Override
	public void warning(String s) {
		logger.warn(s);
	}

	@Override
	public void error(String s) {
		logger.error(s);
	}

	@Override
	public Logger cloneWithPrefix(String s) {
		return new IceLogger(s);
	}
}
