package services;

import static com.google.common.collect.Lists.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import models.*;
import org.junit.Test;
import play.test.UnitTest;
import com.google.common.collect.Lists;

public class TrainStatusNotifierTest extends UnitTest {
	private final EmailSender mockEmailSender = mock(EmailSender.class);
	private final TrainInformationPageDao mockTrainInformationPageDao = mock(TrainInformationPageDao.class);
	private final TrainStatusNotifier notifier = new TrainStatusNotifier(mockEmailSender, mockTrainInformationPageDao);
	private final User mockUser = mock(User.class);

	@Test
	public void shouldSendAnEmailWhenThisIsTheFirstRequestForStatusForThisTrain() {
		when(mockTrainInformationPageDao.find("7000")).thenReturn(Lists.<TrainInformationPage> newArrayList());

		notifier.notify("user@site.com", "7000", new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure"), new TrainStationStatus("Orléans", "11:00", "A l'heure")), mockUser));

		verify(mockEmailSender).sendEmail("user@site.com", "Train 7000", "Paris-Nord: 10:00 (A l'heure)\nOrléans: 11:00 (A l'heure)", "ericlef@gmail.com");
		verify(mockTrainInformationPageDao).save(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure"), new TrainStationStatus("Orléans", "11:00", "A l'heure")), mockUser));
	}

	@Test
	public void shouldNotSendAnEmailIfTheStatusHasNotChanged() {
		when(mockTrainInformationPageDao.find("7000")).thenReturn(newArrayList(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure"), new TrainStationStatus("Orléans", "11:00", "A l'heure")), mockUser)));

		notifier.notify("user@site.com", "7000", new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure"), new TrainStationStatus("Orléans", "11:00", "A l'heure")), mockUser));

		verify(mockEmailSender).sendEmail("user@site.com", "Train 7000", "No change", "ericlef@gmail.com");
		verify(mockTrainInformationPageDao, never()).save(any(TrainInformationPage.class));
	}

	@Test
	public void shouldSendAnEmailIfThereIsADifferenceNumberOfInformationFields() {
		when(mockTrainInformationPageDao.find("7000")).thenReturn(newArrayList(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure"), new TrainStationStatus("Orléans", "11:00", "A l'heure")), mockUser)));

		notifier.notify("user@site.com", "7000", new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure")), mockUser));

		verify(mockEmailSender).sendEmail("user@site.com", "Train 7000", "Paris-Nord: 10:00 (A l'heure)", "ericlef@gmail.com");
		verify(mockTrainInformationPageDao).save(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure")), mockUser));
	}

	@Test
	public void shouldSendAnEmailIfTheInformationFieldHasChanged() {
		when(mockTrainInformationPageDao.find("7000")).thenReturn(newArrayList(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure WILL DE DIFFERENT")), mockUser)));

		notifier.notify("user@site.com", "7000", new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure")), mockUser));

		verify(mockEmailSender).sendEmail("user@site.com", "Train 7000", "Paris-Nord: 10:00 (A l'heure)", "ericlef@gmail.com");
		verify(mockTrainInformationPageDao).save(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure")), mockUser));
	}

	@Test
	public void shouldSendAnEmailIfTheTrainStationHasChanged() {
		when(mockTrainInformationPageDao.find("7000")).thenReturn(newArrayList(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord WILL DE DIFFERENT", "10:00", "A l'heure")), mockUser)));

		notifier.notify("user@site.com", "7000", new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure")), mockUser));

		verify(mockEmailSender).sendEmail("user@site.com", "Train 7000", "Paris-Nord: 10:00 (A l'heure)", "ericlef@gmail.com");
		verify(mockTrainInformationPageDao).save(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure")), mockUser));
	}

	@Test
	public void shouldSendAnEmailIfTheCallingTimeAtATrainStationHasChanged() {
		when(mockTrainInformationPageDao.find("7000")).thenReturn(newArrayList(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00 WILL DE DIFFERENT", "A l'heure")), mockUser)));

		notifier.notify("user@site.com", "7000", new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure")), mockUser));

		verify(mockEmailSender).sendEmail("user@site.com", "Train 7000", "Paris-Nord: 10:00 (A l'heure)", "ericlef@gmail.com");
		verify(mockTrainInformationPageDao).save(new TrainInformationPage("7000", "20/10/2011", newArrayList(new TrainStationStatus("Paris-Nord", "10:00", "A l'heure")), mockUser));
	}
}
