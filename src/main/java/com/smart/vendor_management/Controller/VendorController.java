package com.smart.vendor_management.Controller;

import com.smart.vendor_management.entity.Vendor;
import com.smart.vendor_management.service.VendorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    public Vendor addVendor(@RequestBody Vendor vendor) {
        return vendorService.saveVendor(vendor);
    }

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }
    @PutMapping("/{id}")
    public Vendor updateVendor(
            @PathVariable Long id,
            @RequestBody Vendor vendor) {

        return vendorService.updateVendor(id, vendor);
    }
    @DeleteMapping("/{id}")
    public String deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return "Vendor deleted successfully";
    }
}