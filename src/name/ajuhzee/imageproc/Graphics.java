package name.ajuhzee.imageproc;

import javafx.stage.Screen;
import javafx.stage.Stage;

public class Graphics {

	private static Stage mainStage;

	public static void setMainStage(Stage mainStage) {
		Graphics.mainStage = mainStage;
	}

	public static void setWidth(double width) {
		if (width > Screen.getPrimary().getBounds().getWidth()) {
			width = Screen.getPrimary().getBounds().getWidth();
		}

		mainStage.setWidth(width);
	}

	public static void setHeight(double height) {
		if (height > Screen.getPrimary().getBounds().getHeight()) {
			height = Screen.getPrimary().getBounds().getHeight();
		}

		mainStage.setHeight(height);
	}

}
