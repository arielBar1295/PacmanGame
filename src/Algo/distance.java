package Algo;

import java.util.ArrayList;

import Geom.Point3D;
/**
 * This class responsible for holding and representing the data after calculating the shortest path from the player to a specific fruit.
 * @author ariel and moshe
 *
 */
public class distance {
private double dis;
//the ArrayList represents the way of the pacman to the fruit(holding points).
private ArrayList<Point3D> path;
public distance() {
	this.dis=0;
	this.path=new ArrayList<>();
}
public double getDis() {
	return dis;
}
public void setDis(double dis) {
	this.dis = dis;
}
public ArrayList<Point3D> getPath() {
	return path;
}
public void setPath(ArrayList<Point3D> path) {
	this.path = path;
}
}
