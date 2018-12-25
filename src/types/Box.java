package types;

import Geom.Point3D;

public class Box {
	private int id;
	private Point3D p;
	private Point3D p1;
	private Point3D p2;
	public Box(int _id,Point3D _p,Point3D _p1) {
		this.id=_id;
		this.p=_p;
		this.p1=_p1;
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
	public Point3D getP1() {
		return p1;
	}
	public void setP1(Point3D p1) {
		this.p1 = p1;
	}
}
