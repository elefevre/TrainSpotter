package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import net.ericlefevre.ResultsPageParser;
import net.ericlefevre.StatusPageRetriever;
import net.ericlefevre.URLConnectionProvider;
import org.joda.time.DateTime;

public class TrainSpotter extends Controller {
	public static void displayTrainDetails(@Required String trainNumber) throws MalformedURLException, IOException {
		if (validation.hasErrors()) {
			flash.error("Please enter the train number");
			Application.index();
		}

		DateTime today = new DateTime();
		
		String pageContent = new StatusPageRetriever(new URLConnectionProvider()).downloadStatusPageForTrain(trainNumber, today.getYear(), today.getMonthOfYear(), today.getDayOfMonth());
		
		ResultsPageParser results = new ResultsPageParser(pageContent);
		render(results, pageContent);
	}
}