package models;

import javax.persistence.Entity;
import org.apache.commons.lang.builder.*;
import play.db.jpa.Model;

@Entity
public class TrainStationStatus extends Model {
	public String trainStation;
	public String time;
	public String informations;

	public TrainStationStatus(String trainStation, String time, String informations) {
		this.trainStation = trainStation;
		this.time = time;
		this.informations = informations;
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
