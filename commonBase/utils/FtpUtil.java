package com.yuchen.catalog.common.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

    /**
     * Description: 向FTP服务器上传文件
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password,
                                     String ftpFilePath, String filename, InputStream input) {
        boolean debug = false;
        if(debug){
            return true;
        }else{
            boolean result = true;
            FTPClient ftp = new FTPClient();
            try {
                int reply;
                ftp.connect(host, port);// 连接FTP服务器
                // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
                boolean isLogin = ftp.login(username, password);// 登录\
                System.out.println("ftp服务器连接状态isLogin~~~~~~~~"+isLogin);
                ftp.enterLocalPassiveMode();
                reply = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                    return result;
                }
                System.out.println("上传目录ftpFilePath~~~~~~~~"+ftpFilePath);
                //切换到上传目录
                if (!ftp.changeWorkingDirectory(ftpFilePath)) {
                    //如果目录不存在创建目录
                    String[] dirs = ftpFilePath.split("/");
                    String tempPath = "";
                    for (String dir : dirs) {
                        if (null == dir || "".equals(dir)) continue;
                        tempPath += "/" + dir;
                        if (!ftp.changeWorkingDirectory(tempPath)) {
                            if (!ftp.makeDirectory(tempPath)) {
                                return result;
                            } else {
                                ftp.changeWorkingDirectory(tempPath);
                            }
                        }
                    }
                }
                //设置上传文件的类型为二进制类型
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.setBufferSize(100000);
                System.out.println("准备开始上传文件~~~~~~~~");
                //上传文件
                if (!ftp.storeFile(filename, input)) {
                    return result;
                }
                input.close();
                ftp.logout();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (ftp.isConnected()) {
                    try {
                        ftp.disconnect();
                    } catch (IOException ioe) {
                    }
                }
            }
            return result;
        }

    }

    /**
     * Description: 从FTP服务器下载文件
     * @param host FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
                                       String fileName, String localPath) {
        boolean debug = false;
        if(debug){
            return true;
        }else{
            boolean result = false;
            FTPClient ftp = new FTPClient();
            try {
                int reply;
                ftp.connect(host, port);
                // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
                ftp.login(username, password);// 登录
                reply = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                    return result;
                }
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
                ftp.enterLocalPassiveMode();
                FTPFile[] fs = ftp.listFiles();
                isExist(localPath);//如果路径不存在，创建
                for (FTPFile ff : fs) {
                    if (ff.getName().equals(fileName)) {
                        File localFile = new File(localPath + "/" + ff.getName());

                        OutputStream is = new FileOutputStream(localFile);
                        ftp.retrieveFile(ff.getName(), is);
                        is.close();
                    }
                }

                ftp.logout();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (ftp.isConnected()) {
                    try {
                        ftp.disconnect();
                    } catch (IOException ioe) {
                    }
                }
            }
            return result;
        }
    }
    public static void isExist(String path) {
        File file = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static void main(String[] args){
        FileInputStream ftpInput = null;
        try {
            ftpInput = new FileInputStream(new File("target/test.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean result = uploadFile("192.168.17.251", 21, "devFTP", "admin", "upload/attached/", "test1.txt", ftpInput);
        System.out.println("result==>"+result);
    }

}