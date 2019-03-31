package com.example.gym.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gym.R;
import com.example.gym.global.ConstantVariables;
import com.example.gym.global.StaticVariables;
import com.example.gym.global.Statics;
import com.example.gym.libraries.MyJSONObject;
import com.example.gym.libraries.MySingleton;
import com.example.gym.models.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.editText2);
        password = findViewById(R.id.editText3);
        login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login(){
        Statics.startProgressDialog("Loggin in", this);
        StringRequest login = new StringRequest(Request.Method.POST, ConstantVariables.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyJSONObject myJSONObject = new MyJSONObject(Statics.parseJSON(response));
                if (myJSONObject.isSuccess()) {
                    MyJSONObject data = new MyJSONObject(myJSONObject.getJSON("data"));
                    StaticVariables.user = new User(
                            data.get("name"),
                            data.get("email"),
                            data.get("contact_no"),
                            data.get("address"),
                            data.get("gender"),
                            data.get("birthday"),
                            data.get("image")
                    );
                    StaticVariables.user_id = data.get("id");
                    startActivity(new Intent(MainActivity.this, Dashboard.class));

                }else{
                    Toast.makeText(MainActivity.this, myJSONObject.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Statics.stopProgressDialog();

            }
        }, Statics.getErrorListener(this)){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                return params;

            }
        };

        MySingleton.getInstance(this).addToRequestQueue(login);
    }
}
