package com.gusi.demo.utils.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 
 */
public class IceNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("reference", new ReferenceBeanDefinitionParser());
	}
}
