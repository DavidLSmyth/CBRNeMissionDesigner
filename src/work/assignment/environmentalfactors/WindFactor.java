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
		String n_s = "N";
		String e_w = "E";
		if(getNorthComponent() < 0) {
			n_s = "S";
		}
		if(getEastComponent() < 0) {
			e_w = "W";
		}
		return "Wind blowing " + n_s + ": " + Math.abs(getNorthComponent()) + ", " + e_w + ": " + Math.abs(getEastComponent()); 
	}

}
