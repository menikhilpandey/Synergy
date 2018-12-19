package com.example.alihasan.synergytwo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alihasan.synergytwo.Adapters.AppExecutor;
import com.example.alihasan.synergytwo.Assignments.AssignmentChoose;
import com.example.alihasan.synergytwo.api.service.Client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;

    private static final String FILE_PROVIDER_AUTHORITY = "com.example.alihasan.synergytwo.fileprovider";

    static String SERVER_URL = "http://87408ed5.ngrok.io/project/aztekgo/android/";

    private String mTempPhotoPath;

    private Bitmap mResultsBitmap;

    File photoFile = null;

    ImageView imageView;
    FloatingActionButton floatingActionButton;
    Button exitButton;

    TextView tv;

    Intent i = getIntent();
//    String caseNo = i.getStringExtra("CASENO");
//    String ACTIVITY = i.getStringExtra("TYPEOFCASE");
//    String userName = i.getStringExtra("USERANAME");
//String caseNo = "1234";
//    String ACTIVITY = "BUSINESS";
//    String userName = "PDA123";

    String userName;
    String caseNo;
    String ACTIVITY;




    static String globalImageFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        SharedPreferences loginData = getSharedPreferences("PDANOSHARED", Context.MODE_PRIVATE);
        userName = loginData.getString("PDANO", "");

        SharedPreferences caseData = getSharedPreferences("CASEDATA", Context.MODE_PRIVATE);
        caseNo = caseData.getString("CASENO","");
        ACTIVITY = caseData.getString("ACTIVITY","");

        tv = findViewById(R.id.textView);


        imageView = (ImageView)findViewById(R.id.imageView);
        floatingActionButton = findViewById(R.id.photoButton);
        exitButton = (Button) findViewById(R.id.exitButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // If you do not have permission, request it
                    ActivityCompat.requestPermissions(PhotoActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                } else {
                    // Launch the camera if the permission exists
                    exitButton.setVisibility(View.VISIBLE);
                    launchCamera();
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                retrofitExit(caseNo,ACTIVITY);

                SharedPreferences preferences =getSharedPreferences("PDANOSHARED",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                finish();

                SharedPreferences preferences2 =getSharedPreferences("CASEDATA",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = preferences2.edit();
                editor2.clear();
                editor2.apply();
                finish();

                Intent backToAssignmentChoose = new Intent(PhotoActivity.this,AssignmentChoose.class);
                startActivity(backToAssignmentChoose);
            }
        });

    }

    private void launchCamera() {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            try {
                photoFile = createTempImageFile(PhotoActivity.this);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.getAbsolutePath();

                // Get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
        } else {

            // Otherwise, delete the temporary image file
            deleteImageFile(this, mTempPhotoPath);
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */
    private void processAndSetImage() {

        // Resample the saved image to fit the ImageView
        mResultsBitmap = resamplePic(this, mTempPhotoPath);

//        tv.setText(base64conversion(photoFile));
//
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_TEXT, base64conversion(photoFile));
//        startActivity(intent);

        /**
         * UPLOAD IMAGE USING RETROFIT
         */

        retroFitHelper(base64conversion(photoFile));

        // Set the new bitmap to the ImageView
        imageView.setImageBitmap(mResultsBitmap);
    }

    public void retroFitHelper(String encodedImage)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.imageUpload(encodedImage,globalImageFileName,caseNo,ACTIVITY,userName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body().equals("Success"))
                {
                    Toast.makeText(getApplicationContext(), "IMAGE UPLOAD SUCCESSFUL", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "IMAGE UPLOAD UNSUCCESSFUL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();


            }
        });

    }

    public  void retrofitExit(String caseNo, String caseType){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Client client = retrofit.create(Client.class);

        Call<String> call = client.exit(caseNo,caseType);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body().equals("Success"))
                {
                    Toast.makeText(getApplicationContext(), "COMPLETED", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "IN FAILURE", Toast.LENGTH_SHORT).show();


            }
        });

    }

    /**
     * Resamples the captured photo to fit the screen for better memory usage.
     *
     * @param context   The application context.
     * @param imagePath The path of the photo to be resampled.
     * @return The resampled bitmap
     */
    static Bitmap resamplePic(Context context, String imagePath) {

        // Get device screen size information
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);

        int targetH = metrics.heightPixels;
        int targetW = metrics.widthPixels;

        // Get the dimensions of the original bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeFile(imagePath);
    }

    /**
     * Creates the temporary image file in the cache directory.
     *
     * @return The temporary image file.
     * @throws IOException Thrown if there is an error creating the file
     */
    static File createTempImageFile(Context context)
            throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "AZTEK" + timeStamp + "_";

        globalImageFileName = imageFileName;

        File storageDir = context.getExternalCacheDir();

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    /**
     * Deletes image file for a given path.
     *
     * @param context   The application context.
     * @param imagePath The path of the photo to be deleted.
     */
    static boolean deleteImageFile(Context context, String imagePath) {

        // Get the file
        File imageFile = new File(imagePath);

        // Delete the image
        boolean deleted = imageFile.delete();

        // If there is an error deleting the file, show a Toast
        if (!deleted) {
            String errorMessage = context.getString(R.string.error);

        }

        return deleted;
    }

    public String base64conversion(File destination)
    {
        try {
            FileInputStream in = new FileInputStream(destination);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10; //Downsample 10x
            Log.d("PP", " bitmap factory=========="+options);
            Bitmap user_picture_bmp = BitmapFactory.decodeStream(in, null, options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            user_picture_bmp.compress(Bitmap.CompressFormat.JPEG, 20, bos);
            byte[] bArray = bos.toByteArray();
            String encodedImage = Base64.encodeToString(bArray, Base64.DEFAULT);
            return encodedImage;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }


}


