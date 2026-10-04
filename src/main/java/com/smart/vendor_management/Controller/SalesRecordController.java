package com.smart.vendor_management.Controller;

import com.smart.vendor_management.entity.SalesRecord;
import com.smart.vendor_management.service.SalesRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SalesRecordController {

    private final SalesRecordService salesRecordService;

    public SalesRecordController(SalesRecordService salesRecordService) {
        this.salesRecordService = salesRecordService;
    }

    // RECORD A SALE
    @PostMapping("/{productId}")
    public SalesRecord recordSale(
            @PathVariable Long productId,
            @RequestParam int quantitySold) {

        return salesRecordService.recordSale(productId, quantitySold);
    }

    // GET ALL SALES RECORDS
    @GetMapping
    public List<SalesRecord> getAllSalesRecords() {
        return salesRecordService.getAllSalesRecords();
    }
}