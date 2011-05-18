package services;

import jobs.CheckTrainStatusService;
import com.google.inject.*;

public class GuicyModule extends AbstractModule {
	@Override
	public void configure() {
		// no implementation needed
	}

	@Provides
	public StatusPageRetriever getStatusPageRetriever(URLConnectionProvider urlConnectionProvider, ResultsPageParser resultsPageParser, TimeManager timeManager) {
		return new StatusPageRetriever(urlConnectionProvider, resultsPageParser, timeManager);
	}

	@Provides
	public TrainStatusNotifier getTrainStatusNotifier(EmailSender emailSender, TrainInformationPageDao trainInformationPageDao) {
		return new TrainStatusNotifier(emailSender, trainInformationPageDao);
	}

	@Provides
	public EmailSender getEmailSender(SystemManager systemManager, AmazonSimpleEmailServiceProvider amazonSimpleEmailServiceProvider) {
		return new EmailSender(systemManager, amazonSimpleEmailServiceProvider);
	}

	@Provides
	public CheckTrainStatusService getCheckTrainStatus(TrainInformationPageDao trainInformationPageDao, StatusPageRetriever statusPageRetriever, TrainStatusNotifier trainStatusNotifier) {
		return new CheckTrainStatusService(trainInformationPageDao, statusPageRetriever, trainStatusNotifier);
	}

	@Provides
	public TrainTracker getTrainTracker(TrainInformationPageDao trainInformationPageDao, StatusPageRetriever statusPageRetriever) {
		return new TrainTracker(trainInformationPageDao, statusPageRetriever);
	}
}