package controllers;

import static play.data.validation.Validation.*;
import java.io.IOException;
import java.net.MalformedURLException;
import models.TrainInformationPage;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.mvc.Controller;
import services.*;

public class TrainSpotter extends Controller {
	private static final String TO = "ericlef@gmail.com";

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
		return new TrainStatusNotifier(getEmailSender());
	}

	private static EmailSender getEmailSender() {
		return new EmailSender(new SystemManager(), new AmazonSimpleEmailServiceProvider());
	}

	private static StatusPageRetriever getStatusPageRetriever() {
		return new StatusPageRetriever(new URLConnectionProvider(), new ResultsPageParser());
	}
}