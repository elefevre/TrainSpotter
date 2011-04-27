package services;

import static org.apache.commons.lang.StringUtils.*;
import java.util.List;
import models.*;
import org.apache.commons.lang.StringUtils;
import com.google.common.collect.Iterables;

public class TrainStatusNotifier {
	private static final String FROM = "ericlef@gmail.com";
	private final EmailSender emailSender;
	private final TrainInformationPageDao trainInformationPageDao;

	public TrainStatusNotifier(EmailSender emailSender, TrainInformationPageDao trainInformationPageDao) {
		this.emailSender = emailSender;
		this.trainInformationPageDao = trainInformationPageDao;
	}

	public void notify(String to, String trainNumber, TrainInformationPage trainInformationPage) {
		List<TrainInformationPage> allTrains = trainInformationPageDao.find(trainNumber);
		if (allTrains.isEmpty() || haveDetailsChanged(Iterables.getFirst(allTrains, null), trainInformationPage)) {
			trainInformationPageDao.save(trainInformationPage);
			sendEmail(to, trainNumber, trainInformationPage);
		} else {
			emailSender.sendEmail(to, "Train " + trainNumber, "No change", FROM);
		}
	}

	private static boolean haveDetailsChanged(TrainInformationPage previousPage, TrainInformationPage mostRecentPage) {
		if (previousPage.trainStationStatus.size() != mostRecentPage.trainStationStatus.size()) {
			return true;
		}

		for (int i = 0; i < previousPage.trainStationStatus.size(); i++) {
			TrainStationStatus previousStatus = previousPage.trainStationStatus.get(i);
			TrainStationStatus recentStatus = mostRecentPage.trainStationStatus.get(i);

			if (!equalsIgnoreCase(previousStatus.informations, recentStatus.informations) //
					|| !equalsIgnoreCase(previousStatus.trainStation, recentStatus.trainStation) //
					|| !equalsIgnoreCase(previousStatus.time, recentStatus.time)) {
				return true;
			}
		}

		return false;
	}

	private void sendEmail(String to, String trainNumber, TrainInformationPage trainInformationPage) {
		String body = "";
		for (TrainStationStatus status : trainInformationPage.trainStationStatus) {
			body += status.trainStation + ": " + status.time + " (" + status.informations + ")\n";
		}
		body = StringUtils.trim(body);

		emailSender.sendEmail(to, "Train " + trainNumber, body, FROM);
	}
}
