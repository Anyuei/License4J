package com.license4j.license.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密解密
 *
 * @author ruoyi
 **/
public class RsaUtils {
    // Rsa 私钥 ：秘钥长度位数 、格式
    public static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0QQ/9brgpjXcfi9djUfzVzTgZPluCJXATSNMNtumI6VZHm6k8fappUi10AHOj4aFTABKfnJLzMo8IfdTSTKx0llhTN+W3tsgEULaptCr6fh68QuXSccbe3L3z2TgPvXxK+/b7y9xGvl+X3UlMQJDmB2iWpcLjm7hCOZXohWCDxnzSkokQ2ysSo4K9caCEmzXWUMIaIlCpOxRiETe3xze+ZCrzfl6E6/WJknfAOXJG8AlhfPjzElgfOLiDldCuJW6tCKhuLhXaeStOdxjHv/kmzGMYulbvwW46/GTEfZivuv3ikGJ4KHRRXcFrnXuYvmXZc8OUQWPZvyCAH9A+JmB7AgMBAAECggEABZusmq7yLeDH3wRjzCRF4IUFBMwK3s65YPA4GMojE+5mj83JAyfJwIMdBOnqryOa+FEdQzgE3mopRxNbJrvZ9bqjvtsTW5blPDUiqb8AQ1DfNe5C5jxB+rcVddvBQcQcdNzH/2eFT/ImWTw6RpQPrgh2+ZDosloq7myKPHTB3velhSN4FkOrpqNiMAmZECgfUo+kV2F1IyZh8zoaPQDa0qTcrX4ftOZQr24/s28QHhVUiR/cuAjBVwoeoM2oxcqY+eUGJ4ckEneI9zKW7i2MU+3mrOSyHJUi6+lfr3CAcwnviY930DnEycDTU3fUVfhznOQV48WU0mR4NZrCSPJIaQKBgQDCKsmz+7P+hdJL0zQ40MuIBuEmEeRu1/jg5fe7ftxOfSvPjOOkpuWv+P5pzWN1w85ZPUtWWl6s7qe4v8x6mTDROpAouOtk3S8xsh0zAStERoK3JRgv/dcZAw+kYoG/4vwezJz9Z6vNkV2QASbeegpfoeN50Nezt+zgMwd8mdMHLwKBgQDtqAl2k1zrugL0cfz8AoJHZlqHcFlwPgvzQFSPy4zlDIcpw2l8F8aLJv9ifBf1FhiyN/sqVLgEGyo2xCCFvCTbKCTgD/9R/vhwCiOkpL9UpL9dAWXC2GWv3FxKw6O5N+sebVWHgQijy/oH8mqV3PDX+JMT62eayqbcOiQEVIdodQKBgQCl/SI15WW4nvpqrAGm2IhGn2r2urEIOUWgLbic5xS7XLKJWi554dvVlg0YsebnFB9hls7C4bqyJ8vmWsEZxcA//cqjpYJG1fe0ikoAAm3UjlrM6r9Et8KctUs4AqZdPlS+l7lVwLzUngLYSsPJyZb4rlLjHrBoCUJNYBJ0aKRJLQKBgQCrqkRjpWBqWauGvMu7kWhfgLyFto60N+1T4TTzxVlZneoILcIFQ/tDSoEQt4hqnXJrUWqYkid1t9WPR64iS9vnfiDcxFFt/hmgfIJwjBw0XOhpmvfRfZQ+KpNz4ctin9Sy3tD4FymRf2Z01RNOexBxQHmk2ta0hgtvaoPXqONCfQKBgBdphNQvARIX/WhVWb0lwfORnRD0cNSL2DUlLEo4BsXp4i/O2lwzah2jO2SgYMcUY3h5ykWW5P26rL4S7fIBmZU6vPoQ0dD3skiAlAL3q+tkz5aDi+YfcQoT3eAxWXno+Tbyn3cR3PZW1CDiMhRR1dtk/WDkZ4+6fsNd9zJb4GCp";
    //公钥
    public static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtEEP/W64KY13H4vXY1H81c04GT5bgiVwE0jTDbbpiOlWR5upPH2qaVItdABzo+GhUwASn5yS8zKPCH3U0kysdJZYUzflt7bIBFC2qbQq+n4evELl0nHG3ty989k4D718Svv2+8vcRr5fl91JTECQ5gdolqXC45u4QjmV6IVgg8Z80pKJENsrEqOCvXGghJs11lDCGiJQqTsUYhE3t8c3vmQq835ehOv1iZJ3wDlyRvAJYXz48xJYHzi4g5XQriVurQiobi4V2nkrTncYx7/5JsxjGLpW78FuOvxkxH2Yr7r94pBieCh0UV3Ba517mL5l2XPDlEFj2b8ggB/QPiZgewIDAQAB";

