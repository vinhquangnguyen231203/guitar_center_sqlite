package com.example.guitar_center_sqlite.Presentation.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.guitar_center_sqlite.Domain.Model.Product;
import com.example.guitar_center_sqlite.Domain.Services.Implementation.ProductServices;
import com.example.guitar_center_sqlite.Domain.Services.Interface.IProductServices;
import com.example.guitar_center_sqlite.Presentation.Controller.Command.CommandProcessor;
import com.example.guitar_center_sqlite.Presentation.Controller.Function.InsertCommand;
import com.example.guitar_center_sqlite.Presentation.Controller.Function.ListCommand;
import com.example.guitar_center_sqlite.Presentation.Controller.Function.UpdateCommand;
import com.example.guitar_center_sqlite.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements  ProductAdapter_Add.AddProductListener,ViewSearch.SearchListener,ProductAdapter_Update.UpdateProductListener{
    private ProductAdapter productAdapter;
    private CommandProcessor commandProcessor;

    private IProductServices productServices;

    private RecyclerView recyclerView;

    private ProductAdapter_Add productAdapterAdd;


    private SearchView searchView;

    private ViewSearch viewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productServices = new ProductServices(MainActivity.this);
        commandProcessor = new CommandProcessor();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//

        searchView = findViewById(R.id.searchView);
        viewSearch = new ViewSearch(this,searchView);
        viewSearch.setSearchListener(this);


        Button buttonAdd= findViewById(R.id.buttonAddProduct);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productAdapterAdd = new ProductAdapter_Add(MainActivity.this,MainActivity.this);
                productAdapterAdd.showAddProductPopup();

            }
        });

        loadProduct();
    }
    private void loadProduct()
    {
        List<Product> productList =
                commandProcessor.getAllProduct(new ListCommand(productServices));

        productAdapter = new ProductAdapter(this,productList,commandProcessor);
        productAdapter.setProductServices(productServices);
        recyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddProduct(Product product) {
        boolean result = commandProcessor.execute(new InsertCommand(productServices,product));
        if(result)
        {
            Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
        loadProduct();
    }

    @Override
    public void onQueryTextChange(String newText) {
        productAdapter.filter(newText);
    }

    @Override
    public void onQueryTextSubmit(String query) {
        //null
    }

    @Override
    public void onUpdateProduct(Product product) {
        boolean result = commandProcessor.execute(new UpdateCommand(productServices,product));
        if(result)
        {
            Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Cập nht sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
        loadProduct();
    }
}