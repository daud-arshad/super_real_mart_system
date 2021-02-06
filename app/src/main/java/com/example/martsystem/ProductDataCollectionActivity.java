package com.example.martsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

public class ProductDataCollectionActivity extends AppCompatActivity {
    private EditText etProductName, etProductCategory, etProductPrice, etProductQuantity, etProductDesc;
    private AppCompatButton btnAddProduct;
    private ImageView ivUploadImage;
    static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_data_collection);
        init();
        ivUploadImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), PICK_IMAGE_REQUEST);
                ;
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etProductName.getText().toString().trim();
                String productCategory = etProductCategory.getText().toString().trim();
                String productPrice = etProductPrice.getText().toString().trim();
                String productQuantity = etProductQuantity.getText().toString().trim();
                String productDesc = etProductDesc.getText().toString().trim();
                if (productName.isEmpty())
                    etProductName.setError(getString(R.string.can_not_be_empty));
                else if (productCategory.isEmpty())
                    etProductCategory.setError(getString((R.string.can_not_be_empty)));
                else if (productPrice.isEmpty())
                    etProductPrice.setError(getString(R.string.can_not_be_empty));
                else if (productQuantity.isEmpty())
                    etProductQuantity.setError(getString(R.string.can_not_be_empty));
                else if (productDesc.isEmpty()) {
                    etProductDesc.setError(getString(R.string.can_not_be_empty));
                } else {
                    HashMap<String, String> productEntryData = new HashMap<String, String>();
                    productEntryData.put("productName", etProductName.getText().toString().trim());
                    productEntryData.put("productCategory", etProductCategory.getText().toString().trim());
                    productEntryData.put("productPrice", etProductPrice.getText().toString().trim());
                    productEntryData.put("productQuantity", etProductQuantity.getText().toString().trim());
                    productEntryData.put("productDescription", etProductDesc.getText().toString().trim());
                    FirebaseDatabase.getInstance().getReference().child("Products").push().setValue(productEntryData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProductDataCollectionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProductDataCollectionActivity.this, getString(R.string.data_added), Toast.LENGTH_LONG).show();

                        }
                    });
                    Intent productEntryIntent = new Intent();
                    setResult(RESULT_OK, productEntryIntent);
                    //Toast.makeText(ProductDataCollectionActivity.this, getString(R.string.data_added), Toast.LENGTH_LONG).show();
                    ProductDataCollectionActivity.this.finish();

                }
            }
        });
    }

    private void init() {
        etProductName = (EditText) findViewById(R.id.etProductName);
        etProductCategory = (EditText) findViewById(R.id.etProductCategory);
        etProductPrice = (EditText) findViewById(R.id.etProductPrice);
        etProductQuantity = (EditText) findViewById(R.id.etProductQuantity);
        etProductDesc = findViewById(R.id.etProductDesc);
        btnAddProduct = (AppCompatButton) findViewById(R.id.btnAddProduct);
        ivUploadImage = (ImageView) findViewById(R.id.ivUploadImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            imageURI = data.getData();
            Picasso.with(this).load(imageURI).into(ivUploadImage);
            //ivUploadImage.setImageURI(imageURI);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}