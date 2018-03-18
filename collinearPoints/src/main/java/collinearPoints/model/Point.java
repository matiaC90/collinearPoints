package collinearPoints.model;

import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Point implements Comparable<Point> {

	private int x;

	private int y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Calculate slope between two points.
	 * (This and another in case)
	 * Formula: 
	 * From two point p0(x0,y0) p1 (x1,y1) -> slope = (y1-y0) / (x1-x0)
	 * Two particular case if direction are equal:
	 * If two points are equals the value is negative infinity
	 * If the segment from two points is vertical the value is positive infinity
     * If the segment from two points is horizontal the value is 0.0
	 * 
	 * @param that
	 * @return
	 */
	public double slopeTo(Point that) {
		if (that.x == this.x) {
			if (that.y == this.y) {
				return Double.NEGATIVE_INFINITY;
			}
			return Double.POSITIVE_INFINITY;
		}

		if (that.y == this.y) {
			return 0.0;
		}

		return (double) (that.y - this.y) / (that.x - this.x);
	}

	/**
	 * Compare two points
	 * 
	 * @param that
	 * @return
	 */
	public int compareTo(Point that) {
		if (this.y == that.y && this.x == that.x)
			return 0;
		if (this.y < that.y || (this.y == that.y && this.x < that.x))
			return -1;
		return 1;
	}

	
	/**
	 * Comparator used in sort operation before launch algorithm.
	 *  Other points are sorted by slope of current point
	 */
	@JsonIgnore
	public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
		public int compare(Point p1, Point p2) {
			double slope1 = Point.this.slopeTo(p1);
			double slope2 = Point.this.slopeTo(p2);
			if (slope1 < slope2)
				return -1;
			if (slope1 == slope2)
				return 0;
			return 1;

		}
	};

}
