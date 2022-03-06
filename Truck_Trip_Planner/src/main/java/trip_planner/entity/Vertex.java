package trip_planner.entity;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Vertex implements Serializable{

	private static final long serialVersionUID = 7256684752256780461L;
	
	private final Double latitude;
    private final Double longitude;

    
    public Vertex(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private static double dist(Vertex v1, Vertex v2){
        /*
        const R = 6371000; // metres
    const φ1 = lat1 * Math.PI/180; // φ, λ in radians
    const φ2 = lat2 * Math.PI/180;
    const Δφ = (lat2-lat1) * Math.PI/180;
    const Δλ = (lon2-lon1) * Math.PI/180;
    const a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
          Math.cos(φ1) * Math.cos(φ2) *
          Math.sin(Δλ/2) * Math.sin(Δλ/2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    const d = R * c; // in metres
         */
        double R = 6371000;
        double phi1 = v1.latitude * Math.PI / 180;
        double phi2 = v2.latitude * Math.PI / 180;
        double diff_phi = phi2 - phi1;
        double diff_lambda = (v2.longitude - v1.longitude) * Math.PI / 180;
        double a = Math.sin(diff_phi / 2) * Math.sin(diff_phi / 2) +
                Math.cos(phi1) * Math.cos(phi2) * Math.sin(diff_lambda / 2) * Math.sin(diff_lambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d / 1609; // convert from meters to miles
    }

    public static double fuelCost(Vertex v1, Vertex v2){
        return 0.4 * dist(v1, v2);
    }

    public static double hours(Vertex v1, Vertex v2){
        return dist(v1, v2) / 55;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Vertex){
            Vertex v = (Vertex) o;
            return v.longitude == this.longitude && v.latitude == this.latitude;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(latitude, longitude);
    }

	@Override
	public String toString() {
		return "Vertex [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
    
}
