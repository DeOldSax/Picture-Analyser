package controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Settings;

public class FileController {

	public FileController(final TextField pathToImgField, final Button imgChooser, final Button analyser, final ImageView selectedImage) {
		analyser.setMouseTransparent(true);
		analyser.setOpacity(0.7);
		imgChooser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser chooser = new FileChooser();
				final File selectedFile = chooser.showOpenDialog(null);
				if (selectedFile != null) {
					if (!(selectedFile.getName().toLowerCase().endsWith(".png") || selectedFile.getName().toLowerCase().endsWith(".jpg"))) {
						System.out.println("File is not supported!");
					} else {
						Settings.getInstance().setImgFile(selectedFile);
						pathToImgField.setText(selectedFile.getAbsolutePath());
						analyser.setMouseTransparent(false);
						analyser.setOpacity(1);
						selectedImage.setImage(new Image("file:///" + selectedFile.getAbsolutePath()));
					}
				}
			}
		});
	}
}
