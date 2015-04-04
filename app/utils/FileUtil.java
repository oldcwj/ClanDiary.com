package utils;

import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

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
    
    public static boolean saveImageToDisk(File imageFile, String fileName) {
        File newFile = new File(Constant.ROOT_IMAGE_PATH);//
        newFile = new File(newFile.getAbsolutePath() + "/" + fileName);
        FileUtil.copyFile(imageFile, newFile);
        return true;
    }
    
    public static boolean scaleSmallImageToDisk(File newFile, String imageNameString) {
        boolean result = false;
        try {
            File smallFile = new File(Constant.ROOT_IMAGE_SMAILL_PATH);
            BufferedImage image = ImageIO.read(newFile);
            BufferedImage thumbnail =
                    Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                                 150, 100, Scalr.OP_ANTIALIAS);
            smallFile = new File(smallFile.getAbsolutePath() + "/" + imageNameString);
            result = ImageIO.write(thumbnail, "jpg", smallFile);
        } catch (IOException e) {
            e.printStackTrace();
        }  
        return result;
    }
    
    public static boolean scaleMiddleImageToDisk() {
        return false;
    }
}
