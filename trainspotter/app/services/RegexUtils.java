package services;

import java.util.regex.*;

public class RegexUtils {

	public static String extractFirstMatchInMultiLines(String regex, String content) {
		Pattern pattern = Pattern.compile(regex);

		String[] splittedPage = content.split("\n");

		for (String line : splittedPage) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				return matcher.group(1);
			}
		}
		return null;
	}

}
