package com.les.t_shirt_gen.service;

import com.les.t_shirt_gen.dto.SalesDataDto;

import java.time.LocalDate;
import java.util.List;

public interface SalesDataService {
    List<SalesDataDto> getSalesData(LocalDate startDate, LocalDate endDate);
}
