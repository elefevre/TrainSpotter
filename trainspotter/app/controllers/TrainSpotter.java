package controllers;

import static play.data.validation.Validation.*;
import java.util.List;
import javax.inject.Inject;
import models.TrainInformationPage;
import play.data.validation.Required;
import play.mvc.Controller;
import services.*;
import controllers.security.Secure;

public class TrainSpotter extends Controller {
	@Inject
	private static StatusPageRetriever statusPageRetriever;
	@Inject
	private static TrainTracker trainTracker;
	@Inject
	private static TrainInformationPageDao trainInformationPageDao;

	public static void displayTrainDetails(@Required String trainNumber) throws Exception {
		checkTrainNumber();

		TrainInformationPage results = statusPageRetriever.downloadStatusPageForTrain(trainNumber, Secure.connected());
		TrainInformationPage pageForUser = trainInformationPageDao.findByTrainNumberAndUser(Secure.connected(), trainNumber);
		boolean alreadyTracked = pageForUser != null;

		render(results, alreadyTracked);
	}

	public static void trackTrain(@Required String trainNumber) throws Exception {
		checkTrainNumber();

		trainTracker.addTrain(trainNumber, Secure.connected());

		displayTrainDetails(trainNumber);
	}

	public static void unTrackTrain(@Required String trainNumber) throws Exception {
		checkTrainNumber();

		trainInformationPageDao.delete(Secure.connected(), trainNumber);

		displayTrainDetails(trainNumber);
	}

	public static void trains() {
		List<TrainInformationPage> results = trainInformationPageDao.findByUser(Secure.connected());

		render(results);
	}

	private static void checkTrainNumber() {
		if (hasErrors()) {
			flash.error("Merci de fournir un numéro de train");
			Application.index();
		}
	}

}