package com.gusi.demo.utils.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 
 */
public class ReferenceBeanDefinitionParser implements BeanDefinitionParser {
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {

		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClass(ReferenceBean.class);

		String id = element.getAttribute("id");
		beanDefinition.getPropertyValues().addPropertyValue("id", id);

		String identity = element.getAttribute("identity");
		beanDefinition.getPropertyValues().addPropertyValue("identity", identity);

		String type = element.getAttribute("type");
		beanDefinition.getPropertyValues().addPropertyValue("type", type);

		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
		beanDefinition.setLazyInit(false);
		return beanDefinition;
	}
}
