package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
        private final List<Product> products = new ArrayList<>();
        public ProductsController() {
            products.add(new Product(1, "Mask"));
            products.add(new Product(2, "Gloves"));
            System.out.println("Produse\n\n");
        }
        @GetMapping
        public List<Product> getProducts() {
            return products;
        }

        @PostMapping
        public int createProduct(@RequestParam String name) {
            int id = 1 + products.size();
            products.add(new Product(id, name));
            return id;
        }

        @PostMapping(value = "/obj", consumes="application/json")
        public ResponseEntity<String>
        createProduct(@RequestBody Product product) {
            products.add(product);
            return new ResponseEntity<>(
                    "Product created successfully", HttpStatus.CREATED);
        }

}
