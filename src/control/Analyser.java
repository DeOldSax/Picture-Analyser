package control;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.util.Callback;
import model.Pixel;
import model.Settings;

public class Analyser {
	private Map<Integer, Integer> occurences;
	private final ObservableList<Pixel> data;
	private final TableView<Pixel> table;
	private final ProgressBar progressBar;

	@SuppressWarnings("unchecked")
	public Analyser(final TableView<Pixel> table, final ProgressBar progressBar) {
		this.table = table;
		this.progressBar = progressBar;
		progressBar.setVisible(false);
		data = FXCollections.observableArrayList();
		table.setItems(data);

		TableColumn<Pixel, String> rgbColumn = new TableColumn<Pixel, String>("R G B");
		rgbColumn.setCellValueFactory(new PropertyValueFactory<Pixel, String>("rgb"));
		rgbColumn.setMinWidth(90);
		rgbColumn.setSortable(false);

		TableColumn<Pixel, String> hexColumn = new TableColumn<Pixel, String>("Hex");
		hexColumn.setCellValueFactory(new PropertyValueFactory<Pixel, String>("hexColor"));
		hexColumn.setMinWidth(90);
		hexColumn.setSortable(false);

		TableColumn<Pixel, String> colorColumn = new TableColumn<Pixel, String>("Color");
		colorColumn.setCellValueFactory(new PropertyValueFactory<Pixel, String>("color"));
		colorColumn.setMinWidth(110);
		colorColumn.setSortable(false);

		TableColumn<Pixel, String> occurence = new TableColumn<Pixel, String>("Occurence");
		occurence.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pixel, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Pixel, String> cdf) {
				return cdf.getValue().occurenceProperty();
			}
		});
		occurence.setMinWidth(100);
		occurence.setSortType(SortType.DESCENDING);

		TableColumn<Pixel, String> count = new TableColumn<Pixel, String>("Count");
		count.setCellValueFactory(new PropertyValueFactory<Pixel, String>("count"));
		count.setMinWidth(100);
		count.setSortable(false);

		table.getColumns().clear();

		table.getColumns().addAll(rgbColumn, hexColumn, colorColumn, occurence, count);

		colorColumn.setCellFactory(new Callback<TableColumn<Pixel, String>, TableCell<Pixel, String>>() {
			@Override
			public TableCell<Pixel, String> call(final TableColumn<Pixel, String> rgbColumn) {
				return new TableCell<Pixel, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							final String[] rgbs = ((Pixel) this.getTableRow().getItem()).getRgb().split(" ");
							this.setStyle("-fx-background-color: rgb(" + rgbs[0] + ", " + rgbs[1] + ", " + rgbs[2] + ")");
							setText(item);
						}
					}
				};
			}
		});

	}

	public void analyse() {
		progressBar.setProgress(0.0);
		progressBar.setVisible(true);
		final Image image2 = new Image("file:///" + Settings.getInstance().getImgFile().getAbsolutePath());
		final PixelReader pixelReader = image2.getPixelReader();

		occurences = new HashMap<Integer, Integer>();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				progressBar.setProgress(0.1);
			}
		});

		for (int i = 0; i < image2.getWidth(); i++) {
			for (int j = 0; j < image2.getHeight(); j++) {
				final int rgb = pixelReader.getArgb(i, j);
				Integer counts = occurences.get(rgb);
				if (counts == null) {
					counts = 1;
				} else {
					counts++;
				}
				occurences.put(rgb, counts);
			}
		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				progressBar.setProgress(0.3);
			}
		});

		final ArrayList<Integer> rgbs = new ArrayList<Integer>(occurences.keySet());
		final ArrayList<Pixel> pixel = new ArrayList<Pixel>();

		double test = 0.0;

		for (final Integer integer : rgbs) {

			final Color c = new Color(integer);

			final double value = (double) occurences.get(integer) / (image2.getWidth() * image2.getHeight());
			final String format = new DecimalFormat("#0.000000").format(value * 100.0);
			pixel.add(new Pixel(c.getRed() + " " + c.getGreen() + " " + c.getBlue() + "     ", Integer.toHexString(integer), format + " %",
					occurences.get(integer)));
			test = test + occurences.get(integer);
		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				progressBar.setProgress(0.5);
			}
		});

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		progressBar.setProgress(1);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				table.getColumns().get(2).setText("color (" + pixel.size() + ")");
				table.getColumns().get(4).setText("count (" + (image2.getWidth() * image2.getHeight()) + ")");
				data.setAll(pixel);
				progressBar.setVisible(false);
			}
		});
	}
}
