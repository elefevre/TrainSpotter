package jobs;

import models.TrainInformationPage;
import services.*;

public class CheckTrainStatusService {
	private final TrainInformationPageDao trainInformationPageDao;
	private final StatusPageRetriever statusPageRetriever;
	private final TrainStatusNotifier trainStatusNotifier;

	public CheckTrainStatusService(TrainInformationPageDao trainInformationPageDao, StatusPageRetriever statusPageRetriever, TrainStatusNotifier trainStatusNotifier) {
		this.trainInformationPageDao = trainInformationPageDao;
		this.statusPageRetriever = statusPageRetriever;
		this.trainStatusNotifier = trainStatusNotifier;
	}

	public void doJob() throws Exception {
		for (TrainInformationPage page : trainInformationPageDao.findAll()) {
			TrainInformationPage recentPage = statusPageRetriever.downloadStatusPageForTrain(page.trainNumber, page.user);
			trainStatusNotifier.notify(page.user.email, page.trainNumber, recentPage);
		}
	}
}
