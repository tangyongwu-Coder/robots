package com.sirius.robots.web.controller;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/21
 */
public class QRCodeTools {

    public static void main(String[] args) throws Exception{
        MultiFormatReader formatReader = new MultiFormatReader();
        // 指定识别图片
        File imgDir = new File("D://test.png");
        BufferedImage image = ImageIO.read(imgDir);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        //定义二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 指定编码格式
        Result result = formatReader.decode(binaryBitmap, hints);
        System.out.println("二维码解析结果：" + result.toString());
        System.out.println("二维码的格式：" + result.getBarcodeFormat());
        System.out.println("二维码的文本内容：" + result.getText());
        System.out.println("结果:"+result.getText());
    }
    /**
     * 本地图片转换成base64字符串
     * @param imgFile	图片对象
     * @return
     * @author huangshikai
     */
    public static String ImageToBase64ByLocal(File imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;

        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str = encoder.encode(data);
        base64Str.replaceAll("(\r\n|\r|\n|\n\r)", "");//替换base64后的字符串中的回车换行
        return base64Str;// 返回Base64编码过的字节数组字符串
    }

    /**
     * 本地图片转换成base64字符串
     * @param imgFilePath 本地图片存放路径
     * @return
     * @author huangshikai
     */
    public static String ImageToBase64ByLocal(String imgFilePath){
        return ImageToBase64ByLocal(new File(imgFilePath));
    }
    /**
     * 解析二维码解析,此方法是解析Base64格式二维码图片
     * baseStr:base64字符串,data:image/png;base64开头的
     */
    public static String deEncodeByBase64(String baseStr) {
        String content = null;
        BufferedImage image;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b=null;
        try {
            int i = baseStr.indexOf("data:image/png;base64,");
            baseStr = baseStr.substring(i+"data:image/png;base64,".length());//去掉base64图片的data:image/png;base64,部分才能转换为byte[]

            b = decoder.decodeBuffer(baseStr);//baseStr转byte[]
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(b);//byte[] 转BufferedImage
            image = ImageIO.read(byteArrayInputStream);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
            System.out.println("图片中内容：  ");
            System.out.println("content： " + result.getText());
            content = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }
    /**
     * 解析二维码,此方法解析一个路径的二维码图片
     * path:图片路径
     */
    public static String deEncodeByPath(String path) {
        String content = null;
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
            System.out.println("图片中内容：  ");
            System.out.println("content： " + result.getText());
            content = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }
}