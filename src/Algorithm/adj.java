package Algorithm;

import java.util.ArrayList;

import Geom.Point3D;
/**
 * This class responsible for saving the data while building the graph  
 * @author ariel and moshe
 *
 */
public class adj {
private int id=0;
private Point3D source=new Point3D(0,0,0);
public adj(adj a) {
	this.setId(a.getId());
	this.setSource(a.getSource());
}

public Point3D getSource() {
	return source;
}
public void setSource(Point3D source) {
	this.source = source;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public adj() {
	
}
}
