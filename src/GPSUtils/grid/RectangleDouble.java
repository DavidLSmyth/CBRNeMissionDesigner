package GPSUtils.grid;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class RectangleDouble extends Rectangle {
	
	public double x;
    public double y;
    
    public double width;
    public double height;
    
    public PointDouble topRight;
    public PointDouble topLeft;
    public PointDouble bottomRight;
    public PointDouble bottomLeft;
    
    
    public RectangleDouble() {
        this(0, 0, 0, 0);
    }

    /**
     * Constructs a new {@code Rectangle}, initialized to match
     * the values of the specified {@code Rectangle}.
     * @param r  the {@code Rectangle} from which to copy initial values
     *           to a newly constructed {@code Rectangle}
     * @since 1.1
     */
    public RectangleDouble(RectangleDouble r) {
        this(r.x, r.y, r.width, r.height);
    }
    
    

    /**
     * Constructs a new {@code Rectangle} whose upper-left corner is
     * specified as
     * {@code (x,y)} and whose width and height
     * are specified by the arguments of the same name.
     * @param     x the specified X coordinate
     * @param     y the specified Y coordinate
     * @param     width    the width of the {@code Rectangle}
     * @param     height   the height of the {@code Rectangle}
     * @since 1.0
     */
    public RectangleDouble(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new {@code Rectangle} whose upper-left corner
     * is at (0,&nbsp;0) in the coordinate space, and whose width and
     * height are specified by the arguments of the same name.
     * @param width the width of the {@code Rectangle}
     * @param height the height of the {@code Rectangle}
     */
    public RectangleDouble(double width, double height) {
        this(0, 0, width, height);
    }

    /**
     * Constructs a new {@code Rectangle} whose upper-left corner is
     * specified by the {@link Point} argument, and
     * whose width and height are specified by the
     * {@link Dimension} argument.
     * @param p a {@code Point} that is the upper-left corner of
     * the {@code Rectangle}
     * @param d a {@code Dimension}, representing the
     * width and height of the {@code Rectangle}
     */
    public RectangleDouble(Point p, Dimension d) {
        this(p.x, p.y, d.width, d.height);
    }

    /**
     * Constructs a new {@code Rectangle} whose upper-left corner is the
     * specified {@code Point}, and whose width and height are both zero.
     * @param p a {@code Point} that is the top left corner
     * of the {@code Rectangle}
     */
    public RectangleDouble(Point p) {
        this(p.x, p.y, 0, 0);
    }

    /**
     * Constructs a new {@code Rectangle} whose top left corner is
     * (0,&nbsp;0) and whose width and height are specified
     * by the {@code Dimension} argument.
     * @param d a {@code Dimension}, specifying width and height
     */
    public RectangleDouble(Dimension d) {
        this(0, 0, d.width, d.height);
    }
    public PointDouble getLocation() {
        return new PointDouble(x, y);
    }
    
    public RectangleDouble getBounds() {
        return new RectangleDouble(x, y, width, height);
    }
}
