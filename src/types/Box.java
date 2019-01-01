package types;

import Geom.Point3D;

public class Box {
	private int id;
	private Point3D rU;
	private Point3D lD;
	private Point3D rD;
	private Point3D lu;
	private int isvertic;

	public Box(int _id,Point3D _p,Point3D _p1) {
		this.id=_id;
		this.rU=_p;
		this.lD=_p1;
		this.rD=new Point3D(rU.x(),lD.y());
		this.lu=new Point3D(lD.x(),rU.y());
		this.isvertic=0;
	}
	public Point3D getrU() {
		return rU;
	}
	public void setrU(Point3D rU) {
		this.rU = rU;
	}
	public Point3D getlD() {
		return lD;
	}
	public void setlD(Point3D lD) {
		this.lD = lD;
	}
	public Point3D getrD() {
		return rD;
	}
	public void setrD(Point3D rD) {
		this.rD = rD;
	}
	public Point3D getLu() {
		return lu;
	}
	public void setLu(Point3D lu) {
		this.lu = lu;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
