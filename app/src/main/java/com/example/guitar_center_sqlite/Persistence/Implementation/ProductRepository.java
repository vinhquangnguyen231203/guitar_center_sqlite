package com.example.guitar_center_sqlite.Persistence.Implementation;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.guitar_center_sqlite.Domain.Model.Product;
import com.example.guitar_center_sqlite.Persistence.Database.MyDatabaseHelper;
import com.example.guitar_center_sqlite.Persistence.Interface.IProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IProductRepository {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    public ProductRepository(Context context)
    {
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public boolean insertProduct(Product product) {
        //ContentValues giống PrepareStatement: để lưu cặp khóa - giá trị
        // Cursor giống ResultSet : di chuyển giữa các dòng lệnh

        ContentValues values = new ContentValues();
        values.put("id_product", product.getId_product());
        values.put("name_product", product.getName_product());
        values.put("unit", product.getUnit());
        values.put("price", product.getPrice());
        values.put("image", product.getImage());
        values.put("description", product.getDescription());

        long result = database.insert("products", null, values);
        if(result != -1)
        {
            return  true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(String id_Product) {
        // Xác định điều kiện xóa dựa trên id_product
        String selection = "id_product = ?";
        String[] selectionArgs = { id_Product };

        // Thực hiện xóa sản phẩm từ database
        int rowsDeleted = database.delete("products", selection, selectionArgs);

        // Kiểm tra xem có sản phẩm nào được xóa không
        if (rowsDeleted > 0) {
            return true; // Xóa thành công
        } else {
            return false; // Không có sản phẩm nào được xóa
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name_product", product.getName_product());
        values.put("unit", product.getUnit());
        values.put("price", product.getPrice());
        values.put("image", product.getImage());
        values.put("description", product.getDescription());

        int rowsAffected = database.update("products", values, "id_product = ?", new String[]{product.getId_product()});
        if(rowsAffected > 0)
        {
            return  true;
        }
        return false;
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM products", null);
        if (((Cursor) cursor).moveToFirst())
        {
            do {
                @SuppressLint("Range") Product product = new Product(
                        cursor.getString(cursor.getColumnIndex("id_product")),
                        cursor.getString(cursor.getColumnIndex("name_product")),
                        cursor.getInt(cursor.getColumnIndex("unit")),
                        cursor.getDouble(cursor.getColumnIndex("price")),
                        cursor.getString(cursor.getColumnIndex("image")),
                        cursor.getString(cursor.getColumnIndex("description"))
                );
                productList.add(product);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return productList;
    }
}
