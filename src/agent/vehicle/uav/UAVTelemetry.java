package agent.vehicle.uav;

import agent.vehicle.Telemetry;

public class UAVTelemetry implements Telemetry
{
    private String gps_long;

    private String aim;

    private String speed;

    private String euler_pitch;

    private String lastUpdate;

    private String gps_lat;

    private String battery;

    private String euler_roll;

    private String euler_yaw;

    private String gps_alt;

    private String landed;

    public String getGps_long ()
    {
        return gps_long;
    }

    public void setGps_long (String gps_long)
    {
        this.gps_long = gps_long;
    }

    public String getAim ()
    {
        return aim;
    }

    public void setAim (String aim)
    {
        this.aim = aim;
    }

    public String getSpeed ()
    {
        return speed;
    }

    public void setSpeed (String speed)
    {
        this.speed = speed;
    }

    public String getEuler_pitch ()
    {
        return euler_pitch;
    }

    public void setEuler_pitch (String euler_pitch)
    {
        this.euler_pitch = euler_pitch;
    }

    public String getLastUpdate ()
    {
        return lastUpdate;
    }

    public void setLastUpdate (String lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }

    public String getGps_lat ()
    {
        return gps_lat;
    }

    public void setGps_lat (String gps_lat)
    {
        this.gps_lat = gps_lat;
    }

    public String getBattery ()
    {
        return battery;
    }

    public void setBattery (String battery)
    {
        this.battery = battery;
    }

    public String getEuler_roll ()
    {
        return euler_roll;
    }

    public void setEuler_roll (String euler_roll)
    {
        this.euler_roll = euler_roll;
    }

    public String getEuler_yaw ()
    {
        return euler_yaw;
    }

    public void setEuler_yaw (String euler_yaw)
    {
        this.euler_yaw = euler_yaw;
    }

    public String getGps_alt ()
    {
        return gps_alt;
    }

    public void setGps_alt (String gps_alt)
    {
        this.gps_alt = gps_alt;
    }

    public String getLanded ()
    {
        return landed;
    }

    public void setLanded (String landed)
    {
        this.landed = landed;
    }

    @Override
    public String toString()
    {
        return "UAVTelemetryData [gps_long = "+gps_long+", aim = "+aim+", speed = "+speed+", euler_pitch = "+euler_pitch+", lastUpdate = "+lastUpdate+", gps_lat = "+gps_lat+", battery = "+battery+", euler_roll = "+euler_roll+", euler_yaw = "+euler_yaw+", gps_alt = "+gps_alt+", landed = "+landed+"]";
    }
}
			
			