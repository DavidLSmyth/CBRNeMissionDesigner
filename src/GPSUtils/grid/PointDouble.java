package GPSUtils.grid;

import java.awt.Point;
import java.beans.Transient;

public class PointDouble extends Point {

	/**
     * The X coordinate of this {@code Point}.
     * If no X coordinate is set it will default to 0.
     *
     * @serial
     * @see #getLocation()
     * @see #move(int, int)
     * @since 1.0
     */
    public double x;

    /**
     * The Y coordinate of this {@code Point}.
     * If no Y coordinate is set it will default to 0.
     *
     * @serial
     * @see #getLocation()
     * @see #move(int, int)
     * @since 1.0
     */
    public double y;

    /*
     * JDK 1.1 serialVersionUID
     */
    private static final long serialVersionUID = -5276940640259749850L;

    /**
     * Constructs and initializes a point at the origin
     * (0,&nbsp;0) of the coordinate space.
     * @since       1.1
     */
    public PointDouble() {
        this(0, 0);
    }

    /**
     * Constructs and initializes a point with the same location as
     * the specified {@code Point} object.
     * @param       p a point
     * @since       1.1
     */
    public PointDouble(PointDouble p) {
        this(p.x, p.y);
    }

    /**
     * Constructs and initializes a point at the specified
     * {@code (x,y)} location in the coordinate space.
     * @param x the X coordinate of the newly constructed {@code Point}
     * @param y the Y coordinate of the newly constructed {@code Point}
     * @since 1.0
     */
    public PointDouble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public double getX() {
        return x;
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the location of this point.
     * This method is included for completeness, to parallel the
     * {@code getLocation} method of {@code Component}.
     * @return      a copy of this point, at the same location
     * @see         java.awt.Component#getLocation
     * @see         java.awt.Point#setLocation(java.awt.Point)
     * @see         java.awt.Point#setLocation(int, int)
     * @since       1.1
     */
    @Transient
    public Point getLocation() {
        return new PointDouble(x, y);
    }

    /**
     * Sets the location of the point to the specified location.
     * This method is included for completeness, to parallel the
     * {@code setLocation} method of {@code Component}.
     * @param       p  a point, the new location for this point
     * @see         java.awt.Component#setLocation(java.awt.Point)
     * @see         java.awt.Point#getLocation
     * @since       1.1
     */
    public void setLocation(PointDouble p) {
        setLocation(p.x, p.y);
    }

    /**
     * Changes the point to have the specified location.
     * <p>
     * This method is included for completeness, to parallel the
     * {@code setLocation} method of {@code Component}.
     * Its behavior is identical with <code>move(int,&nbsp;int)</code>.
     * @param       x the X coordinate of the new location
     * @param       y the Y coordinate of the new location
     * @see         java.awt.Component#setLocation(int, int)
     * @see         java.awt.Point#getLocation
     * @see         java.awt.Point#move(int, int)
     * @since       1.1
     */
    public void setLocation(double x, double y) {
        move(x, y);
    }

    /**
     * Moves this point to the specified location in the
     * {@code (x,y)} coordinate plane. This method
     * is identical with <code>setLocation(int,&nbsp;int)</code>.
     * @param       x the X coordinate of the new location
     * @param       y the Y coordinate of the new location
     * @see         java.awt.Component#setLocation(int, int)
     */
    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Translates this point, at location {@code (x,y)},
     * by {@code dx} along the {@code x} axis and {@code dy}
     * along the {@code y} axis so that it now represents the point
     * {@code (x+dx,y+dy)}.
     *
     * @param       dx   the distance to move this point
     *                            along the X axis
     * @param       dy    the distance to move this point
     *                            along the Y axis
     */
    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Determines whether or not two points are equal. Two instances of
     * {@code Point2D} are equal if the values of their
     * {@code x} and {@code y} member fields, representing
     * their position in the coordinate space, are the same.
     * @param obj an object to be compared with this {@code Point2D}
     * @return {@code true} if the object to be compared is
     *         an instance of {@code Point2D} and has
     *         the same values; {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof PointDouble) {
        	PointDouble pt = (PointDouble)obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }
}