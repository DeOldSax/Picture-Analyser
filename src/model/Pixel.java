package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class Pixel {
	private final SimpleStringProperty hexColor;
	private final SimpleStringProperty rgb;
	private final SimpleStringProperty color;
	private final SimpleStringProperty occurence;
	private final SimpleStringProperty count;

	public Pixel(String rgb, String hexColor, String occurence, Integer count) {
		this.rgb = new SimpleStringProperty(rgb);
		this.occurence = new SimpleStringProperty(occurence);
		this.color = new SimpleStringProperty("");
		this.hexColor = new SimpleStringProperty("#" + hexColor.toUpperCase());
		this.count = new SimpleStringProperty(String.valueOf(count));
	}

	public void setRgb(String rGB) {
		this.rgb.set(rGB);
	}

	public void setOccurence(String occurence) {
		this.occurence.set(occurence);
	}

	public String getRgb() {
		return this.rgb.get();
	}

	public String getOccurence() {
		return this.occurence.get();
	}

	public String getColor() {
		return this.color.get();
	}

	public void setColor(String color) {
		this.color.set("");
	}

	public void setHexColor(String hexColor) {
		this.hexColor.set(hexColor);
	}

	public String getHexColor() {
		return this.hexColor.get();
	}

	public void setCount(String count) {
		this.count.set(count);
	}

	public String getCount() {
		return this.count.get();
	}

	public ObservableValue<String> occurenceProperty() {
		return occurence;
	}
}
