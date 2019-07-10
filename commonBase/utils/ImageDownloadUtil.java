package com.yuchen.catalog.common.utils;

import com.alibaba.dubbo.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloadUtil {
    static Logger logger = LoggerFactory.getLogger(ImageDownloadUtil.class);
    public static void  downloadImage(String imgUrl, String rootPath, String anccPrefix) throws Exception{
        //图片的名称带文件夹的
        String imageNameWithPath = imgUrl.replace(anccPrefix+"/", "");
        String imgPath =rootPath + imageNameWithPath.substring(0,imageNameWithPath.lastIndexOf("/")+1);
        String imageName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
        //如果文件夹不存在，创建一个
        File file = new File(imgPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //输入流，用来读取图片
        InputStream ins = null;
        HttpURLConnection httpURL = null;
        //输出流
        OutputStream out = new FileOutputStream(imgPath + imageName);
        try{
            URL url = new URL(imgUrl);
            //打开一个网络连接
            httpURL = (HttpURLConnection)url.openConnection();
            //设置网络连接超时时间
            httpURL.setConnectTimeout(3000);
            //设置应用程序要从网络连接读取数据
            httpURL.setDoInput(true);
            //设置请求方式
            httpURL.setRequestMethod("GET");
            //获取请求返回码
            int responseCode = httpURL.getResponseCode();
            if(responseCode == 200){
                //如果响应为“200”，表示成功响应，则返回一个输入流
                ins = httpURL.getInputStream();

                //输出流到response中
                byte[] data = new byte[1024];
                int len = 0;
                while((len = ins.read(data)) > 0){
                    out.write(data, 0, len);
                }
            }
        }catch(Exception e){
            logger.error("下载附件图片出错！",e);
            throw e;
        }finally{
            if(ins != null){
                ins.close();
            }
            if(out != null){
                out.close();
            }
        }
    }

    public static void main(String[] args) throws Exception{
//        downloadImage("http://www.anccnet.com/userfile/2015825/40475195.jpg");
    }
}
