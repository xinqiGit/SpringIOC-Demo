package com.xxx.spring;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ClassPathXmlApplicationContext implements BeanFactory{
	//Map,存放bean的名称和对象
	private Map<String,Object> beans = new HashMap<>();
	
	public ClassPathXmlApplicationContext(String xml) throws Exception{
		//使用jdom读取xml
		SAXBuilder sb=new SAXBuilder();
		//将src下资源作为inputStream流读入，构造文档对象
		Document doc=sb.build(this.getClass().getClassLoader().getResourceAsStream(xml));
		Element root=doc.getRootElement();//获取根元素
		//将所有bean标签的元素放到List中
		List list = root.getChildren("bean");
		for(int i=0;i<list.size();i++){
			//遍历每个bean标签元素，将id和class的属性值读取出来。
			Element child=(Element) list.get(i);
			String id = child.getAttributeValue("id");
			String clazz = child.getAttributeValue("class");
			//利用反射，根据class属性的值加载该类，并实例化一个对象
			Object o = Class.forName(clazz).newInstance();
			//将bean的名称和实例化对象存到容器中，以便之后使用
			beans.put(id, o);	
			
			System.out.println(id);
			System.out.println(clazz);
			
			//遍历每个bean的property属性，自动注入对应的bean
			for(Element propertyElement:(List<Element>)child.getChildren("property")){
				//获取属性bean的name和bean名称。
				String name=propertyElement.getAttributeValue("name");
				String bean=propertyElement.getAttributeValue("bean");
				Object beanObject=beans.get(bean);
				
				
				//构造set()方法名称
				String methodName="set"+bean.substring(0, 1).toUpperCase()+bean.substring(1);
				System.out.println("methodName:"+methodName);
				//利用反射，调用set方法，注入propertybean,第二个参数是 参数类型:（注入bean对象的类实现的第一个接口）
				Method m = o.getClass().getMethod(methodName, beanObject.getClass().getInterfaces()[0]);
				m.invoke(o, beanObject);
						
			}
		}
		
	}
	
	@Override
	public Object getBean(String id) {
		// TODO Auto-generated method stub
		return beans.get(id);
	}

}
