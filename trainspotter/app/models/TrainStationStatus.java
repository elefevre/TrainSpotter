package models;

import org.apache.commons.lang.builder.*;

public class TrainStationStatus {
	private final String trainStation;
	private final String time;
	private final String informations;

	public TrainStationStatus(String trainStation, String time, String informations) {
		this.trainStation = trainStation;
		this.time = time;
		this.informations = informations;
	}

	public String getInformations() {
		return informations;
	}

	public String getTime() {
		return time;
	}

	public String getTrainStation() {
		return trainStation;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object arg0) {
		return EqualsBuilder.reflectionEquals(this, arg0);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
