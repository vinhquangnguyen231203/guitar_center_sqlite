package com.example.guitar_center_sqlite.Presentation.Controller.Command;

import com.example.guitar_center_sqlite.Domain.Model.Product;
import com.example.guitar_center_sqlite.Domain.Services.Implementation.ProductServices;
import com.example.guitar_center_sqlite.Domain.Services.Interface.IProductServices;

import java.util.List;

public abstract class Command {
    protected IProductServices productServices;

    public Command(IProductServices productServices) {
        this.productServices = productServices;
    }

    public  abstract boolean execute();
    public  abstract List<Product> getAllProduct();
}
