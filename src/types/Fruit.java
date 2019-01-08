package types;

import Geom.Point3D;
/**
 * This class represents a single fruit.
 * @author moshe and ariel
 *
 */

public class Fruit {
	private int id;
	private Point3D p;
	private double weight;
	/**
	 * Constructor 
	 * @param _id fruit id
	 * @param _weight fruit weight
	 * @param _p fruit point location
	 */
	public Fruit(int _id,double _weight,Point3D _p ) {
		this.id=_id;
		this.weight=_weight;
		
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
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	
}
