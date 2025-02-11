package com.example.smartinventoryapi.controllers;

import com.example.smartinventoryapi.repositories.ProductRepository;
import jakarta.annotation.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/{id}/qr")
    public ResponseEntity<Resource> getQRCodeForProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    try {
                        Path filePath = Paths.get(product.getQrCodePath());
                        Resource file = new UrlResource(filePath.toUri());
                        return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                                .body(file);
                    } catch (Exception e) {
                        return ResponseEntity.notFound().build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
