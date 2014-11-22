package name.ajuhzee.imageproc.view;

import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import name.ajuhzee.imageproc.plugin.control.ContentControl;
import name.ajuhzee.imageproc.plugin.control.CorePluginContext;
import name.ajuhzee.imageproc.plugin.control.GeneralControl;
import name.ajuhzee.imageproc.plugin.control.ImageControl;

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

	/**
	 * Creates the main window.
	 * 
	 * @param primaryStage
	 *            the main stage
	 * @throws IOException
	 *             when the main window can't be created
	 */
	public MainWindow(Stage primaryStage) throws IOException {
		mainStage = primaryStage;
		init(mainStage);

		mainMenu = PluginMenu.create();
		sideMenu = ContentPane.create();
		imageDisplay = ImageDisplay.create();

		rootLayout = RootLayout.create(mainMenu, sideMenu, imageDisplay);

		Parent rootPane = rootLayout.getRootPane();
		final Scene primaryScene = new Scene(rootPane);
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}

	@Override
	public GeneralControl getGeneralControl() {
		return new GeneralControl();
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

}
