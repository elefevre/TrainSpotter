package jobs;

import models.TrainInformationPage;
import org.joda.time.DateTime;
import services.*;

public class CheckTrainStatusService {
	private final TrainInformationPageDao trainInformationPageDao;
	private final StatusPageRetriever statusPageRetriever;
	private final TrainStatusNotifier trainStatusNotifier;
	private final TimeManager timeManager;

	public CheckTrainStatusService(TrainInformationPageDao trainInformationPageDao, StatusPageRetriever statusPageRetriever, TrainStatusNotifier trainStatusNotifier, TimeManager timeManager) {
		this.trainInformationPageDao = trainInformationPageDao;
		this.statusPageRetriever = statusPageRetriever;
		this.trainStatusNotifier = trainStatusNotifier;
		this.timeManager = timeManager;
	}

	public void doJob() throws Exception {
		DateTime currentTime = timeManager.getCurrentTime();
		int year = currentTime.getYear();
		int month = currentTime.getMonthOfYear();
		int day = currentTime.getDayOfMonth();

		for (TrainInformationPage page : trainInformationPageDao.findAll()) {
			TrainInformationPage recentPage = statusPageRetriever.downloadStatusPageForTrain(page.trainNumber, year, month, day);
			trainStatusNotifier.notify("ericlef@gmail.com", page.trainNumber, recentPage);
		}
	}
}
