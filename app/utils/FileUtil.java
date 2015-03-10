package utils;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    public static void copyFile(File src, File dest){
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(src));
            fos = new BufferedOutputStream(new FileOutputStream(dest));

            byte[] buf = new byte[4096];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
    public static void uploadFile(String imageName) {
        Mac mac = new Mac(Constant.ACCESS_KEY, Constant.SECRET_KEY);
        PutPolicy putPolicy = new PutPolicy(imageName);
        String uptoken;
        try {
            uptoken = putPolicy.token(mac);
            PutExtra extra = new PutExtra();
            String key = "";
            String localFile = "";
            PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
            System.out.println("resource==" + ret.getResponse());
            // ret.
        } catch (AuthException | JSONException e) {
            e.printStackTrace();
        }
    }
}
