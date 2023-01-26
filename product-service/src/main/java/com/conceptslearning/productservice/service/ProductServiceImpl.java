package com.conceptslearning.productservice.service;

import com.conceptslearning.productservice.entity.Product;
import com.conceptslearning.productservice.exception.ProductException;
import com.conceptslearning.productservice.model.ProductRequest;
import com.conceptslearning.productservice.model.ProductResponse;
import com.conceptslearning.productservice.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    private static Map<Long, Product> productData;

    @Override
    public long addProduct(ProductRequest request) {
        Product product = new Product();
        product.setProductName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        productRepository.save(product);
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Getting Product Id:{}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("PRODUCT_NOT_FOUND", "ProductId " + productId + " is not found"));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("PRODUCT_NOT_FOUND", "ProductId " + productId + " is not found"));
        if (product.getQuantity() < quantity) {
            throw new ProductException("NOT_ENOUGH_QUANTITY", "Qantity " + quantity + " is not available");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated successfully!");
    }

}
