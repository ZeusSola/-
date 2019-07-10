package com.yuchen.catalog.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ANCCUtils {
    public static String getMac(String key, String data){
        Mac m;
        byte[] secretByte;
        SecretKey macKey;
        String resultMac = "";
        byte[] digest;

        try {
            m = Mac.getInstance("HmacSHA256");
            secretByte = DatatypeConverter.parseBase64Binary(key);
            byte[] dataBytes = data.getBytes("ASCII");
            macKey = new SecretKeySpec(secretByte, "HmacSHA256");
            m.init(macKey);
            digest = m.doFinal(dataBytes);
            resultMac = bytesToHexString(digest);
            resultMac = resultMac.toUpperCase();
        }catch (Exception e){

        }
        return resultMac;
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase());
        }

        return sb.toString();
    }


    public static String sendResByHttpClient(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(get);
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream ios = entity.getContent();
                String msg = "", line="";
                BufferedReader reader = new BufferedReader(
                new InputStreamReader(ios));
                while ((line = reader.readLine()) != null) {
                  msg += line + "\n";
                }
                return msg;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
