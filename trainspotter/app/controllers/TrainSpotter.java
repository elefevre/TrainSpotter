package controllers;

import static play.data.validation.Validation.*;
import java.io.IOException;
import java.net.MalformedURLException;
import models.*;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.mvc.Controller;

public class TrainSpotter extends Controller {
	public static void displayTrainDetails(@Required String trainNumber) throws MalformedURLException, IOException {
		if (hasErrors()) {
			flash.error("Please enter the train number");
			Application.index();
		}

		DateTime today = new DateTime();

		TrainInformationPage results = getStatusPageRetriever().downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());

		render(results);
	}

	private static StatusPageRetriever getStatusPageRetriever() {
		return new StatusPageRetriever(new URLConnectionProvider(), new ResultsPageParser());
	}
}