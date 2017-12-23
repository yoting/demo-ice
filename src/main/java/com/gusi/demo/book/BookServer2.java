package com.gusi.demo.book;

import IceBox.Server;

/**
 * 启动IceRegistry<br>
 * 通过Server读取iceRegistryConfig.properties注册服务到registry上
 * 
 * @date Created by yydeng on 2017/12/11.
 */
public class BookServer2 {
	public static void main(String[] args) {
		Server server = new Server();
		server.main("BookServer", new String[] {}, "iceBoxRegistryConfig.properties");
	}
}