    /**
     * 私钥解密
     *
     * @param text 待解密的文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String text) throws Exception {
        return decryptByPrivateKey(privateKey, text);
    }

    /**
     * 公钥解密
     *
     * @param publicKeyString 公钥
     * @param text            待解密的信息
     * @return 解密后的文本
     */
    public static String decryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 私钥加密
     *
     * @param privateKeyString 私钥
     * @param text             待加密的信息
     * @return 加密后的文本
     */
    public static String encryptByPrivateKey(String privateKeyString, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 私钥解密
     *
     * @param privateKeyString 私钥
     * @param text             待解密文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String privateKeyString, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 公钥加密
     *
     * @param publicKeyString 公钥
     * @param text            待加密的文本
     * @return 加密后的文本
     */
    public static String encryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 构建RSA密钥对
     *
     * @return 生成后的公私钥信息
     */
    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }

    /**
     * RSA密钥对对象
     */
    public static class RsaKeyPair {
        private final String publicKey;
        private final String privateKey;

        public RsaKeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }
    }


    /**
     * 只需调用一次 生成/打印新的公钥私钥  并测试是否可用
     * 控制台打印结果，解密成功 则将打印的公钥私钥重新赋值给工具类的 privateKey 、 publicKey
     *
     * @throws NoSuchAlgorithmException
     */
    public static void printNewPubKeypriKey() {
        //调用 RsaUtils.generateKeyPair() 生成RSA公钥秘钥
        String tmpPriKey = "";//私钥
        String tmpPubKey = "";//公钥
        try {
            RsaKeyPair rsaKeyPair = RsaUtils.generateKeyPair();
            tmpPriKey = rsaKeyPair.getPrivateKey();
            tmpPubKey = rsaKeyPair.getPublicKey();
            System.out.println("私钥：" + tmpPriKey);
            System.out.println("公钥：" + tmpPubKey);
        } catch (NoSuchAlgorithmException exception) {
            System.out.println("生成秘钥公钥失败");
        }
        //公钥加密、私钥解密
        try {
            String txt = "123456789,13000000001,oUpF8uMuAJO_M2pxb1Q9zNjWeS6oob1Q9zNjWeS6oQ9zNjW,1672914158,1672914158,啊";//注意需要加密的原文长度不要太长 过长的字符串会导致加解密失败
            System.out.println("加密前原文：" + txt);//加密后文本
            String rsaText = RsaUtils.encryptByPublicKey(tmpPubKey, txt);//公钥加密 ！！！
            System.out.println("密文：" + rsaText);//加密后文本
            System.out.println("解密后原文：" + RsaUtils.decryptByPrivateKey(tmpPriKey, rsaText));//私钥解密 ！！！
        } catch (BadPaddingException e) {
            System.out.println(e.getStackTrace());
            System.out.println("加解密失败");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用固定的 privateKey 、 publicKey 进行加解密测试
     * 注意 需要加密的原文长度不要太长 过长的字符串会导致加解密失败
     */

    public static void main(String[] args) {
        //公钥加密、私钥解密
        try {
            String txt = "123456789,13000000001,oUpF8uMuAJO_M2pxb1Q9zNjWeS6oob1Q9zNjWeS6oQ9zNjW,1672914158,1672914158,啊,123456789,13000000001,oUpF8uMuAJO_M2pxb1Q9zNjWeS6oob1Q9zNjWeS6oQ9zNjW,1672914158,1672914158,啊123456789,13000000001,oUpF8uMuAJO_M2pxb1Q9zNjWeS6oob1Q9zNjWeS6oQ9zNjW,1672914158,1672914158,啊,123456789,13000000001,oUpF8uMuAJO_M2pxb1Q9zNjWeS6oob1Q9zNjWeS6oQ9zNjW,1672914158,1672914158,啊";//注意需要加密的原文长度不要太长 过长的字符串会导致加解密失败
            System.out.println("加密前原文：" + txt);//加密后文本
            String rsaText = RsaUtils.encryptByPublicKey(RsaUtils.publicKey, txt);//RsaUtils.publicKey 公钥加密 ！！！
            System.out.println("密文：" + rsaText);//加密后文本
            System.out.println("解密后原文：" + RsaUtils.decryptByPrivateKey(RsaUtils.privateKey, rsaText));//RsaUtils.privateKey 私钥解密 ！！！
        } catch (BadPaddingException e) {
            System.out.println(e.getStackTrace());
            System.out.println("加解密失败");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
