package com.example.demo.repositories;

import com.example.demo.models.*;
import com.example.demo.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Repository
@Transactional(readOnly = true)
public class MaintenancePlanRepositoryImpl implements CorrectiveRepairsRepository {


	@Autowired
	EntityManager entityManager;

	@Override
	public List<Pair<Integer, Long>> findCorrectiveRepairsByYearForPeriod(int startYear, int endYear) {
		return entityManager.createQuery("select new com.example.demo.utils.Pair(mp.yearOfAction, count(mt)) from MaintenancePlan mp JOIN mp.tasks mt  " +
                "" + "where mp.yearOfAction >= ?1 and mp.yearOfAction <= ?2 and mt.typeOfWork = com.example.demo.models.TypeOfWork.CORRECTIVE group by mp.yearOfAction")
                .setParameter(1, startYear)
                .setParameter(2, endYear)
                .getResultList();

	}
	@Override
	public List<Pair<Integer, BigDecimal>> findCorrectiveRepairCostsByYear(int startYear, int endYear) {
		return entityManager.createQuery("select new com.example.demo.utils.Pair(mp.yearOfAction, sum(mt.price)) from MaintenancePlan mp JOIN mp.tasks mt  " +
                "" + "where mp.yearOfAction >= ?1 and mp.yearOfAction <= ?2 and mt.typeOfWork = com.example.demo.models.TypeOfWork.CORRECTIVE group by mp.yearOfAction ORDER BY mp.yearOfAction")
                .setParameter(1, startYear)
                .setParameter(2, endYear)
                .getResultList();
	}

}
