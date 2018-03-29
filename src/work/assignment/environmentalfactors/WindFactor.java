package work.assignment.environmentalfactors;

public class WindFactor {
	
	public double northComponent;
	public double eastComponent;
	
	public WindFactor(double northComponent, double eastComponent) {
		setNorthComponent(northComponent);
		setEastComponent(eastComponent);
	}

	public double getNorthComponent() {
		return northComponent;
	}

	public void setNorthComponent(double northComponent) {
		this.northComponent = northComponent;
	}

	public double getEastComponent() {
		return eastComponent;
	}

	public void setEastComponent(double eastComponent) {
		this.eastComponent = eastComponent;
	}
	
	public String toString() {
		return "Wind blowing N: " + getNorthComponent() + ", E: " + getEastComponent(); 
	}

}
