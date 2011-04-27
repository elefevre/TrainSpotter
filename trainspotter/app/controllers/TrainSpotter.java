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
	private static StatusPageRetriever statusPageRetriever;
	@Inject
	private static TrainStatusNotifier trainStatusNotifier;

	public static void displayTrainDetails(@Required String trainNumber) throws MalformedURLException, IOException {
		if (hasErrors()) {
			flash.error("Please enter the train number");
			Application.index();
		}

		DateTime today = new DateTime();

		TrainInformationPage results = statusPageRetriever.downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());
		trainStatusNotifier.notify(TO, trainNumber, results);

		render(results);
	}

}