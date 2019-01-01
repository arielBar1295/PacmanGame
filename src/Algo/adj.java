package Algo;

import java.util.ArrayList;

import Geom.Point3D;

public class adj {
private ArrayList<Point3D> ad=new  ArrayList<Point3D> (); ;
private Point3D source=new Point3D(0,0,0);

public ArrayList<Point3D> getAd() {
	return ad;
}
public void setAd(ArrayList<Point3D> ad) {
	this.ad = ad;
}
public Point3D getSource() {
	return source;
}
public void setSource(Point3D source) {
	this.source = source;
}

public adj() {
	
}
}
