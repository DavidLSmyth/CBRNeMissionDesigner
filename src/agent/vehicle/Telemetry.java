package agent.vehicle;

public interface Telemetry {

	public String getGps_long();

	public void setGps_long(String gps_long);

	public String getSpeed();

	public void setSpeed(String speed);

	public String getLastUpdate();

	public void setLastUpdate(String lastUpdate);

	public String getGps_lat();

	public void setGps_lat(String gps_lat);

	public String getBattery();

	public void setBattery(String battery);

	public String getGps_alt();

	public void setGps_alt(String gps_alt);

}
