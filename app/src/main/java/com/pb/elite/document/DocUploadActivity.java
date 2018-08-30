package com.pb.elite.document;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pb.elite.BaseActivity;
import com.pb.elite.R;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.product.ProductController;
import com.pb.elite.core.model.DocumentViewEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.response.DocumentResponse;
import com.pb.elite.core.response.DocumentViewResponse;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.product.ProductActivity;
import com.pb.elite.utility.Utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;

public class DocUploadActivity extends BaseActivity implements IResponseSubcriber, View.OnClickListener {


    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PICTURE = 1800;
    LinearLayout llDocumentUpload;
    ImageView ivLogo, ivClientLogo,
            ivPhotoCam, ivPhotoGallery, ivPanCam, ivPanGallery, ivCancelCam, ivCancelGallery, ivAadharCam, ivAadharGallery,
            ivAadhar, ivCancel, ivPan, ivPhoto;
    TextView txtViewDoc1, txtViewDoc2, txtViewDoc3, txtViewDoc4;

    HashMap<String, Integer> body;
    MultipartBody.Part part;
    File Docfile;
    File file;
    Uri imageUri;

    int OrderID;


    private int PROFILE = 1, PHOTO = 2, PAN = 3, AADHAR = 4;
    private String DOC1 = "DOC1", DOC2 = "DOC2", DOC3 = "DOC3", DOC4 = "DOC4";
    int type;
    ///////////
    DataBaseController dataBaseController;
    UserEntity loginEntity;

    RecyclerView rvProduct;
    DocumentAdapter mAdapter;

