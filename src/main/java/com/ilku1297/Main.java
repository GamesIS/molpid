package com.ilku1297;


import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main extends Application {
    public static final int WEB_CAM_WIDTH = 640;//Можно поставить 640
    public static final int WEB_CAM_HEIGHT = 480;//Можно поставить 480

    public static final int TIME_UPDATE = 35;// Плавно при 20

    Mat matrix = null;

    //private Webcam webcam; //webcam from sarxos libary
    public static final ImageView mainImage = new ImageView();

    private Webcam webcam = Webcam.getDefault();
    private WebcamPanel webcamPanel = new WebcamPanel(webcam, false);

    @Override
    public void start(Stage stage) throws IOException {
        //webcam.setViewSize(new Dimension(1920,1080));
        Dimension frameSize = new Dimension(WEB_CAM_WIDTH, WEB_CAM_HEIGHT);
        webcam.setCustomViewSizes(new Dimension[] { frameSize });
        webcam.setViewSize(frameSize);

        //final SwingNode swingNode = new SwingNode();

        //createSwingContent(swingNode);

        //StackPane pane = new StackPane();
        //pane.getChildren().add(swingNode);


        //stage.setTitle("Swing in JavaFX");
        //stage.setScene(new Scene(pane, 1024, 576));
        //stage.show();



        mainImage.setFitWidth(WEB_CAM_WIDTH);
        mainImage.setFitHeight(WEB_CAM_HEIGHT);
        //mainImage.setImage(bufToFXImage(DBHandler.loadPhoto("E:/OpenCV/chap22/sanpshot.jpg")));



        Label secondLabel = new Label("I'm a Label on new Window");

        StackPane secondaryLayout = new StackPane();
        //secondaryLayout.getChildren().add(secondLabel);
        secondaryLayout.getChildren().add(mainImage);

        Scene secondScene = new Scene(secondaryLayout, WEB_CAM_WIDTH, WEB_CAM_HEIGHT);
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        newWindow.setX(stage.getX() + 200);
        newWindow.setY(stage.getY() + 100);

        newWindow.show();

        webcam.open();
        cycle();

    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                swingNode.setContent(webcamPanel);
                webcamPanel.start();
            }
        });
    }

    private void cycle() {
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(TIME_UPDATE);
                    System.out.println(webcam.getFPS());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WritableImage writableImage = bufToFXImage(webcam.getImage());
                Platform.runLater(() -> mainImage.setImage(writableImage));
            }
        }).start();
    }

    public static WritableImage bufToFXImage(BufferedImage bufferedImage){
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public void saveImage() {
        // Saving the Image
        String file = "E:/OpenCV/chap22/sanpshot.jpg";

        // Instantiating the imgcodecs class
        Imgcodecs imageCodecs = new Imgcodecs();

        // Saving it again
        imageCodecs.imwrite(file, matrix);
    }




    public static void main(String args[]) {
        launch(args);
    }

}