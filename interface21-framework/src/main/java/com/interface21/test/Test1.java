package com.interface21.test;

import com.interface21.aop.framework.AopUtils;
import com.interface21.aop.framework.InvokerInterceptor;
import com.interface21.aop.framework.ProxyFactory;
import com.interface21.beans.BeanWrapperImpl;
import com.interface21.beans.MethodInvocationException;
import com.interface21.beans.PropertyValue;
import com.interface21.beans.factory.xml.XmlBeanFactory;
import com.interface21.context.ApplicationContext;
import com.interface21.context.support.FileSystemXmlApplicationContext;
import jdk.nashorn.internal.objects.NativeNumber;
import org.apache.log4j.Logger;
import org.junit.Test;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;


public class Test1 {


    static void testXml() throws Exception {
        Logger logger = Logger.getLogger(Test1.class);
        String path = "E:\\mj-project\\interface21\\interface21-framework\\src\\test\\java\\com\\interface21\\beans\\factory\\xml\\reftypes.xml";

        InputStream is = new FileInputStream(path);
        XmlBeanFactory xbf = new XmlBeanFactory(is);
        logger.info("def counts={} " + xbf.getBeanDefinitionCount());
        TestBean jen = (TestBean) xbf.getBean("jenny");
        TestBean dave = (TestBean) xbf.getBean("david");
        TestBean jenks = (TestBean) xbf.getBean("jenks");

        System.out.println(jen);
        System.out.println(dave);
        System.out.println(jenks);
    }

    public static void main(String[] args) throws Exception {

        // testCtx();
        //testWrap();
        //testBeanInfo();
       // testMethod();
        //testAop();
        testMixin();
    }


    static void testCtx() throws Exception {
        Logger logger = Logger.getLogger(Test1.class);
        String path = "E:\\mj-project\\interface21\\interface21-framework\\src\\test\\java\\com\\interface21\\beans\\factory\\xml\\reftypes.xml";

        ApplicationContext ctx = new FileSystemXmlApplicationContext(path);
        TestBean jen = (TestBean) ctx.getBean("jenny");
        System.out.println(jen);
        System.out.println("+==================");
        //ctx.refresh();
        //jen = (TestBean) ctx.getBean("jenny");
        //System.out.println(jen);
    }

    @Test
    static void testWrap() throws Exception {
        BeanWrapperImpl testBean = new BeanWrapperImpl(TestBean.class);
        PropertyValue pv = new PropertyValue("age", "23");
        testBean.setPropertyValue(pv);
        TestBean o = (TestBean) testBean.getWrappedInstance();
        System.out.println(o.getAge());

    }


    static void testBeanInfo() throws Exception {

        BeanInfo beanInfo = Introspector.getBeanInfo(TestBean.class);
        System.out.println(beanInfo);

    }

    static void testMethod() throws Exception {

        Method SET_AGE = TestBean.class.getMethod("setAge", new Class[] { int.class});
        Class<?> retClass= SET_AGE.getReturnType();

        Method GET_AGE = TestBean.class.getMethod("getAge", null);


    }


    static void testAop() throws Exception {

        Set set = AopUtils.findAllImplementedInterfaces(ApplicationContext.class);
        Iterator<?> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }



    public static  void testMixin() {
        TestBean tb = new TestBean();
        ProxyFactory pc = new ProxyFactory(new Class[]{Lockable.class});
        pc.addInterceptor(new LockMixin());
        //pc.addInterceptor(new InvokerInterceptor(tb));
        int newAge = 65;
        Lockable lockable = (Lockable) pc.getProxy();
        System.out.println(lockable.locked());
        lockable.lock();
        System.out.println(lockable.locked());
        lockable.unlock();
    }


    public  static void  testReplaceArgument() throws Throwable {
        TestBean tb = new TestBean();
        ProxyFactory pc = new ProxyFactory(new Class[]{ITestBean.class});
       // pc.addMethodPointcut(new StringSetterNullReplacementPointcut());
        pc.addInterceptor(new InvokerInterceptor(tb));

        ITestBean t = (ITestBean) pc.getProxy();
        int newAge = 5;
        t.setAge(newAge);
        System.out.println(t.getAge() == newAge);
        String newName = "greg";
        t.setName(newName);
        System.out.println(newName +  t.getName());

        t.setName(null);
        // Null replacement magic should work
        System.out.println(t.getName().equals(""));
    }



}
