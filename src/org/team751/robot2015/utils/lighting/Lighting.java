package org.team751.robot2015.utils.lighting;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Lighting {
	public static enum LEDColor {
		RED, GREEN, BLUE, ORANGE, YELLOW, PURPLE, WHITE, OFF
	};

	public static void setColor(LEDColor color) {
		URL url;
		try {
			url = new URL("http://10.7.51.50/" + color.name().toLowerCase());
			InputStream stream = url.openStream();
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void disableLEDStrip() {
		setColor(LEDColor.OFF);
	}

	private Lighting() {
	}
}
