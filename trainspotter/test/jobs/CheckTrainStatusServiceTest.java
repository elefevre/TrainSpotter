package jobs;

import static org.mockito.Mockito.*;
import models.*;
import org.joda.time.*;
import org.junit.Test;
import play.test.UnitTest;
import services.*;
import com.google.inject.internal.Lists;

public class CheckTrainStatusServiceTest extends UnitTest {
	private final TrainInformationPageDao mockTrainInformationPageDao = mock(TrainInformationPageDao.class);
	private final TimeManager mockTimeManager = mock(TimeManager.class);
	private final StatusPageRetriever mockStatusPageRetriever = mock(StatusPageRetriever.class);
	private final TrainStatusNotifier mockTrainStatusNotifier = mock(TrainStatusNotifier.class);
	private final User mockUser = mock(User.class);

	@Test
	public void shouldCheckStatusOfTrain() throws Exception {
		TrainInformationPage mockRecentTrainInformationPageFor7000 = mock(TrainInformationPage.class);
		TrainInformationPage mockRecentTrainInformationPageFor8000 = mock(TrainInformationPage.class);
		when(mockTrainInformationPageDao.findAll()).thenReturn(Lists.<TrainInformationPage> newArrayList(//
				new TrainInformationPage("7000", "20/10/2011", Lists.<TrainStationStatus> newArrayList(), mockUser),//
				new TrainInformationPage("8000", "22/10/2011", Lists.<TrainStationStatus> newArrayList(), mockUser)));
		when(mockStatusPageRetriever.downloadStatusPageForTrain("7000", 2011, 1, 1, mockUser)).thenReturn(mockRecentTrainInformationPageFor7000);
		when(mockStatusPageRetriever.downloadStatusPageForTrain("8000", 2011, 1, 1, mockUser)).thenReturn(mockRecentTrainInformationPageFor8000);
		when(mockTimeManager.getCurrentTime()).thenReturn(new DateTime(2011, 01, 01, 2, 0, 0, 0, DateTimeZone.UTC));

		new CheckTrainStatusService(mockTrainInformationPageDao, mockStatusPageRetriever, mockTrainStatusNotifier, mockTimeManager).doJob();

		verify(mockTrainStatusNotifier).notify("ericlef@gmail.com", "7000", mockRecentTrainInformationPageFor7000);
		verify(mockTrainStatusNotifier).notify("ericlef@gmail.com", "8000", mockRecentTrainInformationPageFor8000);
	}

}
