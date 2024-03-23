package com.example.guitar_center_sqlite.Presentation.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitar_center_sqlite.Domain.Model.Product;
import com.example.guitar_center_sqlite.Domain.Services.Interface.IProductServices;
import com.example.guitar_center_sqlite.Presentation.Controller.Command.CommandProcessor;
import com.example.guitar_center_sqlite.Presentation.Controller.Function.DeleteCommand;
import com.example.guitar_center_sqlite.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable{
    private final   Context context;
    private final List<Product> productList;

    private  List<Product> productListFull;
    private final CommandProcessor commandProcessor;

    private ProductAdapterListener listener;

    private  IProductServices productServices;
    public ProductAdapter(Context context, List<Product> productList, CommandProcessor commandProcessor)
    {
        this.context = context;
        this.productList = productList;
        this.commandProcessor = commandProcessor;
    }


    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = productList.get(position);

        // Set data to views
        holder.textViewID.setText(product.getId_product());
        holder.textViewName.setText(product.getName_product());
        holder.textViewUnit.setText(String.valueOf(product.getUnit()));
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewDescription.setText(product.getDescription());

        // Lấy ID của tài nguyên từ tên tệp tin ảnh
         String imageName = product.getImage();
         int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
         String imagePath = "android.resource://" + context.getPackageName() + "/" + resourceId;
         Picasso.get().load(Uri.parse(imagePath)).into(holder.imageView);

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này");

                //Khi ấn đồng ý
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean result = commandProcessor.execute(new DeleteCommand(productServices,product.getId_product()));
                                if(result)
                                {
                                    productList.remove(position);
                                    if(productListFull != null)
                                    {
                                        for(int i = 0; i < productListFull.size(); i++)
                                        {
                                            if(productListFull.get(i).getId_product().equals(product.getId_product()))
                                            {
                                                productListFull.remove(i);
                                                break;
                                            }
                                        }
                                    }
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, productList.size());// Xóa sản phẩm khỏi danh sách// Cập nhật giao diện
                                    Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    // Xóa sản phẩm không thành công
                                    Toast.makeText(context, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        //Cập nhật
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductAdapter_Update productAdapterUpdate = new ProductAdapter_Update(context,(ProductAdapter_Update.UpdateProductListener) context);
                productAdapterUpdate.onUpdateProduct(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public void filter(String text) {
        // Nếu danh sách sản phẩm gốc chưa được khởi tạo, sao chép từ danh sách hiện tại
        if (productListFull == null) {
            productListFull = new ArrayList<>(productList);
        }

        productList.clear(); // Xóa danh sách sản phẩm hiện tại

        // Nếu key truyền vào là rỗng, hiển thị toàn bộ danh sách sản phẩm
        if (text.isEmpty()) {
            productList.addAll(productListFull);
        } else {
            // Nếu không, lọc danh sách sản phẩm theo văn bản đầu vào
            String searchText = text.toLowerCase().trim();
            for (Product product : productListFull) {
                if (product.getName_product().toLowerCase().contains(searchText)) {
                    productList.add(product);
                }
            }

        }
        notifyDataSetChanged(); // Cập nhật giao diện RecyclerView
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewID, textViewName, textViewUnit, textViewPrice, textViewDescription;
        Button buttonDelete, buttonEdit;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewID = itemView.findViewById(R.id.textViewID);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewUnit = itemView.findViewById(R.id.textViewUnit);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
        }
    }
    public  void setProductServices(IProductServices productServices)
    {
        this.productServices = productServices;
    }
    public interface ProductAdapterListener
    {
        void onUpdateProduct(Product product);
    }
    public void setUpdateListener(ProductAdapterListener listener)
    {
        this.listener = listener;
    }
}
