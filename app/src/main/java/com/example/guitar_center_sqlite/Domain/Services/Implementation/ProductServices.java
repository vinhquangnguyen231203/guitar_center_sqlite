package com.example.guitar_center_sqlite.Domain.Services.Implementation;

import android.content.Context;

import com.example.guitar_center_sqlite.Domain.Model.Product;
import com.example.guitar_center_sqlite.Domain.Services.Interface.IProductServices;
import com.example.guitar_center_sqlite.Persistence.Implementation.ProductRepository;
import com.example.guitar_center_sqlite.Persistence.Interface.IProductRepository;

import java.util.List;

public class ProductServices implements IProductServices {
    private IProductRepository productRepository;

    public ProductServices(Context context) {
        productRepository = new ProductRepository(context);
    }

    @Override
    public boolean insertProduct(Product product) {
        if (product != null) {
            return productRepository.insertProduct(product);
        }
        return false;
    }

    @Override
    public boolean deleteProduct(String idProduct) {
        if (idProduct != null && !idProduct.isEmpty()) {
            return productRepository.deleteProduct(idProduct);
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        if (product != null) {
            return productRepository.updateProduct(product);
        }
        return false;
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.getAllProduct();
    }
}

