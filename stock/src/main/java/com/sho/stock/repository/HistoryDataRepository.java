package com.sho.stock.repository;

import com.sho.stock.model.HistoryData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryDataRepository extends JpaRepository<HistoryData, Long> {
  
  List<HistoryData> findByCodeOrderByDateDesc(String code);
}
