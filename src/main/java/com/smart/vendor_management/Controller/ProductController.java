package com.smart.vendor_management.Controller;

import com.smart.vendor_management.entity.Product;
import com.smart.vendor_management.service.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        return productService.getLowStockProducts();
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
    @PutMapping("/{id}/increase-stock")
    public Product increaseStock(
            @PathVariable Long id,
            @RequestParam int quantity) {

        return productService.increaseStock(id, quantity);
    }
    @PutMapping("/{id}/decrease-stock")
    public ResponseEntity<?> decreaseStock(
            @PathVariable Long id,
            @RequestParam int quantity) {

        try {
            return ResponseEntity.ok(productService.decreaseStock(id, quantity));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


}