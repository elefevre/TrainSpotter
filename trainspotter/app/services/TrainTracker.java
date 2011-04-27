package services;

import java.io.IOException;
import java.net.MalformedURLException;
import models.TrainInformationPage;
import org.joda.time.DateTime;

public class TrainTracker {
	private final TrainInformationPageDao trainInformationPageDao;
	private final StatusPageRetriever statusPageRetriever;
	private final TimeManager timeManager;

	public TrainTracker(TrainInformationPageDao trainInformationPageDao, StatusPageRetriever statusPageRetriever, TimeManager timeManager) {
		this.trainInformationPageDao = trainInformationPageDao;
		this.statusPageRetriever = statusPageRetriever;
		this.timeManager = timeManager;
	}

	public void addTrain(String trainNumber) throws MalformedURLException, IOException {
		DateTime currentTime = timeManager.getCurrentTime();
		int year = currentTime.getYear();
		int month = currentTime.getMonthOfYear();
		int day = currentTime.getDayOfMonth();

		TrainInformationPage page = statusPageRetriever.downloadStatusPageForTrain(trainNumber, year, month, day);

		trainInformationPageDao.save(page);
	}
}
