package com.ilku1297;

import org.opencv.photo.Photo;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DBHandler {
    public static final String DATA_FOLDER = "H:\\girlsPhoto";
    
    public static BufferedImage loadPhoto(String path) throws IOException {
        File f = new File(path);

        return ImageIO.read(f);
    }

    public static void saveImage(Photo photo, BufferedImage bufImage){
        throw new NotImplementedException();
    }
}
