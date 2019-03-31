package com.example.gym.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.gym.R;
import com.example.gym.global.ConstantVariables;
import com.example.gym.global.GlobalVariables;
import com.example.gym.global.Statics;
import com.example.gym.libraries.MySingleton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EditHealthActivity extends AppCompatActivity {

    public static int RESULT_LOAD_IMG = 101;

    Button openGallery;
    Button submit;

    EditText height;
    EditText weight;
    EditText bp;
    EditText waistline;
    EditText sugar;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_health);

        openGallery = findViewById(R.id.button6);
        submit = findViewById(R.id.button7);

        height = findViewById(R.id.edit_height);
        weight = findViewById(R.id.edit_weight);
        bp = findViewById(R.id.edit_bp);
        waistline = findViewById(R.id.edit_waist_line);
        sugar = findViewById(R.id.edit_sugar);
        image = findViewById(R.id.imageView);

        openGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validInput()) {
                    updateHealth();                    
                }else{
                    Toast.makeText(EditHealthActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validInput(){
        if(GlobalVariables.compressed_bitmap == null ||
                height.getText().toString().equalsIgnoreCase("") ||
                weight.getText().toString().equalsIgnoreCase("") ||
                bp.getText().toString().equalsIgnoreCase("") ||
                waistline.getText().toString().equalsIgnoreCase("") ||
                sugar.getText().toString().equalsIgnoreCase("")){
            return false;
        }
        return true;
    }

    private void updateHealth(){
        Statics.startProgressDialog("updating", this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ConstantVariables.UPDATE_HEALTH_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditHealthActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                GlobalVariables.compressed_bitmap = null;
                Statics.stopProgressDialog();
                finish();
            }
        }, Statics.getErrorListener(this)){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", GlobalVariables.selectedHealthStatus.id);
                params.put("day_id", height.getText().toString());
                params.put("height", height.getText().toString());
                params.put("weight", weight.getText().toString());
                params.put("blood_pressure", bp.getText().toString());
                params.put("sugar", sugar.getText().toString());
                params.put("waist_line", waistline.getText().toString());
                params.put("image", GlobalVariables.compressed_bitmap);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                final Bitmap resizedImage = getResizedBitmap(selectedImage, 720, 1080);
                GlobalVariables.compressed_bitmap = imageToString(resizedImage);

                image.setImageBitmap(resizedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        Log.d("TAG", Base64.encodeToString(imageBytes, Base64.DEFAULT));
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
