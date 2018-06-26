package GPSUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.geotools.io.NumberedLineWriter;

import GPSUtils.grid.GPSPolygon;
import GPSUtils.grid.GPSPolygonGrid;

public class TestUtils {
	
	//tests have coordinates
	GPSCoordinate p1;
	GPSCoordinate p2;
	GPSCoordinate p3;
	GPSCoordinate p4;
	GPSCoordinate p5;
	GPSCoordinate p6;
	GPSCoordinate p7;
	GPSCoordinate p8;
	GPSCoordinate p9;
	GPSCoordinate p10;
	
	GPSPolygon polygon;
	
	GPSPolygonGrid grid;
	
	GPSPolygon NUIGPoly;

	GPSPolygonGrid NUIGGrid;

	public TestUtils(int gridLatSpacing, int gridLongSpacing) throws Exception {
		// TODO Auto-generated constructor stub
		//A pentagonal / hexagonal grid around galway
		//highest lat
		p1 = new GPSCoordinate(53.355684, -9.034788, 3.112233445566);
		//lowest long
		p2 = new GPSCoordinate(53.318084, -9.135740, 3.112233445566);
		//lowest lat
		p3 = new GPSCoordinate(53.265427, -9.108212, 100.0);
		
		p4 = new GPSCoordinate(53.268804, -9.037489, 3.11223344556600001);
		//highest long
		p5 = new GPSCoordinate(53.305931, -8.989646, 8.11223344556);
		
		p6 = new GPSCoordinate(53.316051, -9.018173, 3.112233445);
		
		NUIGPoly = new GPSPolygon(Arrays.asList(p1,p2,p3,p4,p5,p6));
		NUIGGrid = new GPSPolygonGrid(NUIGPoly, gridLatSpacing, gridLongSpacing);
		
		p7 = new GPSCoordinate(-3.1234582, 8.9876544, 3.11223344);
		p8 = new GPSCoordinate(-5.1234582, 8.876544, 3.11223344);
		p9 = new GPSCoordinate(-5.1234582, 8.0876544, 3.11223344);
		
		polygon = new GPSPolygon(Arrays.asList(p1, p2, p3, p4, p5));
		grid = new GPSPolygonGrid(polygon, gridLatSpacing, gridLongSpacing);
	}
	
	public TestUtils() throws Exception {
		// TODO Auto-generated constructor stub
		//A pentagonal / hexagonal grid around galway
		//highest lat
		p1 = new GPSCoordinate(53.355684, -9.034788, 3.112233445566);
		//lowest long
		p2 = new GPSCoordinate(53.318084, -9.135740, 3.112233445566);
		//lowest lat
		p3 = new GPSCoordinate(53.265427, -9.108212, 100.0);
		
		p4 = new GPSCoordinate(53.268804, -9.037489, 3.11223344556600001);
		//highest long
		p5 = new GPSCoordinate(53.305931, -8.989646, 8.11223344556);
		
		p6 = new GPSCoordinate(53.316051, -9.018173, 3.112233445);
		
		NUIGPoly = new GPSPolygon(Arrays.asList(p1,p2,p3,p4,p5,p6));
		NUIGGrid = new GPSPolygonGrid(NUIGPoly, 200, 200);
		
		p7 = new GPSCoordinate(-3.1234582, 8.9876544, 3.11223344);
		p8 = new GPSCoordinate(-5.1234582, 8.876544, 3.11223344);
		p9 = new GPSCoordinate(-5.1234582, 8.0876544, 3.11223344);
		
		polygon = new GPSPolygon(Arrays.asList(p1, p2, p3, p4, p5));
		grid = new GPSPolygonGrid(polygon, 100, 100);
	}
	
	public List<GPSCoordinate> getNUIGPoints(){
		return Arrays.asList(p1,p2,p3,p4,p5,p6);
	}
	
	public GPSPolygon getNUIGPoly() {
		return NUIGPoly;
	}

	public void setNUIGPoly(GPSPolygon nUIGPoly) {
		NUIGPoly = nUIGPoly;
	}

	public GPSPolygonGrid getNUIGGrid() {
		return NUIGGrid;
	}

	public void setNUIGGrid(GPSPolygonGrid nUIGGrid) {
		NUIGGrid = nUIGGrid;
	}

	
	public List<GPSCoordinate> getPoints(){
		return Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10);
	}
	
	public GPSCoordinate getP1() {
		return p1;
	}

	public void setP1(GPSCoordinate p1) {
		this.p1 = p1;
	}

	public GPSCoordinate getP2() {
		return p2;
	}

	public void setP2(GPSCoordinate p2) {
		this.p2 = p2;
	}

	public GPSCoordinate getP3() {
		return p3;
	}

	public void setP3(GPSCoordinate p3) {
		this.p3 = p3;
	}

	public GPSCoordinate getP4() {
		return p4;
	}

	public void setP4(GPSCoordinate p4) {
		this.p4 = p4;
	}

	public GPSCoordinate getP5() {
		return p5;
	}

	public void setP5(GPSCoordinate p5) {
		this.p5 = p5;
	}

	public GPSCoordinate getP6() {
		return p6;
	}

	public void setP6(GPSCoordinate p6) {
		this.p6 = p6;
	}

	public GPSCoordinate getP7() {
		return p7;
	}

	public void setP7(GPSCoordinate p7) {
		this.p7 = p7;
	}

	public GPSCoordinate getP8() {
		return p8;
	}

	public void setP8(GPSCoordinate p8) {
		this.p8 = p8;
	}

	public GPSCoordinate getP9() {
		return p9;
	}

	public void setP9(GPSCoordinate p9) {
		this.p9 = p9;
	}

	public GPSCoordinate getP10() {
		return p10;
	}

	public void setP10(GPSCoordinate p10) {
		this.p10 = p10;
	}

	public GPSPolygon getPolygon() {
		return polygon;
	}

	public void setPolygon(GPSPolygon polygon) {
		this.polygon = polygon;
	}

	public GPSPolygonGrid getGrid() {
		return grid;
	}

	public void setGrid(GPSPolygonGrid grid) {
		this.grid = grid;
	}

}
