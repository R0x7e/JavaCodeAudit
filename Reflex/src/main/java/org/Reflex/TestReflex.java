package org.Reflex;

import org.Reflex.entity.User;
import org.testng.annotations.Test;

import java.lang.reflect.*;
import java.util.Arrays;

public class TestReflex {
    @Test
    public void testStart() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        User user = new User();
        //第一种方法
        Class<?> aClass = Class.forName("org.Reflex.entity.User");
        //第二种方法
        Class<? extends User> aClass1 = user.getClass();
        //第三种方法
        Class<User> userClass = User.class;
        //创建实例化对象
        User user1 = (User) aClass.newInstance();
        user1.setUser_phone("aaaaa");
        System.out.println(user1.getUser_phone());
    }
    @Test
    public void testFields() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName("org.Reflex.entity.User");
        //获取所有的属性
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field f:declaredFields){
            System.out.println("属性名:"+f.getName()); //依次打印属性名
            System.out.println("修饰符:"+ Modifier.toString(f.getModifiers()));   //获取权限修饰符
            System.out.println("Type:"+f.getType().getSimpleName());
        }
        //获取指定属性
        Field userPhone = aClass.getDeclaredField("user_phone");
        userPhone.setAccessible(true);
        //实例化class类
        Object o = aClass.newInstance();
        //为指定实例化对象的指定属性赋值
        userPhone.set(o,"12356456");
        //通过get方法获取属性的值
        System.out.println(userPhone.get(o));
    }
    @Test
    public void testMethods() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> aClass = Class.forName("org.Reflex.entity.User");
        //查看所有的方法
        Method[] methods = aClass.getDeclaredMethods();
        for (Method m:methods){
            System.out.print(Modifier.toString(m.getModifiers())+" ");   //获取方法的权限
            System.out.print(m.getName()+"  ");   //获取方法的名字
            System.out.print(Arrays.toString(m.getParameterTypes())+" ");   //获取方法的参数类型
            System.out.print(m.getDeclaringClass());
            System.out.println();
        }
        //获取指定方法
        Method pring = aClass.getDeclaredMethod("Print",String.class);
        //实例化User类
        Object o = aClass.newInstance();
        //调用实例化User类的Print方法
        Object invoke = pring.invoke(o, "测试指定方法调用");
        //输出方法调用的返回值
        System.out.println(invoke.toString());
        //获取指定的私有化方法
        Method printAll = aClass.getDeclaredMethod("PrintAll");
        //禁用安全检查
        printAll.setAccessible(true);
        //调用方法
        Object invoke1 = printAll.invoke(o);

        //调用静态方法
        Method pringB = aClass.getDeclaredMethod("PringB");
        //因为为静态方法，所以不需要传入实例化的类，直接调用就可以
        Object invoke2 = pringB.invoke(null);
    }
    @Test
    public void testConstructor() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<?> aClass = Class.forName("org.Reflex.entity.User");
        //获取当前类的所有构造方法
        Constructor<?>[] constructors = aClass.getConstructors();
       for (Constructor c:constructors){
           //打印构造方法的权限
           System.out.print(Modifier.toString(c.getModifiers())+" ");
           //打印构造方法的名字
           System.out.print(c.getName());
           //打印当前构造方法所有的
           System.out.print(Arrays.toString(c.getParameterTypes()));
           //打印当前构造方法的引用
           System.out.println(c.getDeclaringClass());
       }
       //获取类的无参构造方法
        Constructor<?> constructor = aClass.getConstructor();
       //调用类的无参构造方法
        Object o = constructor.newInstance();
        //调用类的有参构造方法
        Constructor<?> constructor1 = aClass.getConstructor(String.class, String.class);
        constructor1.newInstance("pwjcw","123456");
    }
    @Test
    public void testInternal() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        Class<?> aClass1 = Class.forName("org.Reflex.entity.User");
//        Class<?> aClass = Class.forName("org.Reflex.entity.User$Userinternal");
//        Constructor<?> constructor = aClass1.getConstructor();
//        Object o = constructor.newInstance();
//
//        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(aClass1);
//        Object o1 = declaredConstructor.newInstance(o);
//        Method printC = aClass.getDeclaredMethod("PrintC");
//        printC.invoke(o1);

    }
}
