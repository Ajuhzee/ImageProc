package name.ajuhzee.imageproc.view;

import static com.google.common.base.Preconditions.checkNotNull;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import name.ajuhzee.imageproc.plugin.control.ContentControl;

/**
 * Creates a side menu for the rootlayout.
 * 
 * @author Ajuhzee
 *
 */
public class ContentPane implements NodeRepresentation, ContentControl {

	/**
	 * 
	 * @return the ContentPane
	 */
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
	public void clearContent() {
		Platform.runLater(() -> contentWrapper.setCenter(null));
	}

	@Override
	public Node toNodeRepresentation() {
		return contentWrapper;
	}

}
