package types;

import Geom.Point3D;

public class Ghost {
	private int id;
	private Point3D p;
	private double speed;
	private double raduis;
	public Ghost(int _id,double _speed,Point3D _p ,double _raduis) {
		this.id=_id;
		this.speed=_speed;
		this.raduis=_raduis;
		this.p=_p;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Point3D getP() {
		return p;
	}
	public void setP(Point3D p) {
		this.p = p;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getRadius() {
		return raduis;
	}
	public void setRadius(double radius) {
		this.raduis = radius;
	}

}


