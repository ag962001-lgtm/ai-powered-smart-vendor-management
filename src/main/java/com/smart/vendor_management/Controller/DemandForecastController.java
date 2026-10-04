package com.smart.vendor_management.Controller;

import com.smart.vendor_management.dto.DemandForecastResponse;
import com.smart.vendor_management.service.DemandForecastService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forecast")
public class DemandForecastController {

    private final DemandForecastService demandForecastService;

    public DemandForecastController(
            DemandForecastService demandForecastService) {

        this.demandForecastService = demandForecastService;
    }

    @GetMapping("/{productId}")
    public DemandForecastResponse forecastDemand(
            @PathVariable Long productId) {

        return demandForecastService.forecastDemand(productId);
    }
}