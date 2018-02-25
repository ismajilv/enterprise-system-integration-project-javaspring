package com.example.demo.repositories;

import com.example.demo.utils.Pair;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class MaintenancePlanRepositoryImpl implements CorrectiveRepairsRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Pair<Integer, Long>> findCorrectiveRepairsByYearForPeriod(int startYear, int endYear) {
		return null;
	}

	@Override
	public List<Pair<Integer, Integer>> findCorrectiveRepairCostsByYear(int startYear, int endYear) {
		return null;
	}

}
