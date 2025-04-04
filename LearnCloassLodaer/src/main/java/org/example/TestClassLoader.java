package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 自定义类加载器 - 从 AES 加密文件加载类
 * 功能：支持解密加密的 .class 文件并加载类
 * 使用方式：
 * 1. 加密类文件：将编译后的 .class 文件用 AES 加密，保存为 .enc 文件
 * 2. 将加密文件放在项目根目录的 encrypted 文件夹下
 * 3. 通过 TestClassLoader 加载类
 */
public class TestClassLoader extends ClassLoader {
    private final String key; // AES 加密密钥
    private final String basePath; // 加密文件存放的基础路径

    public TestClassLoader(String key, String basePath) {
        this.key = key;
        this.basePath = basePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // 1. 从加密文件读取字节码
            byte[] encryptedBytes = loadEncryptedClass(name);

            // 2. 解密字节码
            byte[] decryptedBytes = decrypt(encryptedBytes);

            // 3. 定义类
            return defineClass(name, decryptedBytes, 0, decryptedBytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException("Failed to load class: " + name, e);
        }
    }

    // 读取加密的类文件
    private byte[] loadEncryptedClass(String className) throws Exception {
        String path = className.replace('.', File.separatorChar) + ".enc";
        File file = new File(basePath + File.separator + path);

        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        }
    }

    // AES 解密
    private byte[] decrypt(byte[] encryptedData) throws Exception {
        // 生成合法的 AES 密钥（128/256位）
        byte[] keyBytes = MessageDigest.getInstance("SHA-1")
                .digest(key.getBytes(StandardCharsets.UTF_8));

        keyBytes = Arrays.copyOf(keyBytes, 16); // 强制截取为 16 字节（128 位）
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(encryptedData);
    }
}