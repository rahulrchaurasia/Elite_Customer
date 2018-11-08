package com.pb.elite.document;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import com.pb.elite.utility.Constants;
import com.pb.elite.utility.Utility;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;

public class DocUploadActivity extends BaseActivity implements IResponseSubcriber , BaseActivity.CustomPopUpListener {


    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PICTURE = 1800;

    HashMap<String, Integer> body;
    MultipartBody.Part part;
    File Docfile;
    File file;
    Uri imageUri;

    int OrderID;



    private String DOC1 = "DOC1", DOC2 = "DOC2", DOC3 = "DOC3", DOC4 = "DOC4";
    int type;
    ///////////
    DataBaseController dataBaseController;
    UserEntity loginEntity;

    RecyclerView rvProduct;
    DocumentAdapter mAdapter;

    List<DocumentViewEntity> lstDoc;
    DocumentViewEntity documentViewEntity;
    String[] perms = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

          registerCustomPopUp(DocUploadActivity.this);
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

    }

    private void setDocumentUpload(String urlPath) {


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

    private void galleryCamPopUp() {

        if (!checkPermission()) {

            if (checkRationalePermission()) {
                //Show Information about why you need the permission
                requestPermission();

            } else {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                //  permissionAlert(navigationView,"Need Call Permission","This app needs Call permission.");
                openPopUp(rvProduct, "Need  Permission", "This app needs all permissions.", "GRANT","DENNY",false, true);


            }
        } else {

            showCamerGalleryPopUp();
        }
    }


    private void showCamerGalleryPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);

        LinearLayout lyCamera, lyGallery;
        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_cam_gallery, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        lyCamera = (LinearLayout) dialogView.findViewById(R.id.lyCamera);
        lyGallery = (LinearLayout) dialogView.findViewById(R.id.lyGallery);

        lyCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
                alertDialog.dismiss();

            }
        });

        lyGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(true);
        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);

        // for user define height and width..
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

        galleryCamPopUp();
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


    // region permission
    private boolean checkPermission() {

        int camera = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[0]);

        int WRITE_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[1]);
        int READ_EXTERNAL = ActivityCompat.checkSelfPermission(getApplicationContext(), perms[2]);

        return camera == PackageManager.PERMISSION_GRANTED
                && WRITE_EXTERNAL == PackageManager.PERMISSION_GRANTED
                && READ_EXTERNAL == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkRationalePermission() {

        boolean camera = ActivityCompat.shouldShowRequestPermissionRationale(DocUploadActivity.this, perms[0]);

        boolean write_external = ActivityCompat.shouldShowRequestPermissionRationale(DocUploadActivity.this, perms[1]);
        boolean read_external = ActivityCompat.shouldShowRequestPermissionRationale(DocUploadActivity.this, perms[2]);

        return camera || write_external || read_external;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, perms, Constants.PERMISSION_CAMERA_STORACGE_CONSTANT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case Constants.PERMISSION_CAMERA_STORACGE_CONSTANT:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternal = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternal = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (camera && writeExternal && readExternal) {

                        showCamerGalleryPopUp();

                    }

                }
                break;




        }
    }

    //endregion


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {

        dialog.cancel();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);
    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        dialog.cancel();
    }
}