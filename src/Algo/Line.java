package Algo;

import Geom.Point3D;

public class Line {
private Point3D p1;
private Point3D p2;
private double[] y;
public Line(Point3D _p1,Point3D _p2) {
	this.p1=_p1;
	this.p2=_p2;
	this.y=new double[2];
	init();
}
private void init() {
	double m=0;
	if ((p2.x()-p1.x())==0){
		m=0;
	}else {
	 m=(p2.y()-p1.y())/(p2.x()-p1.x());
	}
	y[0]=m;
	y[1]=(-m*p1.x())+p1.y();

}
public double valOfY(double x) {
	return ((x*y[0])+y[1]);
}
public double valOfX(double _y) {
	return ((_y-y[1])/y[0]);
}
}
