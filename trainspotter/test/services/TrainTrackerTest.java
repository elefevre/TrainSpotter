package services;

import static org.mockito.Mockito.*;
import java.io.IOException;
import java.net.MalformedURLException;
import models.*;
import org.joda.time.*;
import org.junit.Test;
import play.test.UnitTest;

public class TrainTrackerTest extends UnitTest {
	private final TrainInformationPageDao mockTrainInformationPageDao = mock(TrainInformationPageDao.class);
	private final StatusPageRetriever mockStatusPageRetriever = mock(StatusPageRetriever.class);
	private final TimeManager mockTimeManager = mock(TimeManager.class);
	private final User mockUser = mock(User.class);

	private final TrainTracker tracker = new TrainTracker(mockTrainInformationPageDao, mockStatusPageRetriever);

	@Test
	public void shouldAddTrainToListOfTrackedTrains() throws MalformedURLException, IOException {
		TrainInformationPage mockTrainInformationPage = mock(TrainInformationPage.class);
		when(mockTimeManager.getCurrentTime()).thenReturn(new DateTime(2011, 01, 01, 2, 0, 0, 0, DateTimeZone.UTC));
		when(mockStatusPageRetriever.downloadStatusPageForTrain("7000", mockUser)).thenReturn(mockTrainInformationPage);

		tracker.addTrain("7000", mockUser);

		verify(mockTrainInformationPageDao).save(mockTrainInformationPage);
	}
}
