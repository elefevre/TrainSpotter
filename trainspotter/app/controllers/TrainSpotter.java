package controllers;

import static play.data.validation.Validation.*;
import javax.inject.Inject;
import models.TrainInformationPage;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.mvc.Controller;
import services.*;

public class TrainSpotter extends Controller {
	@Inject
	private static StatusPageRetriever statusPageRetriever;
	@Inject
	private static TrainTracker trainTracker;

	public static void displayTrainDetails(@Required String trainNumber) throws Exception {
		if (hasErrors()) {
			flash.error("Please enter the train number");
			Application.index();
		}

		DateTime today = new DateTime();
		TrainInformationPage results = statusPageRetriever.downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());

		render(results);
	}

	public static void trackTrain(@Required String trainNumber) throws Exception {
		if (hasErrors()) {
			flash.error("Please enter the train number");
			Application.index();
		}

		trainTracker.addTrain(trainNumber);

		DateTime today = new DateTime();
		TrainInformationPage results = statusPageRetriever.downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());

		render(results);
	}

}