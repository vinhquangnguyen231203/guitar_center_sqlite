package com.example.guitar_center_sqlite.Presentation.Controller.Function;

import com.example.guitar_center_sqlite.Domain.Model.Product;
import com.example.guitar_center_sqlite.Domain.Services.Implementation.ProductServices;
import com.example.guitar_center_sqlite.Domain.Services.Interface.IProductServices;
import com.example.guitar_center_sqlite.Presentation.Controller.Command.Command;

import java.util.List;

public class ListCommand extends Command {
    public ListCommand(IProductServices productServices) {
        super(productServices);
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public List<Product> getAllProduct() {
        return productServices.getAllProduct();
    }
}
