package modules;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

/** This Listener implements drag and drop for Draw2D Figure */
public class MyListener implements MouseListener, MouseMotionListener {

	Figure figure;
	Point location;

	/** constructor save reference to figure, then add listeners */
	public MyListener(Figure figure) {
		this.figure = figure;
		figure.addMouseListener(this);
		figure.addMouseMotionListener(this);

		figure.getCursor();
		// figure.
	}

	// @Override
	public void mousePressed(MouseEvent me) {
		location = me.getLocation();
		me.consume();
	}

	// @Override
	public void mouseDragged(MouseEvent me) {
		Point newLocation = me.getLocation();
		if (location == null || newLocation == null)
			return;
		// calculate offset wrt last location
		Dimension offset = newLocation.getDifference(location);
		if (offset.width == 0 && offset.height == 0)
			return;
		// exchange location
		location = newLocation;

		// old Bounds are dirty
		figure.getUpdateManager().addDirtyRegion(figure.getParent(), figure.getBounds());

		// translate figure
		figure.translate(offset.width, offset.height);

		// new Bounds are dirty
		figure.getUpdateManager().addDirtyRegion(figure.getParent(), figure.getBounds());

		// new Bounds: set parent constraint
		figure.getParent().getLayoutManager().setConstraint(figure, figure.getBounds());
		//
		me.consume();
	}

	// @Override
	public void mouseReleased(MouseEvent me) {
		if (location == null)
			return;
		location = null;
		me.consume();
	}

	// @Override
	public void mouseEntered(MouseEvent me) {
	}

	// @Override
	public void mouseExited(MouseEvent me) {
	}

	// @Override
	public void mouseHover(MouseEvent me) {
	}

	// @Override
	public void mouseMoved(MouseEvent me) {
	}

	// @Override
	public void mouseDoubleClicked(MouseEvent me) {
	}

}
