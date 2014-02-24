package model;

import java.io.File;

public class Settings {
	private static Settings instance;
	private File imgFile;

	private Settings() {

	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	public void setImgFile(File pathToImg) {
		this.imgFile = pathToImg;
	}

	public File getImgFile() {
		return imgFile;
	}
}
