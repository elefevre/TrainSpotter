package controllers;

import static play.data.validation.Validation.*;
import java.util.List;
import javax.inject.Inject;
import models.TrainInformationPage;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.mvc.Controller;
import services.*;
import controllers.security.Secure;

public class TrainSpotter extends Controller {
	@Inject
	private static StatusPageRetriever statusPageRetriever;
	@Inject
	private static TrainTracker trainTracker;

	public static void displayTrainDetails(@Required String trainNumber) throws Exception {
		checkTrainNumber();

		DateTime today = new DateTime();
		TrainInformationPage results = statusPageRetriever.downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth(), Secure.connected());

		render(results);
	}

	public static void trackTrain(@Required String trainNumber) throws Exception {
		checkTrainNumber();

		trainTracker.addTrain(trainNumber, Secure.connected());

		DateTime today = new DateTime();
		TrainInformationPage results = statusPageRetriever.downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth(), Secure.connected());

		render(results);
	}

	public static void trains() {
		@SuppressWarnings("static-access") List<TrainInformationPage> results = TrainInformationPage.find("byUser", Secure.connected()).fetch();

		render(results);
	}

	private static void checkTrainNumber() {
		if (hasErrors()) {
			flash.error("Merci de fournir un numéro de train");
			Application.index();
		}
	}

}