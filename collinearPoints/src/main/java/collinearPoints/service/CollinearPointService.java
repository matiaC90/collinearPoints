package collinearPoints.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import collinearPoints.model.Point;
import collinearPoints.utils.FileUtils;

@Service
public class CollinearPointService {

	private final int TWO_POINTS = 2;

	private List<Point> points;

	public CollinearPointService() {
		super();
		this.points = new ArrayList<Point>();
	}

	/**
	 * 
	 * Function to get space
	 * 
	 * @return
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * 
	 * @param point
	 *            Function to add point
	 * @return
	 */
	public Point addPoint(Point point) {
		this.points.add(point);
		return point;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	/**
	 * Function to delete all points on plane
	 */
	public void deleteAllPoints() {
		this.points.clear();
	}

	/**
	 * Function to read and populate from file the plane
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	private void readFromFile(String fileName) throws Exception {

		File f = FileUtils.getFileFromName(fileName);
		BufferedReader br = new BufferedReader(new FileReader(f));
		while (br.ready()) {
			String[] s;
			do {
				s = br.readLine().trim().split("\\s+");
			} while (s.length != 2);

			Point toInsert = new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
			if (!this.points.contains(toInsert)) {
				this.points.add(toInsert);
			}
		}

		br.close();
	}

	/**
	 * Algorithm Steps:
	 * 
	 * 1.Choose an point p as pivot element 2.For others points calculate their
	 * slope with pivot. 3.In according (2) sort these points in new list. 4.iterate
	 * this new sorted list. Check if any N (or more) adjacent points in this list,
	 * have equal slopes with pivot. If yes, these points, with pivot, are collinear
	 * and they make a line.
	 * 
	 * If N is 1, the algorithm not started. 
	 * If N is 2, always is possible draw line from two points.
	 * 
	 * @throws Exception
	 * 
	 */
	public List<Point[]> getCollinearPointsFromSpace(int numberPointsOnLine) throws Exception {

		List<Point[]> lines = new ArrayList<Point[]>();

		/**
		 * Remove comment from this if load points in space from file Put file.txt in
		 * src/main/resources and use the filename
		 * 
		 * if (this.points.isEmpty()) { this.readFromFile("input"); }
		 */

		if (numberPointsOnLine >= TWO_POINTS) {
			for (int i = 0; i < points.size(); i++) {
				Point pivot = points.get(i);
				getCoolinearPointsForPivot(numberPointsOnLine, lines, pivot);
			}
		}

		// always draw a line from two points
		else if (numberPointsOnLine == TWO_POINTS) {

			for (int i = 0; i < points.size(); i++) {
				Point pivot = points.get(i);
				List<Point> otherPoints = points.stream().filter(p -> !p.equals(pivot)).collect(Collectors.toList());
				for (int j = 0; j < otherPoints.size(); j++) {
					Point[] ret = { pivot, otherPoints.get(j) };
					lines.add(ret);
				}
			}
		}

		return lines;
	}

	private void getCoolinearPointsForPivot(int numberPointsOnLine, List<Point[]> lines, Point pivot) {

		// Sort other point relative at slope order of pivot
		Point[] sortedPointsBySlope = this.points.toArray(new Point[points.size()]);
		Arrays.sort(sortedPointsBySlope, pivot.SLOPE_ORDER);

		boolean isFirstIteration = true;
		double previous = 0.0;
		List<Point> collinearPointsForPivot = new ArrayList<Point>();
		for (Point p : sortedPointsBySlope) {
			double currentSlope = p.slopeTo(pivot);

			// Necessary boolean condition because slope from point and pivot is equal at
			// first iteration
			if (isFirstIteration || currentSlope != previous) {
				checkCollinearPoints(numberPointsOnLine, lines, pivot, collinearPointsForPivot);
				// reset
				collinearPointsForPivot.clear();
			}

			// add point to collinear
			collinearPointsForPivot.add(p);

			// previous = current slope
			previous = p.slopeTo(pivot);

			if (isFirstIteration)
				isFirstIteration = false;
		}

		// check if last n points are collinear (edge case)
		checkCollinearPoints(numberPointsOnLine, lines, pivot, collinearPointsForPivot);
	}

	private void checkCollinearPoints(int numberPointsOnLine, List<Point[]> lines, Point pivot,
			List<Point> collinearPointsForPivot) {

		// this check to salt first iteration
		if (collinearPointsForPivot.size() > 0) {
			boolean checkCollinearSize = (collinearPointsForPivot.size() + 1) >= numberPointsOnLine;

			// +1 for next insert of pivot
			if (checkCollinearSize) {
				collinearPointsForPivot.add(pivot);
				Collections.sort(collinearPointsForPivot);

				// first must be match with pivot
				if (pivot == collinearPointsForPivot.get(0)) {
					Point[] ret = collinearPointsForPivot.toArray(new Point[collinearPointsForPivot.size()]);
					lines.add(ret);
				}
			}
		}
	}

}
