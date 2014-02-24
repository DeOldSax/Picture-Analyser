package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Pixel;
import control.Analyser;
import controller.FileController;

public class Controller implements Initializable {

	@FXML
	private TextField pathToImgField;

	@FXML
	private Button imgChooser;

	@FXML
	private ImageView selectedImage;

	@FXML
	private TableView<Pixel> table;

	@FXML
	private Button analyser;

	@FXML
	private ProgressBar progressBar;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		if (selectedImage == null) {
			System.out.println("image is null");
		}
		new FileController(pathToImgField, imgChooser, analyser, selectedImage);
		final Analyser analyser2 = new Analyser(table, progressBar);
		analyser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						analyser2.analyse();
					}
				}).start();
			}
		});
	}
}
