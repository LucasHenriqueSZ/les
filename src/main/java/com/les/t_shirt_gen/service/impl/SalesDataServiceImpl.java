package com.les.t_shirt_gen.service.impl;

import com.les.t_shirt_gen.dto.SalesDataDto;
import com.les.t_shirt_gen.dto.SalesVolumeDataDto;
import com.les.t_shirt_gen.model.order.Order;
import com.les.t_shirt_gen.model.order.OrderItem;
import com.les.t_shirt_gen.repository.OrderRepository;
import com.les.t_shirt_gen.service.SalesDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SalesDataServiceImpl implements SalesDataService {

    private final OrderRepository orderRepository;

    public List<SalesDataDto> getSalesData(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.atTime(23, 59));

        Map<String, Map<String, Integer>> productSalesMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                String productName = item.getProduct().getName();
                String orderDate = order.getCreatedAt().toLocalDate().format(DateTimeFormatter.ISO_DATE);

                productSalesMap
                        .computeIfAbsent(productName, k -> new HashMap<>())
                        .merge(orderDate, item.getQuantity(), Integer::sum);
            }
        }

        List<SalesDataDto> salesDataList = new ArrayList<>();

        for (Map.Entry<String, Map<String, Integer>> productEntry : productSalesMap.entrySet()) {
            SalesDataDto salesDataResponse = new SalesDataDto();
            salesDataResponse.setProduct(productEntry.getKey());

            List<SalesVolumeDataDto> salesVolumeDataList = productEntry.getValue().entrySet().stream()
                    .map(entry -> {
                        SalesVolumeDataDto salesVolumeData = new SalesVolumeDataDto();
                        salesVolumeData.setDate(entry.getKey());
                        salesVolumeData.setVolume(entry.getValue());
                        return salesVolumeData;
                    })
                    .collect(Collectors.toList());

            salesDataResponse.setData(salesVolumeDataList);
            salesDataList.add(salesDataResponse);
        }

        return salesDataList;
    }
}
