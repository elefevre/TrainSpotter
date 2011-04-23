package services;

import java.io.*;
import java.net.MalformedURLException;

import models.TrainInformationPage;

public class StatusPageRetriever {
	private final URLConnectionProvider urlConnectionProvider;
	private final ResultsPageParser resultsPageParser;

	public StatusPageRetriever(URLConnectionProvider urlConnectionProvider, ResultsPageParser resultsPageParser) {
		this.urlConnectionProvider = urlConnectionProvider;
		this.resultsPageParser = resultsPageParser;
	}

	public TrainInformationPage downloadStatusPageForTrain(String trainNumber, int year, int month, int day) throws MalformedURLException, IOException {
		String inputLine;
		String result = "";

		BufferedReader inputStreamForUrl = urlConnectionProvider.getInputStreamForUrl(createUrl(trainNumber, year, month, day));
		while ((inputLine = inputStreamForUrl.readLine()) != null) {
			result += inputLine + "\n";
		}
		return resultsPageParser.toTrainInformationPage(result);
	}

	private String createUrl(String trainNumber, int year, int month, int day) {
		String monthAsString = (month < 10 ? "0" : "") + month;
		String dayAsString = (day < 10 ? "0" : "") + day;
		return "http://www.infolignes.com/recherche.php?date_num_train=" + year + "|" + monthAsString + "|" + dayAsString + "&num_train=" + trainNumber;
	}

}