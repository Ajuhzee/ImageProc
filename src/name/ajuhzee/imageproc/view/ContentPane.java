package name.ajuhzee.imageproc.view;

import static com.google.common.base.Preconditions.checkNotNull;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import name.ajuhzee.imageproc.plugin.control.ContentControl;

public class ContentPane implements NodeRepresentation, ContentControl {

	public static ContentPane create() {
		// can be changed to fxml loading
		return new ContentPane();
	}

	private final BorderPane contentWrapper = new BorderPane();

	@Override
	public void setContent(Node newContent) {
		checkNotNull(newContent);
		Platform.runLater(() -> contentWrapper.setCenter(newContent));
	}

	@Override
	public Node toNodeRepresentation() {
		return contentWrapper;
	}

}