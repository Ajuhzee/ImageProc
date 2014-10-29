package name.ajuhzee.imageproc.view;

import javafx.scene.Node;

/**
 * Indicates that a class has a representation as JavaFX node.
 */
public interface NodeRepresentation {

	/**
	 * @return the node representation
	 */
	public Node toNodeRepresentation();

}
