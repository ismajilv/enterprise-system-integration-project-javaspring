package com.example.demo.inventory.domain.repository;

import com.example.demo.utils.Pair;

import java.math.BigDecimal;
import java.util.List;

public interface MaintenancePlanRepositoryCustomQueries {

	List<Pair<Integer, Long>> findCorrectiveRepairsByYearForPeriod(int startYear, int endYear);

	List<Pair<Integer, BigDecimal>> findCorrectiveRepairCostsByYear(int startYear, int endYear);


}
