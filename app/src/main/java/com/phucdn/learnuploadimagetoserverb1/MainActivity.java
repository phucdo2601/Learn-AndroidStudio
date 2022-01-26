package com.phucdn.learnuploadimagetoserverb1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.phucdn.learnuploadimagetoserverb1.api.ApiService;
import com.phucdn.learnuploadimagetoserverb1.model.User;
import com.phucdn.learnuploadimagetoserverb1.utils.Constant;
import com.phucdn.learnuploadimagetoserverb1.utils.RealPathUtil;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    private static final String TAG = MainActivity.class.getName();

    private EditText edtUsername;
    private EditText edtPassword;

    private Button btnSelectImage;
    private Button btnUploadImage;

    private ImageView imgFromLibrary;
    private CircleImageView imgFromApi;

    private TextView txtUsername;
    private TextView txtPassword;

    private Uri mUri;
    private ProgressDialog mProgressDialog;

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        //There are no request codes
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgFromLibrary.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();
        //Init progress dialog
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Please wait ...");

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUri != null) {
                    callApiRegisterAccount();
                }
            }
        });
    }

    private void initUi() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        imgFromLibrary = findViewById(R.id.img_from_library);
        imgFromApi = findViewById(R.id.img_from_api);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
    }

    //lay request tu file thu vien cua may
    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    //call api create image on server
    private void callApiRegisterAccount() {
        mProgressDialog.show();

        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        RequestBody requestBodyUsername = RequestBody.create(MediaType.parse("multipart/form-data"), username);
        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), password);

        String strRealPath = RealPathUtil.getRealPath(this, mUri);

        Log.e("Phucdn", strRealPath);

        File file = new File(strRealPath);

        // Parse doi tuong file de gui len server
        RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBodyAvt = MultipartBody.Part.createFormData(Constant.KEY_AVARTAR, file.getName(), requestBodyAvt);

        ApiService.apiService.registerAccount(requestBodyUsername, requestBodyPassword, multipartBodyAvt)
                .enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mProgressDialog.dismiss();
                User user = response.body();
                if (user != null) {
                    txtUsername.setText(user.getUsername());
                    txtPassword.setText(user.getPassword());
                    Glide.with(MainActivity.this).load(user.getAvtatar()).into(imgFromApi);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //tat profress dialog di
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Call Api Fail", Toast.LENGTH_LONG).show();
            }
        });
    }

}