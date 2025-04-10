package org.example;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;

public class ClassEncryptor {
    public static void main(String[] args) throws Exception {
        String key = "R0x7e"; // 加密密钥（需与 TestClassLoader 一致）
        String inputClassPath = "F:\\project\\JavaCodeAudit\\JavaSecurity\\JavaSecurity\\LearnCloassLodaer\\target\\classes\\org\\example\\HelloWorld.class";
        String outputPath = "HelloWorld.enc";

        // 读取原始类文件
        byte[] classBytes = Files.readAllBytes(Paths.get(inputClassPath));

        // 加密
        byte[] encrypted = encrypt(classBytes, key);

        // 保存加密文件
        Files.write(Paths.get(outputPath), encrypted);
    }

    //加密函数
    private static byte[] encrypt(byte[] data, String key) throws Exception {
        // 使用 SHA-128 生成 16 字节的密钥（128 位）
        byte[] keyBytes = MessageDigest.getInstance("SHA-1") // SHA-1 生成 20 字节，截取前 16 字节
                .digest(key.getBytes());
        keyBytes = Arrays.copyOf(keyBytes, 16); // 强制截取为 16 字节（128 位）

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(data);
    }
}