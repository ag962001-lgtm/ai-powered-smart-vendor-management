package com.smart.vendor_management.service;

import com.smart.vendor_management.entity.Vendor;
import com.smart.vendor_management.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
    public Vendor updateVendor(Long id, Vendor vendor) {

        Vendor existingVendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        existingVendor.setVendorName(vendor.getVendorName());
        existingVendor.setContactPerson(vendor.getContactPerson());
        existingVendor.setEmail(vendor.getEmail());
        existingVendor.setPhone(vendor.getPhone());

        return vendorRepository.save(existingVendor);
    }
    public void deleteVendor(Long id) {

        if (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor not found");
        }

        vendorRepository.deleteById(id);
    }
}