package GPSUtils.grid;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Arrays;

public class PolygonDouble extends Polygon {
	
	public double xpoints[];
	public double ypoints[];
	protected RectangleDouble bounds;
	private static final int MIN_LENGTH = 4;
	
	public PolygonDouble() {
		// TODO Auto-generated constructor stub
	}

	public PolygonDouble(double[] xpoints, double[] ypoints, int npoints) {
		//super(arg0, arg1, arg2);
		// Fix 4489009: should throw IndexOutOfBoundsException instead
        // of OutOfMemoryError if npoints is huge and > {x,y}points.length
        if (npoints > xpoints.length || npoints > ypoints.length) {
            throw new IndexOutOfBoundsException("npoints > xpoints.length || "+
                                                "npoints > ypoints.length");
        }
        // Fix 6191114: should throw NegativeArraySizeException with
        // negative npoints
        if (npoints < 0) {
            throw new NegativeArraySizeException("npoints < 0");
        }
        // Fix 6343431: Applet compatibility problems if arrays are not
        // exactly npoints in length
        this.npoints = npoints;
        this.xpoints = Arrays.copyOf(xpoints, npoints);
        this.ypoints = Arrays.copyOf(ypoints, npoints);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void addPoint(double x, double y) {
        if (npoints >= xpoints.length || npoints >= ypoints.length) {
            int newLength = npoints * 2;
            // Make sure that newLength will be greater than MIN_LENGTH and
            // aligned to the power of 2
            if (newLength < MIN_LENGTH) {
                newLength = MIN_LENGTH;
            } else if ((newLength & (newLength - 1)) != 0) {
                newLength = Integer.highestOneBit(newLength);
            }

            xpoints = Arrays.copyOf(xpoints, newLength);
            ypoints = Arrays.copyOf(ypoints, newLength);
        }
        xpoints[npoints] = x;
        ypoints[npoints] = y;
        npoints++;
        if (bounds != null) {
            updateBounds(x, y);
        }
    }

	private void updateBounds(double x, double y) {
		if (x < bounds.x) {
		        bounds.width = bounds.width + (bounds.x - x);
		        bounds.x = x;
		    }
		    else {
		        bounds.width = Math.max(bounds.width, x - bounds.x);
		        // bounds.x = bounds.x;
		}
		
		if (y < bounds.y) {
		    bounds.height = bounds.height + (bounds.y - y);
		    bounds.y = y;
		}
		else {
		    bounds.height = Math.max(bounds.height, y - bounds.y);
		    // bounds.y = bounds.y;
		    }
	}
	
	public RectangleDouble getBounds() {
		return getBoundingBox();
    }
	
	public RectangleDouble getBoundingBox() {
        if (npoints == 0) {
            return new RectangleDouble();
        }
        if (bounds == null) {
            calculateBounds(xpoints, ypoints, npoints);
        }
        return bounds.getBounds();
    }
	
	
	void calculateBounds(double xpoints[], double ypoints[], int npoints) {
        double boundsMinX = Double.MAX_VALUE;
        double boundsMinY = Double.MAX_VALUE;
        double boundsMaxX = Double.MIN_VALUE;
        double boundsMaxY = Double.MIN_VALUE;

        for (int i = 0; i < npoints; i++) {
            double x = xpoints[i];
            boundsMinX = Math.min(boundsMinX, x);
            boundsMaxX = Math.max(boundsMaxX, x);
            double y = ypoints[i];
            boundsMinY = Math.min(boundsMinY, y);
            boundsMaxY = Math.max(boundsMaxY, y);
        }
        bounds = new RectangleDouble(boundsMinX, boundsMinY,
                               boundsMaxX - boundsMinX,
                               boundsMaxY - boundsMinY);
    }

}
