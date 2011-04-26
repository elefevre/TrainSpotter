package controllers;

import static play.data.validation.Validation.*;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.inject.Inject;
import models.TrainInformationPage;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.mvc.Controller;
import services.*;

public class TrainSpotter extends Controller {
	private static final String TO = "ericlef@gmail.com";
	@Inject
	static SystemManager systemManager;
	@Inject
	static TrainInformationPageDao trainInformationPageDao;
	@Inject
	static AmazonSimpleEmailServiceProvider amazonSimpleEmailServiceProvider;
	@Inject
	static URLConnectionProvider urlConnectionProvider;
	@Inject
	static ResultsPageParser resultsPageParser;
	@Inject
	static StatusPageRetriever statusPageRetriever;

	public static void displayTrainDetails(@Required String trainNumber) throws MalformedURLException, IOException {
		if (hasErrors()) {
			flash.error("Please enter the train number");
			Application.index();
		}

		DateTime today = new DateTime();

		TrainInformationPage results = getStatusPageRetriever().downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());
		getTrainStatusNotifier().notify(TO, trainNumber, results);

		render(results);
	}

	private static TrainStatusNotifier getTrainStatusNotifier() {
		return new TrainStatusNotifier(getEmailSender(), trainInformationPageDao);
	}

	private static EmailSender getEmailSender() {
		return new EmailSender(systemManager, amazonSimpleEmailServiceProvider);
	}

	private static StatusPageRetriever getStatusPageRetriever() {
		return new StatusPageRetriever(urlConnectionProvider, resultsPageParser);
	}
}