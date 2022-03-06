package trip_planner.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.nashorn.internal.objects.annotations.Setter;

public class Trucker implements Serializable{

	private static final long serialVersionUID = -1149243533903055911L;
	
	private Integer tripId;
	private Double startLatitude;
	private Double startLongitude;
	
	private LocalDateTime startTime;
	
	private LocalDateTime maxDestTime;

	public Integer getTripId() {
		return tripId;
	}

	@Setter
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public Double getStartLatitude() {
		return startLatitude;
	}

	@Setter
	public void setStartLatitude(Double startLatitude) {
		this.startLatitude = startLatitude;
	}

	public Double getStartLongitude() {
		return startLongitude;
	}

	@Setter
	public void setStartLongitude(Double startLongitude) {
		this.startLongitude = startLongitude;
	}

	
	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		LocalDateTime dateTime = LocalDateTime.parse(startTime, formatter);

		this.startTime = dateTime;
	}

	public LocalDateTime getMaxDestTime() {
		return maxDestTime;
	}

	public void setMaxDestTime(String maxDestTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		LocalDateTime dateTime = LocalDateTime.parse(maxDestTime, formatter);
		this.maxDestTime = dateTime;
	}

	@Override
	public String toString() {
		return "Trucker [tripId=" + tripId + ", startLatitude=" + startLatitude + ", startLongitude=" + startLongitude
				+ ", startTime=" + startTime + ", maxDestTime=" + maxDestTime + "]";
	}
	

}
