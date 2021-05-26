package com.example.laptrinhandroid_firebase_mockapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptrinhandroid_firebase_mockapi.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<User>  users = new ArrayList<>();
    RecyclerView rcv_user;
    UserAdapter adapter;
    EditText edt_name,edt_age,edt_id;
    Button btn_post,btn_delete,btn_put;
    String url = "https://60ad9ae180a61f00173313b8.mockapi.io/user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcv_user = findViewById(R.id.rcv_users);
        edt_name = findViewById(R.id.edt_name);
        edt_age = findViewById(R.id.edt_age);
        edt_id = findViewById(R.id.edt_id);
        btn_post = findViewById(R.id.btn_post);
        btn_delete = findViewById(R.id.btn_delete);
        btn_put = findViewById(R.id.btn_put);

        edt_id.setHint("Id");
        edt_age.setHint("Age");
        edt_name.setHint("Name");


        getJsonObjectArray(url,users);

        adapter = new UserAdapter(users,this);
        rcv_user.setAdapter(adapter);
        rcv_user.setLayoutManager(new GridLayoutManager(this,1));


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostAPI(url);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
              @Override
               public void onClick(View v) {
                  Delete(url);
             }
        });

        btn_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutAPI(url);
            }
        });


    }

    private void getData(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this,
                        response.toString(), Toast.LENGTH_SHORT).show();
            }
            }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,
                        "Error make by API server!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getJsonObjectArray(String url, List<User> users){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                      for(int i =0 ; i<response.length();i++){
                          try {
                              JSONObject jsonObject = (JSONObject) response.get(i);
                              User user = new User();
                              user.setId(jsonObject.getString("id").toString());
                              user.setAge(jsonObject.getInt("age"));
                              user.setName(jsonObject.getString("name"));
                              users.add(user);
                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,
                                "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                    }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void PostAPI(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",edt_name.getText().toString());
                params.put("age",edt_age.getText().toString());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void PutAPI(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "/" + edt_id.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by Put data!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",edt_name.getText().toString());
                params.put("age",edt_age.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void Delete(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url +"/" + edt_id.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}