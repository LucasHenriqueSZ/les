package com.les.t_shirt_gen.controllers.admin;

import com.les.t_shirt_gen.dto.SalesDataDto;
import com.les.t_shirt_gen.service.SalesDataService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/analysis")
public class AnalysisController {

    private SalesDataService salesDataService;

    @GetMapping
    public ModelAndView shoppingCart() {
        return new ModelAndView("private/pages/analysis");
    }

    @GetMapping("/api")
    public ResponseEntity<List<SalesDataDto>> apiShoppingCart(
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate == null) {
            startDate = LocalDate.now().withDayOfMonth(1);
        }

        if (endDate == null) {
            endDate = LocalDate.now();
        }

        List<SalesDataDto> salesData = salesDataService.getSalesData(startDate, endDate);

        return ResponseEntity.ok(salesData);
    }

}
