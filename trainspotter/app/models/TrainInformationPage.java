package models;

import java.util.List;
import javax.persistence.*;
import org.apache.commons.lang.builder.*;
import play.db.jpa.Model;

@Entity
public class TrainInformationPage extends Model {
	public String trainNumber;
	public String theoreticalDepartureDay;
	@OneToMany
	public List<TrainStationStatus> trainStationStatus;

	public TrainInformationPage(String trainNumber, String theoreticalDepartureDay, List<TrainStationStatus> trainStationStatus) {
		this.trainNumber = trainNumber;
		this.theoreticalDepartureDay = theoreticalDepartureDay;
		this.trainStationStatus = trainStationStatus;
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
