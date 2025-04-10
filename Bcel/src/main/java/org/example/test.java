package org.example;


import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.Constant;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.sun.org.apache.bcel.internal.generic.ClassGen;
import com.sun.org.apache.bcel.internal.util.ClassLoader;
import org.junit.Test;

import java.io.IOException;

public class test {
    @Test
    public void test1() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JavaClass javaClass = Repository.lookupClass(Calc.class);
        System.out.println(javaClass);
        String encode = Utility.encode(javaClass.getBytes(), true);
        System.out.println(encode);
//        new ClassLoader().loadClass("$$BCEL$$" + encode);
        Object o = new ClassLoader().loadClass("$$BCEL$$" + encode).newInstance();
    }
}
