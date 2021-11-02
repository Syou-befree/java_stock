package com.sho.stock.repository;

import com.sho.stock.model.DailyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyDataRepository extends JpaRepository<DailyData, Long> {
}
