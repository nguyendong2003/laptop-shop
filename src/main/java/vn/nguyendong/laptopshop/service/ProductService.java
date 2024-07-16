package vn.nguyendong.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.nguyendong.laptopshop.domain.Product;
import vn.nguyendong.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product getProductById(long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    public void handleSaveProduct(Product product) {
        this.productRepository.save(product);
    }

    public void handleDeleteProduct(long id) {
        this.productRepository.deleteById(id);
    }
}
