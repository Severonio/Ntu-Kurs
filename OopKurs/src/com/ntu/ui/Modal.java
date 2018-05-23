package com.ntu.ui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class Modal {
	
	public static void display(String title, double x, double y, Pane pane) {
	    Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(x);
        stage.setMinHeight(y);
        stage.setScene(new Scene(pane, stage.getMinWidth(), stage.getMinHeight()));
        File file = new File("C:/Media Files/Photoes/sss.png");
        String localUrl = null;
        try {
            localUrl = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image image = new Image(localUrl);
        stage.getIcons().addAll(image);
        stage.setResizable(false);

        stage.show();
        
	}
	

}
