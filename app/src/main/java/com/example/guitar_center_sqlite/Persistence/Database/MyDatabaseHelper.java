package com.example.guitar_center_sqlite.Persistence.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper{
    //Instance Field
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    // Tạo bảng products
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE products (" +
            "id_product TEXT PRIMARY KEY," +
            "name_product TEXT," +
            "unit INTEGER," +
            "price REAL," +
            "image TEXT," +
            "description TEXT)";

    //CONSTRUCTOR
    public MyDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*
    Cụ thể, hàm khởi tạo này có các đối số sau:

    context: Đối tượng Context được sử dụng để tạo hoặc mở cơ sở dữ liệu.
    DATABASE_NAME: Tên của cơ sở dữ liệu.
            null: Tham số này được sử dụng khi chúng ta muốn sử dụng một cơ sở dữ liệu mặc định, không có các tùy chọn bổ sung.
            DATABASE_VERSION: Phiên bản của cơ sở dữ liệu.
    */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS products");
        // Tạo lại bảng mới
        onCreate(db);
    }
}
