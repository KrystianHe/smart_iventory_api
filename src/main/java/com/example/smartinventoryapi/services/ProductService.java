package com.example.smartinventoryapi.services;

import com.example.smartinventoryapi.models.Product;
import com.example.smartinventoryapi.repositories.ProductRepository;
import com.example.smartinventoryapi.utils.QRCodeGenerator;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final QRCodeGenerator qrCodeService;

    public ProductService(ProductRepository productRepository, QRCodeGenerator qrCodeService) {
        this.productRepository = productRepository;
        this.qrCodeService = qrCodeService;
    }

    @Transactional
    public Product saveProduct(Product product) {
        String qrData = "https://example.com/product/" + product.getSku();
        String qrPath = qrCodeService.generateAndSaveQRCode(qrData, product.getSku());
        product.setQrCodePath(qrPath);
        return productRepository.save(product);
    }
}