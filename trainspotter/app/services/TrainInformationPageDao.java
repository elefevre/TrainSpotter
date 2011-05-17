package services;

import java.util.List;
import javax.persistence.criteria.*;
import models.*;
import play.db.jpa.JPA;

public class TrainInformationPageDao {
	public List<TrainInformationPage> find(String trainNumber) {
		CriteriaBuilder criteriaBuilder = JPA.em().getCriteriaBuilder();
		CriteriaQuery<TrainInformationPage> query = criteriaBuilder.createQuery(TrainInformationPage.class);
		Root<TrainInformationPage> from = query.from(TrainInformationPage.class);
		query.select(from);
		query.where(criteriaBuilder.equal(from.get("trainNumber"), trainNumber));

		return JPA.em().createQuery(query).getResultList();
	}

	public void save(TrainInformationPage trainInformationPage) {
		trainInformationPage.save();
	}

	public List<TrainInformationPage> findAll() {
		CriteriaBuilder criteriaBuilder = JPA.em().getCriteriaBuilder();
		CriteriaQuery<TrainInformationPage> query = criteriaBuilder.createQuery(TrainInformationPage.class);
		Root<TrainInformationPage> from = query.from(TrainInformationPage.class);
		query.select(from);

		return JPA.em().createQuery(query).getResultList();
	}

	@SuppressWarnings("static-access")
	public List<TrainInformationPage> findByUser(User user) {
		return TrainInformationPage.find("byUser", user).fetch();
	}
}
