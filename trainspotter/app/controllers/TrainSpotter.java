package controllers;

import static play.data.validation.Validation.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map.Entry;
import java.util.*;
import models.TrainInformationPage;
import org.joda.time.DateTime;
import play.data.validation.Required;
import play.mvc.Controller;
import services.*;

public class TrainSpotter extends Controller {
	private static final String TO = "ericlef@gmail.com";
	private static final String FROM = "ericlef@gmail.com";
	private static final String BODY = "Hello World!";
	private static final String SUBJECT = "Hello World!";

	public static void displayTrainDetails(@Required String trainNumber) throws MalformedURLException, IOException {
		if (hasErrors()) {
			flash.error("Please enter the train number");
			Application.index();
		}

		DateTime today = new DateTime();

		TrainInformationPage results = getStatusPageRetriever().downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());

		render(results);
	}

	public static void displaySystemProperties() {
		Set<Entry<Object, Object>> properties = System.getProperties().entrySet();

		render(properties);
	}

	public static void sendEmail() {
		getEmailSender().sendEmail(TrainSpotter.TO, TrainSpotter.SUBJECT, TrainSpotter.BODY, TrainSpotter.FROM);
	}

	private static EmailSender getEmailSender() {
		SystemManager systemManager = new SystemManager();
		return new EmailSender(systemManager, null);
	}

	private static StatusPageRetriever getStatusPageRetriever() {
		return new StatusPageRetriever(new URLConnectionProvider(), new ResultsPageParser());
	}
}