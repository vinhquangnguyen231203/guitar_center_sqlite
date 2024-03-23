package com.example.guitar_center_sqlite.Presentation.Controller.Function;

import com.example.guitar_center_sqlite.Domain.Model.Product;
import com.example.guitar_center_sqlite.Domain.Services.Implementation.ProductServices;
import com.example.guitar_center_sqlite.Domain.Services.Interface.IProductServices;
import com.example.guitar_center_sqlite.Presentation.Controller.Command.Command;

import java.util.List;

public class UpdateCommand extends Command {
    private Product product;

    public UpdateCommand(IProductServices productServices, Product product) {
        super(productServices);
        this.product = product;
    }

    @Override
    public boolean execute() {
        return productServices.updateProduct(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return null;
    }
}
