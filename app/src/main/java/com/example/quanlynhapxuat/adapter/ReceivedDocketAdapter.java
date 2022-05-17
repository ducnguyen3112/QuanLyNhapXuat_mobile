package com.example.quanlynhapxuat.adapter;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlynhapxuat.R;
import com.example.quanlynhapxuat.activity.ReceivedDocket.ReceivedDocketDetailActivity;
import com.example.quanlynhapxuat.api.ApiUtils;
import com.example.quanlynhapxuat.model.Product;
import com.example.quanlynhapxuat.model.ReceivedDocket;
import com.example.quanlynhapxuat.model.ReceivedDocketDetail;
import com.example.quanlynhapxuat.model.RestErrorResponse;
import com.example.quanlynhapxuat.utils.CustomToast;
import com.google.gson.Gson;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceivedDocketAdapter extends RecyclerView.Adapter<ReceivedDocketAdapter.ReceivedDocketViewHolder> {
    private Context context;
    private ArrayList<ReceivedDocket> receivedDocketList;
    private String mPDFPath;

    public ReceivedDocketAdapter(Context context) {
        this.context = context;
    }

    public void setReceivedDocketList(ArrayList<ReceivedDocket> receivedDocketList) {
        this.receivedDocketList = receivedDocketList;
    }

    @NonNull
    @Override
    public ReceivedDocketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received_docket,parent,false);
        return new ReceivedDocketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedDocketViewHolder holder, int position) {
        ReceivedDocket receivedDocket = receivedDocketList.get(position);
        if(receivedDocket==null) {
            return;
        }

        holder.tvID.setText("PN"+receivedDocket.getId());
        holder.tvCreatedAt.setText(receivedDocket.getCreatedAt());
        holder.tvSupplierName.setText(receivedDocket.getSupplier_name());
        holder.tvTotalDocket.setText(getTotalDocket(receivedDocket.receivedDocketDetails)+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReceivedDocketDetailActivity.class);
                intent.putExtra("maPN", receivedDocket.getId());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                getReceivedDocketForDPF(receivedDocket.getId());

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return receivedDocketList==null ? 0 : receivedDocketList.size();
    }

    public class ReceivedDocketViewHolder extends RecyclerView.ViewHolder {
        private TextView tvID;
        private TextView tvCreatedAt;
        private TextView tvSupplierName;
        private TextView tvTotalDocket;

        public ReceivedDocketViewHolder(@NonNull View itemView) {
            super(itemView);

            tvID = itemView.findViewById(R.id.tvID_itemReceivedDocket);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt_itemReceivedDocket);
            tvSupplierName = itemView.findViewById(R.id.tvSupplierName_itemReceivedDocket);
            tvTotalDocket = itemView.findViewById(R.id.tvTotalDocket_itemReceivedDocket);
        }
    }

    private int getTotalDocket(ArrayList<ReceivedDocketDetail> receivedDocketDetailList) {
        if(receivedDocketDetailList==null) {
            return 0;
        }
        int sum = 0;
        for(ReceivedDocketDetail receivedDocketDetail : receivedDocketDetailList) {
            sum += receivedDocketDetail.getPrice()*receivedDocketDetail.getQuantity();
        }
        return sum;
    }

    public int getTotalList() {
        int sum = 0;
        if(receivedDocketList!=null) {
            for(ReceivedDocket receivedDocket : receivedDocketList) {
                sum += getTotalDocket(receivedDocket.receivedDocketDetails);
            }
        }
        return sum;
    }

    public void getReceivedDocketForDPF(int maPN) {
        ApiUtils.getReceivedDocketService().getReceivedDocket(maPN).enqueue(new Callback<ReceivedDocket>() {
            @Override
            public void onResponse(Call<ReceivedDocket> call, Response<ReceivedDocket> response) {
                if(response.isSuccessful()) {
                    try {
                        CreatePDF(response.body());
                        CustomToast.makeText(context,"Xuất file PDF thành công!"
                                ,CustomToast.LENGTH_SHORT,CustomToast.SUCCESS).show();
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        Gson g = new Gson();
                        RestErrorResponse errorResponse = g.fromJson(response.errorBody().string(),RestErrorResponse.class);
                        CustomToast.makeText(context,"TRY: " + errorResponse.getMessage()
                                ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
                    }
                    catch (Exception e) {
                        CustomToast.makeText(context,"CATCH: " + e.getMessage()
                                ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReceivedDocket> call, Throwable t) {
                CustomToast.makeText(context,"CALL API FAIL!!!"
                        ,CustomToast.LENGTH_LONG,CustomToast.ERROR).show();
            }
        });
    }

    private void CreatePDF(ReceivedDocket receivedDocket) throws FileNotFoundException{
        if(receivedDocket==null||receivedDocket.receivedDocketDetails==null) {
            Log.e("receivedDocket","null");
            CustomToast.makeText(context,"Đơn hàng rỗng!"
                    ,CustomToast.LENGTH_SHORT,CustomToast.WARNING).show();

            return;
        }
        Log.e("receivedDocket",receivedDocket.toString());

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        Log.e("pdfPath: ", pdfPath);
        String pdfPath1 = "";
        String child = "ChiTietPhieuXuat-" + receivedDocket.getId() + ".pdf";
        File file = new File(pdfPath,child);
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        Text textTittle = new Text("CHI TIET PHIEU XUAT").setFontSize(28).setBold();
        Paragraph paraTittle = new Paragraph();
        paraTittle.add(textTittle).setTextAlignment(TextAlignment.CENTER);
        document.add(paraTittle);
        document.add(new Paragraph("\n\n\n"));

        //table RD
        float clwRD[] = {150,150};
        Table tableRD = new Table(clwRD);
        //1
        tableRD.addCell(new Cell().add(new Paragraph("MA PHIEU NHAP").setTextAlignment(TextAlignment.LEFT)));
        tableRD.addCell(new Cell().add(new Paragraph(receivedDocket.getId()+"").setTextAlignment(TextAlignment.CENTER)));
        //2
        tableRD.addCell(new Cell().add(new Paragraph("NHAN VIEN TAO").setTextAlignment(TextAlignment.LEFT)));
        tableRD.addCell(new Cell().add(new Paragraph(receivedDocket.getEmployee_id()+"").setTextAlignment(TextAlignment.RIGHT)));
        //3
        tableRD.addCell(new Cell().add(new Paragraph("NGAY TAO").setTextAlignment(TextAlignment.LEFT)));
        tableRD.addCell(new Cell().add(new Paragraph(receivedDocket.getCreatedAt()+"").setTextAlignment(TextAlignment.RIGHT)));
        //4
        tableRD.addCell(new Cell().add(new Paragraph("NHA CUNG CAP").setTextAlignment(TextAlignment.LEFT)));
        tableRD.addCell(new Cell().add(new Paragraph(receivedDocket.getSupplier_name()+"").setTextAlignment(TextAlignment.RIGHT)));
        //5
        Text t1 = new Text("Tong: ").setItalic();
        Text t2 = new Text(getTotalList()+"").setBold();
        tableRD.addCell(new Cell(1,2).add(new Paragraph().add(t1).add(t2).setTextAlignment(TextAlignment.RIGHT)));
        //tableRD.addCell(new Cell().add(new Paragraph()));
        //
        document.add(tableRD);

        document.add(new Paragraph("\n\n\n"));

        //table danh sach san pham
        float clwList[] = {40,90,290,90,90,100};
        Table tableListProducts = new Table(clwList);
        //0
        tableListProducts.addCell(new Cell().add(new Paragraph("STT")).setTextAlignment(TextAlignment.CENTER));
        tableListProducts.addCell(new Cell().add(new Paragraph("Hinh anh")).setTextAlignment(TextAlignment.CENTER));
        tableListProducts.addCell(new Cell().add(new Paragraph("Ten san pham")).setTextAlignment(TextAlignment.CENTER));
        tableListProducts.addCell(new Cell().add(new Paragraph("Don gia")).setTextAlignment(TextAlignment.CENTER));
        tableListProducts.addCell(new Cell().add(new Paragraph("So luong")).setTextAlignment(TextAlignment.CENTER));
        tableListProducts.addCell(new Cell().add(new Paragraph("Thanh tien")).setTextAlignment(TextAlignment.CENTER));
        //1-n
        getProductList();
        if(productList==null||productList.size()==0) {
            CustomToast.makeText(context,"PDF: productList is empty!"
                    ,CustomToast.LENGTH_SHORT,CustomToast.WARNING).show();
            return;
        }
        int stt = 1;
        for(ReceivedDocketDetail item : receivedDocket.receivedDocketDetails) {
            Product product = getProduct(item.getProductId());
            if(product==null) {
                CustomToast.makeText(context,"PDF: 1 product is null!"
                        ,CustomToast.LENGTH_SHORT,CustomToast.WARNING).show();
                break;
            }

            Bitmap bitmap;
            Drawable drawable = context.getDrawable(R.drawable.ic_product);
            bitmap = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] bitmapData = stream.toByteArray();
            ImageData imageData = ImageDataFactory.create(bitmapData);
            Image image = new Image(imageData);
            image.setWidth(80).setHeight(80);

            tableListProducts.addCell(new Cell().add(new Paragraph(stt + "")).setTextAlignment(TextAlignment.CENTER));
            tableListProducts.addCell(new Cell().add(image));
            tableListProducts.addCell(new Cell().add(new Paragraph(product.getName()+"")));
            tableListProducts.addCell(new Cell().add(new Paragraph(item.getPrice()+"")).setTextAlignment(TextAlignment.CENTER));
            tableListProducts.addCell(new Cell().add(new Paragraph(item.getQuantity()+"")).setTextAlignment(TextAlignment.CENTER));
            tableListProducts.addCell(new Cell().add(new Paragraph((item.getPrice()*item.getQuantity())+"")).setTextAlignment(TextAlignment.CENTER));

            stt++;
        }
        document.add(tableListProducts);
        //
        Text tongCong = new Text("Tong cong: ").setFontSize(14).setItalic();
        Text tongTien = new Text(getTotalList()+" VND").setFontSize(14).setBold();
        document.add(new Paragraph().add(tongCong).add(tongTien).setTextAlignment(TextAlignment.RIGHT));
        //
        mPDFPath = file.getPath();
        document.close();
    }

    private ArrayList<Product> productList;

    private void getProductList() {
        ApiUtils.productRetrofit().getProducts().enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if(response.isSuccessful()) {
                    productList = response.body();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

            }
        });
    }

    private Product getProduct(int productId) {
        for(Product item : productList) {
            if(item.getId()==productId) {
                return item;
            }
        }
        return null;
    }
}
