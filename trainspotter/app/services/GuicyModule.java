package services;

import jobs.CheckTrainStatusService;
import com.google.inject.*;

public class GuicyModule extends AbstractModule {
	@Override
	public void configure() {
		// no implementation needed
	}

	@Provides
	public StatusPageRetriever getStatusPageRetriever(URLConnectionProvider urlConnectionProvider, ResultsPageParser resultsPageParser) {
		return new StatusPageRetriever(urlConnectionProvider, resultsPageParser);
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
	public CheckTrainStatusService getCheckTrainStatus(TrainInformationPageDao trainInformationPageDao, StatusPageRetriever statusPageRetriever, TrainStatusNotifier trainStatusNotifier, TimeManager timeManager) {
		return new CheckTrainStatusService(trainInformationPageDao, statusPageRetriever, trainStatusNotifier, timeManager);
	}

	@Provides
	public TrainTracker getTrainTracker(TrainInformationPageDao trainInformationPageDao, StatusPageRetriever statusPageRetriever, TimeManager timeManager) {
		return new TrainTracker(trainInformationPageDao, statusPageRetriever, timeManager);
	}
}