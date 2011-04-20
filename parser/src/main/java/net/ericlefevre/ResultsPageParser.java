package net.ericlefevre;

import java.util.regex.*;

public class ResultsPageParser {
	private final String content;

	public ResultsPageParser(String content) {
		this.content = content;
	}

	public String getTrainNumber() {
		Pattern pattern = Pattern.compile("Train \\D*([0-9]*)<");

		String[] splittedPage = content.split("\n");

		String value = null;
		for (String line : splittedPage) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				value = matcher.group(1);
			}
		}

		return value;
	}

	public String getDepartureDate() {
		Pattern pattern = Pattern.compile("\\(le (.*)\\)<");

		String[] splittedPage = content.split("\n");

		String value = null;
		for (String line : splittedPage) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				value = matcher.group(1);
			}
		}

		return value;
	}
}
