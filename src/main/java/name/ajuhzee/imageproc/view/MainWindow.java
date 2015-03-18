package name.ajuhzee.imageproc.view;

import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import name.ajuhzee.imageproc.plugin.control.ContentControl;
import name.ajuhzee.imageproc.plugin.control.CorePluginContext;
import name.ajuhzee.imageproc.plugin.control.GeneralControl;
import name.ajuhzee.imageproc.plugin.control.ImageControl;
import name.ajuhzee.imageproc.plugin.control.MenuControl;
import name.ajuhzee.imageproc.preferences.SettingsManager;

/**
 * The main view of the program. It opens the main window.
 * 
 * @author Ajuhzee
 *
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
	 * @param primaryStage
	 *            the main stage
	 * @throws IOException
	 *             when the main window can't be created
	 */
	public MainWindow(Stage primaryStage, SettingsManager manager) throws IOException {
		mainStage = primaryStage;
		this.manager = manager;

		init(mainStage);

		mainMenu = PluginMenu.create();
		sideMenu = ContentPane.create();
		imageDisplay = ImageDisplay.create();
		imageDisplay.addImageChangedCallback(this::resize);

		rootLayout = RootLayout.create(mainMenu, sideMenu, imageDisplay);

		Parent rootPane = rootLayout.getRootPane();
		Scene primaryScene = new Scene(rootPane);
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}

	private Void resize(@SuppressWarnings("unused") Image img) {
		Platform.runLater(() -> mainStage.sizeToScene());
		return null;
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
