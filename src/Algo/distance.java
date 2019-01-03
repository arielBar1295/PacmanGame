package Algo;

import java.util.ArrayList;

import Geom.Point3D;

public class distance {
private double dis;
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
