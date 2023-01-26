package com.conceptslearning.productservice.controller;

import com.conceptslearning.productservice.model.ProductRequest;
import com.conceptslearning.productservice.model.ProductResponse;
import com.conceptslearning.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductServiceController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest request) {
        long productId = productService.addProduct(request);
        return ResponseEntity.ok(productId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId,
                                               @RequestParam long quantity){
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
