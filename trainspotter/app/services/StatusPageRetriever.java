package services;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;
import models.*;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import com.google.common.base.Joiner;

public class StatusPageRetriever {
	private final URLConnectionProvider urlConnectionProvider;
	private final ResultsPageParser resultsPageParser;
	private final TimeManager timeManager;

	public StatusPageRetriever(URLConnectionProvider urlConnectionProvider, ResultsPageParser resultsPageParser, TimeManager timeManager) {
		this.urlConnectionProvider = urlConnectionProvider;
		this.resultsPageParser = resultsPageParser;
		this.timeManager = timeManager;
	}

	public TrainInformationPage downloadStatusPageForTrain(String trainNumber, User user) throws MalformedURLException, IOException {
		DateTime dateTime = timeManager.getCurrentTime();
		BufferedReader inputStreamForUrl = urlConnectionProvider.getInputStreamForUrl(createUrl(trainNumber, dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth()));

		String results = toMultiLineString(inputStreamForUrl);

		return resultsPageParser.toTrainInformationPage(results, user);
	}

	private static String toMultiLineString(BufferedReader inputStreamForUrl) throws IOException {
		List<String> readLines = IOUtils.readLines(inputStreamForUrl);

		return Joiner.on('\n').join(readLines);
	}

	private static String createUrl(String trainNumber, int year, int month, int day) {
		String monthAsString = (month < 10 ? "0" : "") + month;
		String dayAsString = (day < 10 ? "0" : "") + day;

		return "http://www.infolignes.com/recherche.php?date_num_train=" + year + "|" + monthAsString + "|" + dayAsString + "&num_train=" + trainNumber;
	}

}