    List<DocumentViewEntity> lstDoc;
    DocumentViewEntity documentViewEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().getExtras() != null) {
            OrderID = getIntent().getIntExtra("ORDER_ID", 0);

            if (OrderID != 0) {
                showDialog();
                new ProductController(this).getDocumentView(String.valueOf(OrderID), DocUploadActivity.this);
            }
        }

        dataBaseController = new DataBaseController(this);
        loginEntity = dataBaseController.getUserData();

        initialize();

    }


    private void initialize() {


        rvProduct = (RecyclerView) findViewById(R.id.rvProduct);
        rvProduct.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DocUploadActivity.this);
        rvProduct.setLayoutManager(layoutManager);


        //  spProduct = (Spinner) view.view.findViewById(R.id.spProduct);

        llDocumentUpload = (LinearLayout) this.findViewById(R.id.llDocumentUpload);

        ivLogo = (ImageView) this.findViewById(R.id.ivLogo);
        ivClientLogo = (ImageView) this.findViewById(R.id.ivClientLogo);

        ivPhotoCam = (ImageView) this.findViewById(R.id.ivPhotoCam);
        ivPhotoGallery = (ImageView) this.findViewById(R.id.ivPhotoGallery);
        ivPanCam = (ImageView) findViewById(R.id.ivPanCam);
        ivPanGallery = (ImageView) findViewById(R.id.ivPanGallery);

        ivCancelCam = (ImageView) findViewById(R.id.ivCancelCam);
        ivCancelGallery = (ImageView) findViewById(R.id.ivCancelGallery);
        ivAadharCam = (ImageView) findViewById(R.id.ivAadharCam);
        ivAadharGallery = (ImageView) findViewById(R.id.ivAadharGallery);

        ivAadhar = (ImageView) findViewById(R.id.ivAadhar);
        ivCancel = (ImageView) findViewById(R.id.ivCancel);
        ivPan = (ImageView) findViewById(R.id.ivPan);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

        txtViewDoc1 = (TextView) findViewById(R.id.txtViewDoc1);
        txtViewDoc2 = (TextView) findViewById(R.id.txtViewDoc2);
        txtViewDoc3 = (TextView) findViewById(R.id.txtViewDoc3);
        txtViewDoc4 = (TextView) findViewById(R.id.txtViewDoc4);

        txtViewDoc1.setOnClickListener(this);
        txtViewDoc2.setOnClickListener(this);
        txtViewDoc3.setOnClickListener(this);
        txtViewDoc4.setOnClickListener(this);

        txtViewDoc1.setVisibility(View.INVISIBLE);
        txtViewDoc2.setVisibility(View.INVISIBLE);
        txtViewDoc3.setVisibility(View.INVISIBLE);
        txtViewDoc4.setVisibility(View.INVISIBLE);


        ivPhotoCam.setOnClickListener(this);
        ivPhotoGallery.setOnClickListener(this);
        ivPanCam.setOnClickListener(this);
        ivPanGallery.setOnClickListener(this);

        ivCancelCam.setOnClickListener(this);
        ivCancelGallery.setOnClickListener(this);
        ivAadharCam.setOnClickListener(this);
        ivAadharGallery.setOnClickListener(this);


    }

    private void setDocumentUpload(String urlPath) {

//        if (type == 1) {
        //           ivPhoto.setImageResource(R.drawable.doc_uploaded);
//            txtViewDoc1.setTag(URL);
//            txtViewDoc1.setVisibility(View.VISIBLE);
//            //  Glide.with(ProductActivity.this).load(URL).into(ivPhoto);
//        } else if (type == 2) {
//            ivPan.setImageResource(R.drawable.doc_uploaded);
//            txtViewDoc2.setTag(URL);
//            txtViewDoc2.setVisibility(View.VISIBLE);
//            //  Glide.with(ProductActivity.this).load(URL).into(ivPan);
//        } else if (type == 3) {
//            ivCancel.setImageResource(R.drawable.doc_uploaded);
//            txtViewDoc3.setTag(URL);
//            txtViewDoc3.setVisibility(View.VISIBLE);
//            //  Glide.with(ProductActivity.this).load(URL).into(ivCancel);
//        } else if (type == 4) {
//            ivAadhar.setImageResource(R.drawable.doc_uploaded);
//            txtViewDoc4.setTag(URL);
//            txtViewDoc4.setVisibility(View.VISIBLE);
//            // Glide.with(ProductActivity.this).load(URL).into(ivAadhar);
//        }

        if (documentViewEntity != null) {
            documentViewEntity.setPath(urlPath);

            mAdapter.updateList(documentViewEntity);
        }


    }


    private void viewUploadFile(String url) {

        if (url.equals("")) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(DocUploadActivity.this);

        // TouchImageView ivDocFile;
        ImageView ivDocFile;
        Button btnClose;
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView;

        dialogView = inflater.inflate(R.layout.layout_view_doc, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        btnClose = (Button) dialogView.findViewById(R.id.btnClose);
//        ivDocFile = (TouchImageView) dialogView.findViewById(R.id.ivDocFile);
        ivDocFile = (ImageView) dialogView.findViewById(R.id.ivDocFile);

        Glide.with(this)
                .load(url)

//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivDocFile);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.setCancelable(false);
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation;
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }

    private void launchCamera() {


        String FileName = "EliteDoc";

//
        Docfile = createFile(FileName);
        imageUri = FileProvider.getUriForFile(this,
                getString(R.string.file_provider_authority), Docfile);


        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.txtViewDoc1:
                viewUploadFile(txtViewDoc1.getTag().toString());
                break;

            case R.id.txtViewDoc2:
                viewUploadFile(txtViewDoc2.getTag().toString());
                break;

            case R.id.txtViewDoc3:
                viewUploadFile(txtViewDoc3.getTag().toString());
                break;

            case R.id.txtViewDoc4:
                viewUploadFile(txtViewDoc4.getTag().toString());
                break;


            case R.id.ivPhotoCam:
                type = 1;
                launchCamera();
                break;

            case R.id.ivPhotoGallery:
                type = 1;
                openGallery();
                break;

            case R.id.ivPanCam:
                type = 2;
                launchCamera();
                break;

            case R.id.ivPanGallery:
                type = 2;
                openGallery();
                break;

            case R.id.ivCancelCam:
                type = 3;
                launchCamera();
                break;

            case R.id.ivCancelGallery:
                type = 3;
                openGallery();
                break;

            case R.id.ivAadharCam:
                type = 4;
                launchCamera();
                break;

            case R.id.ivAadharGallery:
                type = 4;
                openGallery();
                break;


        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        cancelDialog();
        if (response instanceof DocumentResponse) {
            if (response.getStatus_code() == 0) {

                setDocumentUpload(((DocumentResponse) response).getData().get(0).getPath());

            }
        } else if (response instanceof DocumentViewResponse) {
            if (response.getStatus_code() == 0) {

                lstDoc = ((DocumentViewResponse) response).getData();

                mAdapter = new DocumentAdapter(DocUploadActivity.this, lstDoc);
                rvProduct.setAdapter(mAdapter);

            }
        }


    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = null;
            try {
                mphoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                mphoto = getResizedBitmap(mphoto, 800);


            } catch (Exception e) {
                e.printStackTrace();
            }

            showDialog();
            file = saveImageToStorage(mphoto, DOC1);
            part = Utility.getMultipartImage(file);
            body = Utility.getBody(this, loginEntity.getUser_id(), documentViewEntity.getDoc_id(), OrderID);
            new ProductController(this).uploadDocuments(part, body, this);


        } else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            Bitmap mphoto = null;
            try {
                mphoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                mphoto = getResizedBitmap(mphoto, 800);

                showDialog();
                file = saveImageToStorage(mphoto, DOC1);
                part = Utility.getMultipartImage(file);
                body = Utility.getBody(this, loginEntity.getUser_id(), documentViewEntity.getDoc_id(), OrderID);

                new ProductController(this).uploadDocuments(part, body, this);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


    public void getActionCamera(DocumentViewEntity entity) {
        documentViewEntity = entity;

        launchCamera();
    }

    public void getActionGallery(DocumentViewEntity entity) {
        documentViewEntity = entity;

        openGallery();

    }

    public void getActionView(DocumentViewEntity entity) {
        documentViewEntity = entity;

        viewUploadFile(entity.getPath());

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
