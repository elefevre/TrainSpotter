package services;

import models.*;
import org.apache.commons.lang.StringUtils;

public class TrainStatusNotifier {
	private static final String FROM = "ericlef@gmail.com";
	private final EmailSender emailSender;

	public TrainStatusNotifier(EmailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void notify(String to, String trainNumber, TrainInformationPage trainInformationPage) {
		String body = "";
		for (TrainStationStatus status : trainInformationPage.trainStationStatus) {
			body += status.trainStation + ": " + status.time + " (" + status.informations + ")\n";
		}
		body = StringUtils.trim(body);

		emailSender.sendEmail(to, "Train " + trainNumber, body, FROM);
	}
}
