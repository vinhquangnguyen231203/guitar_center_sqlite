package com.example.guitar_center_sqlite.Presentation.Controller.Function;

import com.example.guitar_center_sqlite.Domain.Model.Product;
import com.example.guitar_center_sqlite.Domain.Services.Implementation.ProductServices;
import com.example.guitar_center_sqlite.Domain.Services.Interface.IProductServices;
import com.example.guitar_center_sqlite.Presentation.Controller.Command.Command;

import java.util.List;

public class DeleteCommand extends Command {
    private  String id_Product;

    public DeleteCommand(IProductServices productServices, String id_Product) {
        super(productServices);
        this.id_Product = id_Product;
    }

    @Override
    public boolean execute() {
        return productServices.deleteProduct(id_Product);
    }

    @Override
    public List<Product> getAllProduct() {
        return null;
    }
}
