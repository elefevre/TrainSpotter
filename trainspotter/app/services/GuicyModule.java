package services;

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
}