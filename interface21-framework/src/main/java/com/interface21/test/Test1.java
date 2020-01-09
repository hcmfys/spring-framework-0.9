package com.interface21.test;

import com.interface21.beans.factory.xml.XmlBeanFactory;
import org.apache.log4j.Logger;


import java.io.FileInputStream;
import java.io.InputStream;


public class Test1 {


    public static void main(String[] args) throws Exception {

        Logger logger = Logger.getLogger(Test1.class);
        InputStream is = new FileInputStream(
                "E:\\mj-project\\interface21\\interface21-framework\\src\\test\\java\\com\\interface21\\beans\\factory\\xml\\reftypes.xml");
        XmlBeanFactory xbf = new XmlBeanFactory(is);
        logger.info("def counts={} " + xbf.getBeanDefinitionCount() );
        TestBean jen = (TestBean) xbf.getBean("jenny");
        TestBean dave = (TestBean) xbf.getBean("david");
        TestBean jenks = (TestBean) xbf.getBean("jenks");

        System.out.println( jen);
        System.out.println( dave);
        System.out.println( jenks);
    }


}
