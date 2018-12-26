package Coords;
import java.lang.reflect.Array;
import java.util.Arrays;

import Geom.Point3D;

public class MyCoords implements coords_converter {
	//Earth radius.
 final double rErth = 6371000;
/**
 * computes a new point which is the gps point transformed by a 3D vector (in meters).
 */
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		double lonNorm=Math.cos(gps.x()*(Math.PI/180));
		if(!isValid_GPS_Point(gps)) 
			return null;
		//***convert meter to radians ***
		double meterTolat =Math.asin(local_vector_in_meter.x()/rErth)*(180/Math.PI);
		double meterTolong=Math.asin(local_vector_in_meter.y()/rErth*lonNorm)*(180/Math.PI);
		// *** add vector after convert ***
		double x=meterTolat+gps.x();
		double y=meterTolong+gps.y();
		double z=gps.z()+local_vector_in_meter.z();
		// if the x point  is above and under a 90 degrees the point is illegal.
		if((y>90)||(y<-90)) { 
			System.out.println("Invalid x");
			return null;
		}
		if(x>180) {
			x=((x+180)%360)-180;
			
		}
		 if(x<-180) {
			x=(y+180)+180;
			
		}
		
		Point3D negps = new Point3D (x,y,z);
		if(isValid_GPS_Point(negps))
			return negps;
		return null;
     	
		}
	
/**
 * 
 * The function computes the 3D distance (in meters) between the two gps like points
 * @param gps0 a Point3d
 * @param gps1 a Point3d
 * @return the distance 
 * 
 */
	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		if(!isValid_GPS_Point(gps0)||!isValid_GPS_Point(gps1))
			return Double.MAX_VALUE;
		else {
			
		double lonNorm=Math.cos(gps0.x()*Math.PI/180);
		
		double diff_lat =gps1.x()-gps0.x();
		
		double diff_lon=gps1.y()-gps0.y();
		
		double diff_z=gps1.z()-gps0.z();
		//***convert meter to radians ***
		double diff_radianlat=diff_lat*Math.PI/180;
		
		double diff_radianlon=diff_lon*Math.PI/180;
		
		double toMeterlat=Math.sin(diff_radianlat)*rErth;
		
		double toMeterlon=Math.sin(diff_radianlon)*lonNorm*rErth;
//		System.out.println(toMeterlat*toMeterlat+toMeterlon*toMeterlon);
		
		return Math.sqrt(toMeterlat*toMeterlat+toMeterlon*toMeterlon);
		}
		
	}
/**
 * The function computes the 3D vector (in meters) between two gps like points
 * @param gps0 a Point3D
 * @param gps1 a Point3D
 * @return Point3D vector
 */
	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		if(!isValid_GPS_Point(gps0)||!isValid_GPS_Point(gps1))
			return null;
		else {
		double lonNorm=Math.cos(gps0.x()*Math.PI/180);
		
		double diff_lat =gps1.x()-gps0.x();
		
		double diff_lon=gps1.y()-gps0.y();
		
		double diff_z=gps1.z()-gps0.z();
		//***convert meter to radians ***
		double diff_radianlat=diff_lat*Math.PI/180;
		
		double diff_radianlon=diff_lon*Math.PI/180;
		
		double toMeterlat=Math.sin(diff_radianlat)*rErth;
		
		double toMeterlon=Math.sin(diff_radianlon)*lonNorm*rErth;

	    // Obtain vertical difference, too
	    double z   = gps1.z() - gps0.z();

	    return new Point3D(toMeterlat, toMeterlon, z);
	}
	}
/**
 * The function computes the polar representation of the 3D vector be gps0 to gps1
 * @param gps0 a Point3D
 * @param gps1 a Point3D
 * @return an array contains azimuth, elevation, and distance.
 */
	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		if(!isValid_GPS_Point(gps0)||!isValid_GPS_Point(gps1))
			return null;
		else {
		//**azimut***//
//		double longps0 = Math.toRadians(gps0.y()); 
//		double longps1 = Math.toRadians(gps1.y()); 
//		double latgps0 = Math.toRadians(gps0.x()); 
//		double latgps1 = Math.toRadians(gps1.x()); 
//		double delta = longps1 - longps0;
//		double left = Math.sin(delta)*Math.cos(latgps1);
//		double right = Math.cos(latgps0)*Math.sin(latgps1)-Math.sin(latgps0)*Math.cos(latgps1)*Math.cos(delta);
//		double	azimut = Math.atan2(left, right);
			Point3D vec =vector3D(gps0, gps1);
		double azimut=Math.toDegrees(Math.atan(Math.abs(vec.x()/vec.y())));
		if (vec.y()<0) {
			if (vec.x()>0) {
				azimut=180-azimut;
			}else
				azimut=180+azimut;
		}else {
			if(vec.x()<0) {
				azimut=360-azimut;
			}
		}
		//***distance***//
		double distance = distance3d(gps0,gps1);
		//***elevation***//
//		azimut = Math.toDegrees(azimut);
//		if(azimut<0) azimut+=360;
		double high = gps1.z() - gps0.z();
		double eleveation = Math.toDegrees(Math.asin(high/distance));
	
		double arr[] = {azimut,eleveation,distance};
		return arr;
		}
	}
/**
 * The funcrion checks if the Point3D is valid ,meaning :
 * x:[-180,+180]
 * y:[-90,+90];
 * z:[-450,+inf]
 * @param p a Poin3D.
 * @return true if this point is a valid .
 * 
 */
	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		if ((p.x()<-180 ||  p.x()>180) || (p.y()<-90 || p.y()>90) || (p.z()<-450 )) return false;
		return true;
	}



}
