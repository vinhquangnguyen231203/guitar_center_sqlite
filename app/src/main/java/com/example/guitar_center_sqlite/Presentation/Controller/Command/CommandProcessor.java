package com.example.guitar_center_sqlite.Presentation.Controller.Command;

import com.example.guitar_center_sqlite.Domain.Model.Product;

import java.util.List;

public class CommandProcessor {
   public boolean execute(Command command)
   {
       return command.execute();
   }
   public List<Product> getAllProduct(Command command)
   {
       return  command.getAllProduct();
   }
}
