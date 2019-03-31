package com.example.gym.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.gym.R;
import com.example.gym.global.ConstantVariables;
import com.example.gym.global.GlobalVariables;
import com.example.gym.global.Statics;
import com.example.gym.libraries.MyJSONObject;
import com.example.gym.libraries.MySingleton;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPassword;
    EditText newPassword;
    EditText confirmPassword;

    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = findViewById(R.id.cp_old);
        newPassword = findViewById(R.id.cp_new);
        confirmPassword = findViewById(R.id.cp_confirm);

        save = findViewById(R.id.button3);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }

    private void changePassword(){
        Statics.startProgressDialog("Changing password", this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ConstantVariables.CHANGE_PASSWORD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Statics.stopProgressDialog();
                MyJSONObject myJSONObject = new MyJSONObject(Statics.parseJSON(response));
                if (myJSONObject.isSuccess()) {
                    Toast.makeText(ChangePasswordActivity.this, myJSONObject.get("message"), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ChangePasswordActivity.this, myJSONObject.get("message"), Toast.LENGTH_SHORT).show();
                }
            }
        }, Statics.getErrorListener(this)){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", GlobalVariables.user.email);
                params.put("old_password", oldPassword.getText().toString());
                params.put("new_password", newPassword.getText().toString());
                params.put("confirm_password", confirmPassword.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
