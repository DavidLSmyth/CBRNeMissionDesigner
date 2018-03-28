package agent.vehicle.uav;


public class UAVTelemetryMessage {
	
	    private UAVTelemetry data;

	    private String success;

	    public UAVTelemetry getData ()
	    {
	        return data;
	    }

	    public void setData (UAVTelemetry data)
	    {
	        this.data = data;
	    }

	    public String getSuccess ()
	    {
	        return success;
	    }

	    public void setSuccess (String success)
	    {
	        this.success = success;
	    }

	    @Override
	    public String toString()
	    {
	        return "UAVTelemetryMessage [data = "+data+", success = "+success+"]";
	    }
}
