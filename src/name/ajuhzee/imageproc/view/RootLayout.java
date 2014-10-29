package name.ajuhzee.imageproc.view;

import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class RootLayout implements NodeRepresentation {

	public static RootLayout create(NodeRepresentation mainMenu, NodeRepresentation sideMenu,
			NodeRepresentation imageDisplay) throws IOException {
		// can be changed to fxml loading
		return new RootLayout(mainMenu, sideMenu, imageDisplay, new BorderPane());
	}

	private final BorderPane rootPane;

	private RootLayout(NodeRepresentation mainMenu, NodeRepresentation sideMenu, NodeRepresentation imageDisplay,
			BorderPane rootPane) throws IOException {
		this.rootPane = rootPane;
		rootPane.setTop(mainMenu.toNodeRepresentation());
		rootPane.setLeft(sideMenu.toNodeRepresentation());
		rootPane.setCenter(imageDisplay.toNodeRepresentation());
	}

	@Override
	public Node toNodeRepresentation() {
		return rootPane;
	}

}
