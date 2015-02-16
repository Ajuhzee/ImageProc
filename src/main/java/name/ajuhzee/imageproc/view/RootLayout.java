package name.ajuhzee.imageproc.view;

import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Consolidates the main elements of the program to a Rootlayout.
 * 
 * @author Ajuhzee
 *
 */
public class RootLayout implements NodeRepresentation {

	/**
	 * Creates the Rootlayout.
	 * 
	 * @param mainMenu
	 *            the main menu view
	 * @param sideMenu
	 *            the side menu view
	 * @param imageDisplay
	 *            the Image view
	 * @return the RootLayout
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static RootLayout create(NodeRepresentation mainMenu, NodeRepresentation sideMenu,
			NodeRepresentation imageDisplay) throws IOException {
		// can be changed to fxml loading
		return new RootLayout(mainMenu, sideMenu, imageDisplay, new BorderPane());
	}

	private final BorderPane rootPane;

	private RootLayout(NodeRepresentation mainMenu, NodeRepresentation sideMenu, NodeRepresentation imageDisplay,
			BorderPane rootPane) {
		this.rootPane = rootPane;
		rootPane.setTop(mainMenu.toNodeRepresentation());
		rootPane.setLeft(sideMenu.toNodeRepresentation());
		rootPane.setCenter(imageDisplay.toNodeRepresentation());
	}

	@Override
	public Node toNodeRepresentation() {
		return rootPane;
	}

	/**
	 * @return the root pane
	 */
	public Pane getRootPane() {
		return rootPane;
	}

}
