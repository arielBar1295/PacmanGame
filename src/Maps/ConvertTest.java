package Maps;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Geom.Point3D;

class ConvertTest {
private Point3D pointInPix=new Point3D(940,600);
private Convert c=new Convert();
//private Point3D pointInPix=new Point3D(940,600);

	@Test
	void pixToCoordsTest() {
		Point3D expected=new Point3D(35.2089265457083,32.1019,0.0);
		Point3D actual=c.pixToCo(pointInPix,1433,600);
		
		assertTrue(expected.equals(actual));
	}
	@Test
	void coordsToPixTest() {
		Point3D expected=new Point3D(940.0,560.0,0.0);
		Point3D actual=c.conToPix(new Point3D(35.2089265457083,32.10214794392523,0.0),1433,600);
		System.out.println(actual);
		assertTrue(expected.equals(actual));
	}

}
