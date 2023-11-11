package com.youtube.jwt.controllerMongo;

import com.youtube.jwt.daoMongo.ProductDao;
import com.youtube.jwt.entityMongo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts()
    {
        return productDao.findAll();
    }
    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/admin/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        Product existingProduct = productDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        existingProduct.updateProduct(updatedProduct);
        return productDao.save(existingProduct);
    }
    @GetMapping("/getProductById/{id}")
    public Optional<Product> getProductById(@PathVariable String id)
    {
        return productDao.findById(id);
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable String id)
    {
        productDao.deleteById(id);
    }

}


