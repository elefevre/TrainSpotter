package services;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;
import models.TrainInformationPage;
import org.apache.commons.io.IOUtils;
import com.google.common.base.Joiner;

public class StatusPageRetriever {
	private final URLConnectionProvider urlConnectionProvider;
	private final ResultsPageParser resultsPageParser;

	public StatusPageRetriever(URLConnectionProvider urlConnectionProvider, ResultsPageParser resultsPageParser) {
		this.urlConnectionProvider = urlConnectionProvider;
		this.resultsPageParser = resultsPageParser;
	}

	public TrainInformationPage downloadStatusPageForTrain(String trainNumber, int year, int month, int day) throws MalformedURLException, IOException {
		BufferedReader inputStreamForUrl = urlConnectionProvider.getInputStreamForUrl(createUrl(trainNumber, year, month, day));

		String results = toMultiLineString(inputStreamForUrl);

		return resultsPageParser.toTrainInformationPage(results);
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
