package com.example.demo.repositories;

import com.example.demo.utils.Pair;

import java.util.List;

public interface CorrectiveRepairsRepository {

	List<Pair<Integer, Long>> findCorrectiveRepairsByYearForPeriod(int startYear, int endYear);

	List<Pair<Integer, Integer>> findCorrectiveRepairCostsByYear(int startYear, int endYear);


}
