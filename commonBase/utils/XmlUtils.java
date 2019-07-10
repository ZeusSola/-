package com.yuchen.catalog.common.utils;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.awt.print.Book;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class XmlUtils {
    public static String getValueByKeyJDom(String key, String xmlToParse, String rootPath, String anccImgPrefix) {
        String imageUrls = "";
        try {
            Reader xmlStreamReader = null;
            xmlToParse = xmlToParse.substring(xmlToParse.indexOf("<ns2:TSDBasicProductInformationModuleType>"),xmlToParse.indexOf("</ns2:TSDBasicProductInformationModuleType>")+43);
            xmlToParse = xmlToParse.replace("ns2:", "");
            InputStream is = new ByteArrayInputStream(xmlToParse.getBytes());
            xmlStreamReader = new InputStreamReader(is);

            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(xmlStreamReader);
            Element root = doc.getRootElement();
            List<Element> tables = root.getChildren();
            for(Element element : tables){
                if(element.getName().equals("imageLink")){
                    String imgUrl = element.getValue();
                    imageUrls += imgUrl.replace(anccImgPrefix, "") + ",";
                    //下载图片到本地
                    ImageDownloadUtil.downloadImage(imgUrl, rootPath, anccImgPrefix);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(imageUrls.endsWith(",")){
            imageUrls = imageUrls.substring(0, imageUrls.length() - 1);
        }
        return imageUrls;
    }

}
