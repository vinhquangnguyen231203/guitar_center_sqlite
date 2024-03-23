package com.example.guitar_center_sqlite.Persistence.Interface;

import com.example.guitar_center_sqlite.Domain.Model.Product;

import java.util.List;

public interface IProductRepository {
    boolean insertProduct(Product product);
    boolean deleteProduct(String id_Product);
    boolean updateProduct(Product product);
    List<Product> getAllProduct();
}
