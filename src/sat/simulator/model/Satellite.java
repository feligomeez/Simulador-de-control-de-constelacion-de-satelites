package sat.simulator.model;

import java.util.UUID;

public class Satellite {
    private final String id;
    private SatelliteStatus status;
    private double batteryLevel; // %
    private double latitude;
    private double longitude;

    public Satellite(double latitude, double longitude) {
        this.id = UUID.randomUUID().toString();
        this.status = SatelliteStatus.ACTIVE;
        this.batteryLevel = 100.0;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public SatelliteStatus getStatus() {
        return status;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    
    public void drainBattery(double amount) {
        batteryLevel = Math.max(0, batteryLevel - amount);
        if (batteryLevel < 15.0) {
            status = SatelliteStatus.LOW_POWER;
        }
        if (batteryLevel == 0) {
            status = SatelliteStatus.OFFLINE;
        }
    }

    public void simulateSignalLoss() {
        this.status = SatelliteStatus.LOST_SIGNAL;
    }

    public void reboot() {
        if (batteryLevel > 0) {
            this.status = SatelliteStatus.ACTIVE;
        }
    }

    public void enterSafeMode() {
        if (batteryLevel > 0) {
            this.status = SatelliteStatus.SAFE_MODE;
        }
    }

    public void updatePosition(double latChange, double lonChange) {
        this.latitude += latChange;
        this.longitude += lonChange;
    }

	@Override
	public String toString() {
		return "Satellite ID=" + id + ", STATUS=" + status + ", BATTERYLEVEL=" + batteryLevel + ", COORDINATES= ["
				+ latitude + ", " + longitude + "]";
	}
    
}