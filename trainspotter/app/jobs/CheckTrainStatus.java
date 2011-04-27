package jobs;

import javax.inject.Inject;
import play.jobs.*;

@Every("15mn")
public class CheckTrainStatus extends Job<Object> {
	@Inject
	static CheckTrainStatusService checkTrainStatusService;

	@Override
	public void doJob() throws Exception {
		checkTrainStatusService.doJob();
	}
}
