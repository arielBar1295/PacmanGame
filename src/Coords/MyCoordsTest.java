package Coords;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import Geom.Point3D;

class MyCoordsTest {
	Point3D pTest = new Point3D (32.103315,35.209039,670);
	Point3D p2Test = new Point3D (32.106352,35.205225,650);
	Point3D p3Test = new Point3D(337.699,-359.249,-20);
	Point3D p4Test = new Point3D(185.3434,35.209039,670);
	MyCoords m= new MyCoords();
	double arr[] =m.azimuth_elevation_dist(pTest, p2Test);

	
	
	@Test
	void isValidTest() {
		assertFalse(m.isValid_GPS_Point(p4Test));
	}
	@Test
	void isValidTestTrue() {
		assertTrue(m.isValid_GPS_Point(p2Test));
	}
	@Test
	void addTest() {
		Point3D expected=new Point3D(32.106352000071396,35.20630221454907,650.0);
		Point3D actual=m.add(pTest, p3Test);
		assertTrue(expected.equals(actual));
	}
	@Test
	void distance3d() {
		double actual=m.distance3d(pTest, p2Test);
		double expected=493.0523318324134;
		assertEquals(actual, expected,0.0001);
	}
	@Test
	void azimuth_elevation_distTest() {
		double []  actual=m.azimuth_elevation_dist(pTest, p2Test); ;
		double [] expected= {313.23042032651705, -2.3247635173865278, 493.0523318324134};
		boolean flag = true;
		for (int i = 0; i < expected.length; i++) {
			if (actual[i]!= expected[i])
				flag=false;
		}
		assertTrue(flag);
		
		
	}
	
	@Test
	void vec3D() {
		Point3D vecactual = m.vector3D(pTest, p2Test);
		Point3D expected = new Point3D(337.69899206128815,-359.24920693881893,-20.0);
		assertTrue(vecactual.equals(expected));
		
	}

}
