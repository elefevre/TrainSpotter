package services;

import java.io.IOException;
import java.net.MalformedURLException;
import models.*;

public class TrainTracker {
	private final TrainInformationPageDao trainInformationPageDao;
	private final StatusPageRetriever statusPageRetriever;

	public TrainTracker(TrainInformationPageDao trainInformationPageDao, StatusPageRetriever statusPageRetriever) {
		this.trainInformationPageDao = trainInformationPageDao;
		this.statusPageRetriever = statusPageRetriever;
	}

	public void addTrain(String trainNumber, User user) throws MalformedURLException, IOException {
		TrainInformationPage page = statusPageRetriever.downloadStatusPageForTrain(trainNumber, user);

		trainInformationPageDao.save(page);
	}
}
