# collinearPoints
A java solution for Pattern Recognition

Problem:
Given a set of P feature points in the bidimensional plane, determine every line that contains at least N or
more COLLINEAR points.

From requirements, apis not consumes / produces any other response datas type except json.

Adopted approach:
To resolve this problem I used the slope formula -> 
For two points p0 (x0,y0) , p1 (x1,y1) , the slope is calculated as:

slope = (y1 - y0) / (x1 -x0)

Definition collinear:
Three or more points are collinear, if slope of any two pairs of points is same.
With three points A, B and C, If

Slope of AB = slope of BC = slope of AC,

then A, B and C are collinear points.

Algorithm flow:
From a space points:

1.Choose an point p as pivot element 
2.For others points calculate their slope with pivot. 
3.In according (2) sort these points in new list.
4.iterate this new sorted list. Check if any N (or more) adjacent points in this list, have equal slopes with pivot. 
  If yes, these points, with pivot, are collinear and they make a line.
  If N is 1, the algorithm not started. 
  If N is two, algorithm provide an added check to avoid duplicate

Extra:
To populate space without manually call post api "/point/", you use the method in collinearPointService called "readFromFile" which read a file from
directory "src/main/resources".

The file default extension is "txt" and respect this example content:
x coordinate y coordinate

for example
2 4
4 6
6 8

In service file there is a commented code to initialize space.

* Remove comment 
* 
* if (this.points.isEmpty()) { this.readFromFile("input"); }
 */



