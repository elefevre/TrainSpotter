package services;

import static net.htmlparser.jericho.HTMLElementName.*;
import static org.apache.commons.lang.StringUtils.*;
import java.util.*;
import java.util.regex.*;

import models.TrainInformationPage;
import models.TrainStationStatus;
import net.htmlparser.jericho.*;
import org.apache.commons.lang.StringUtils;
import com.google.common.collect.*;

public class ResultsPageParser {

	public TrainInformationPage toTrainInformationPage(String content) {
		return new TrainInformationPage(getTrainNumber(content), getDepartureDate(content), getStationDetails(content));
	}

	private String getTrainNumber(String content) {
		Source source = new Source(content);
		Element firstH2 = Iterables.getFirst(source.getAllElements(H2), null);
		Segment contentOfH2 = firstH2.getChildElements().get(0).getContent();

		return StringUtils.substringBetween(contentOfH2.toString(), "Train N°", "<");
	}

	private String getDepartureDate(String content) {
		return extract("\\(le (.*)\\)<", content);
	}

	private String extract(String regex, String content) {
		Pattern pattern = Pattern.compile(regex);

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

	private List<TrainStationStatus> getStationDetails(String content) {
		Source source = new Source(content);
		Iterator<Element> tr = source.getAllElements(TR).iterator();
		tr.next(); // column titles

		List<TrainStationStatus> details = Lists.newArrayList();
		while (tr.hasNext()) {
			List<Element> element = tr.next().getChildElements(); // TD
			String trainStation = asString(element.get(0).getContent());
			String time = asString(element.get(1).getContent());
			String status = asString(element.get(2).getContent());
			TrainStationStatus trainStationStatus = new TrainStationStatus(trainStation, time, status);

			details.add(trainStationStatus);
		}

		return details;
	}

	private static String asString(Segment segment) {
		String text = trimToEmpty(segment.toString());
		return StringUtils.replaceEach(text, new String[] { "&nbsp;" }, new String[] { " " });
	}

}
