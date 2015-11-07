package name.ajuhzee.imageproc.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import name.ajuhzee.imageproc.plugin.control.*;
import name.ajuhzee.imageproc.preferences.SettingsManager;

import java.io.IOException;

/**
 * The main view of the program. It opens the main window.
 *
 * @author Ajuhzee
 */
public class MainWindow implements CorePluginContext {

	private static void init(Stage stage) {
		stage.setTitle("ImageProc");
	}

	private final Stage mainStage;

	private final RootLayout rootLayout;

	private final PluginMenu mainMenu;

	private final ContentPane sideMenu;

	private final ImageDisplay imageDisplay;

	private final SettingsManager manager;

	/**
	 * Creates the main window.
	 *
	 * @param primaryStage the main stage
	 * @throws IOException when the main window can't be created
	 */
	public MainWindow(Stage primaryStage, SettingsManager manager) throws IOException {
		mainStage = primaryStage;
		this.manager = manager;

		init(mainStage);

		mainMenu = PluginMenu.create();
		sideMenu = ContentPane.create();
		imageDisplay = ImageDisplay.create();

		rootLayout = RootLayout.create(mainMenu, sideMenu, imageDisplay);

		Parent rootPane = rootLayout.getRootPane();
		Scene primaryScene = new Scene(rootPane);
		primaryStage.setScene(primaryScene);
		maximize(primaryStage);
		primaryStage.show();
	}

	private void maximize(Stage primaryStage) {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
	}

	@Override
	public GeneralControl getGeneralControl() {
		return new GeneralControl(mainStage);
	}

	@Override
	public ImageControl getImageControl() {
		return imageDisplay;
	}

	@Override
	public PluginMenu getMainMenu() {
		return mainMenu;
	}

	@Override
	public Stage getMainStage() {
		return mainStage;
	}

	@Override
	public ContentControl getSideMenuControl() {
		return sideMenu;
	}

	@Override
	public MenuControl getMenuControl() {
		return mainMenu.getMenuControl();
	}

	@Override
	public SettingsManager getSettings() {
		return manager;
	}
}
