package com.sirius.robots.web.controller;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/21
 */
@Slf4j
public class OCRDemo {

    public static void main(String[] args) throws Exception {
      /*  File tmpFolder = LoadLibs.extractTessResources("win32-x86-64");

        System.setProperty("java.library.path", tmpFolder.getPath());*/
        ITesseract instance = new Tesseract();
        Path dataDirectory = Paths.get(ClassLoader.getSystemResource("testdata").toURI());

        instance.setDatapath(dataDirectory.toString());

        //如果未将tessdata放在根目录下需要指定绝对路径
       // instance.setDatapath("D:\\xinyan\\my\\robots\\robots-web\\src\\main\\resources");

        //如果需要识别英文之外的语种，需要指定识别语种，并且需要将对应的语言包放进项目中
        instance.setLanguage("chi_sim");
        instance.setOcrEngineMode(1);
        // 指定识别图片
        File imgDir = new File("D://test3.png");
        long startTime = System.currentTimeMillis();
        String ocrResult = instance.doOCR(imgDir);

        // 输出识别结果
        System.out.println("OCR Result: \n" + ocrResult + "\n 耗时：" + (System.currentTimeMillis() - startTime) + "ms");
    }
}