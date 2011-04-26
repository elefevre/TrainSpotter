package services;

import static com.google.common.collect.Lists.*;
import static org.mockito.Mockito.*;
import models.*;
import org.junit.Test;
import play.test.UnitTest;

public class TrainStatusNotifierTest extends UnitTest {
	private final EmailSender mockEmailSender = mock(EmailSender.class);

	@Test
	public void shouldSendAnEmailWithTheStatusOfTheTrain() {
		new TrainStatusNotifier(mockEmailSender).notify("user@site.com", "7000", new TrainInformationPage(null, "20/10/2011", //
				newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure"), new TrainStationStatus("Orléans", "11:00", "A l'heure"))));

		verify(mockEmailSender).sendEmail("user@site.com", "Train 7000", "Paris-Nord: 10:00 (A l'heure)\nOrléans: 11:00 (A l'heure)", "ericlef@gmail.com");
	}
}
