package models;

import java.util.List;

public class TrainInformationPage {
	private final String trainNumber;
	private final String theoreticalDepartureDay;
	private final List<TrainStationStatus> trainStationStatus;

	public TrainInformationPage(String trainNumber, String theoreticalDepartureDay, List<TrainStationStatus> trainStationStatus) {
		this.trainNumber = trainNumber;
		this.theoreticalDepartureDay = theoreticalDepartureDay;
		this.trainStationStatus = trainStationStatus;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public String getTheoreticalDepartureDay() {
		return theoreticalDepartureDay;
	}

	public List<TrainStationStatus> getTrainStationStatus() {
		return trainStationStatus;
	}
}
