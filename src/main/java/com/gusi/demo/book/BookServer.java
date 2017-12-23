package com.gusi.demo.book;

import IceBox.Server;

import java.util.concurrent.TimeUnit;

/**
 * Created by yydeng on 2017/12/11.
 */
public class BookServer {
	public static void main(String[] args) {
		Server server = new Server();

		server.main("BookServer", new String[] {}, "iceBoxConfig.properties");
	}
}
