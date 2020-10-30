package com.sirius.robots.comm.util;

import com.alibaba.fastjson.JSON;
import com.sirius.robots.comm.bo.LoginInfoTokenBO;
import com.sirius.robots.comm.enums.ErrorCodeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author liang_shi
 * @date 2018/11/15 16:24
 * @description
 */
@Slf4j
public class TokenEncodeUtil {

    public static final String ENCODING = "UTF-8";

    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj4WTdBrJs+srY9TxwdXCX7/gQVyJ+a2CQGbZqBih0WwDG3cqauSA1BQYit89s0Ep+mq4RQPRJp7epnR0CeeDfyUuu9095lOAGNnH4xF1amoxe6sK7GrMT/Ani3NBNKlRSQlnYaOkIdKmlCLIobNzV1yh8YiVdAI+1Ha9E/JhfJtYYWI8ynaDGTxbN+xpwM0xwNa3Pqwu/8T7OxuDNlXSV1VTg6uYuHZfnfhQPo9YN0wBbnqkmhIuBT5n4yjvCBWId9SrkWdwdM78hoOP3LayVAOYEOxV9YFSSHfmmdr9A21YL4DCTvcB2eFrTRH72nas3uy0kq9gWm0obSl40BFWhwIDAQAB";
    private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCPhZN0Gsmz6ytj1PHB1cJfv+BBXIn5rYJAZtmoGKHRbAMbdypq5IDUFBiK3z2zQSn6arhFA9Emnt6mdHQJ54N/JS673T3mU4AY2cfjEXVqajF7qwrsasxP8CeLc0E0qVFJCWdho6Qh0qaUIsihs3NXXKHxiJV0Aj7Udr0T8mF8m1hhYjzKdoMZPFs37GnAzTHA1rc+rC7/xPs7G4M2VdJXVVODq5i4dl+d+FA+j1g3TAFueqSaEi4FPmfjKO8IFYh31KuRZ3B0zvyGg4/ctrJUA5gQ7FX1gVJId+aZ2v0DbVgvgMJO9wHZ4WtNEfvadqze7LSSr2BabShtKXjQEVaHAgMBAAECggEAfD5ofdRK1IQ7FbtcZi7Ei9sxKaQQlaJM5a+jsM9SFldpATfR8qNJm06iSGYlpa49xtjoGGbLgqF5oaOiaIiQ31qy8FMAqQmUFO9DCSEuAidYGvThCfUSVglSUng99Ha3NYD8jBv7lIQlTIwoH0OYEALS9qMErl7bQVZhLIlhN1p/3nkaTvj57e5USLFEnhFv702Dlwmbd4YAArwNkxU5eIh8fWdMF9qnMgzxgH6/Gq8XUuFAAbFVUGVNdr1qKyeWcma+oKt8f4sgsjmwfHayFNH545Op0nJClJNq9Jn5KNWPfH12ue/Dd93933U1w2VD+VSecIKe9aSvylDfLRN5sQKBgQDD5JVeRlvkl8kkfk8PMjPeugB1BLvqZkP9s08M7xFZJNKaQxDdeMi93IScwWl/kLMBsGT67KiDj8UJvl8Q6Dd1/FbS8DfCKVCFL5ItUcSGNr3B9CkuV3JdDp78pV1KZMt3VRRgCEPck6TR28H9UotksW43lrqVxFY+OzG2jo/+ZQKBgQC7jzurWvt0CC08fLW5IzTPAw7iQ/yMUmI1q0PUIPj9SoHVqYZpDok9dBMc+Hesg6BmK+k7GFa+7YZx6pXKs3cmFXRUZS5uICRDtoU45Me+z5kaVxJ01MYm7fWvs4vG8siAhlQAxwSYVI1X6J8W0DXT5wcO3s1UTM/Ehaif6JnsewKBgGJ1hc34tz9SOzsocTxGE2QjQQ+P7ZOHCy187oKltqaDp4b5poRqrRsgt1bCr+/6Hr7+lgK46IoXvQWEteudjCK1Tj5/lsb0VRBcNLCqpIiBAyd1PS2ZrlWvf6GkzBsURmAYV0FdZh575x/DwOE+l+lodOljzSxxnpdw0S/cv+sxAoGBALFRGnJHk44A5PNYW1zqQdSr6XhUGgB3Kk3e2kND4OFlfRWILYBIs8qbYC51YH6B+jRdsyOhQPFEEWTFCFQYEr64bcAT4C9Qx7gUaCJ/d6+XCAF07Afz2FmZgjpXnf+6K4AGfog1nsx8sxu22iBxO1JYCb4vOSNHcu0yB1cWSd7dAoGAe0y1hKCAGAORoa9Wkb/HVpZWFnfjFtLWBs+ShU6rY57a18eF5KhhCYMCcalUh2/+J/upJp9vDz/vDZK/IdT2ou1Bs/gyHWOa4D2b5eKYDGeFkJpsj5IkdXL/+dQEqxq/LPkjc6DdRPvWKsIoMiu9CF6c2UdiuaqbKVJuPY+jMQE=";


    public static String encodeToken(LoginInfoTokenBO loginInfoTokenBO) throws Exception {
        byte[] publicKeyBase64Data = publicKey.getBytes(Charset.forName(ENCODING));
        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyBase64Data);
        PublicKey pk = RSAUtils.restorePublicKey(publicKeyBytes);
        // 公钥加密
        byte[] objectData = RSAUtils.RSAEncode(pk, JSON.toJSON(loginInfoTokenBO).toString().getBytes(ENCODING));
        // Base64编码
        return Base64.encodeBase64String(objectData);
    }


    public static LoginInfoTokenBO decodeToken(String token) {
        LoginInfoTokenBO loginInfoTokenBO = null;
        // Base64解码
        byte[] objectData = Base64.decodeBase64(token);
        // 私钥获取
        byte[] privateKeyBase64Data = privateKey.getBytes(Charset.forName(ENCODING));
        byte[] privateKeyBytes = Base64.decodeBase64(privateKeyBase64Data);
        PrivateKey privateKey = RSAUtils.restorePrivateKey(privateKeyBytes);
        // 私钥解密
        String objectDataStr = null;
        try {
            objectDataStr = RSAUtils.RSADecode(privateKey, objectData);
        } catch (Exception e) {
            log.error("token解析出错,token:{}",token);
            throw new RobotsServiceException(ErrorCodeEnum.AUTH0001);
        }
        // 获取信息
        loginInfoTokenBO = JSON.parseObject(objectDataStr, LoginInfoTokenBO.class);
        return loginInfoTokenBO;
    }
}
