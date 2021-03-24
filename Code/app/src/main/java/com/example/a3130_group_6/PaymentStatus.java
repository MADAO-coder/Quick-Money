package com.example.a3130_group_6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import android.content.Intent;
import com.example.a3130_group_6.PaymentModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONException;
import org.json.JSONObject;

public class PaymentStatus extends AppCompatActivity {

    TextView txtId, txtAmount, txtStatus;
    PaymentModel responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);


        txtId = findViewById(R.id.txtId);
        txtAmount = findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txtStatus);

        Intent intent = getIntent();

        GsonBuilder builder = new GsonBuilder();
        Gson mGson = builder.create();

        responseData = mGson.fromJson(intent.getStringExtra("PaymentDetails"), PaymentModel.class);

       /* try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        showDetails(intent.getStringExtra("Amount"));
    }

    private void showDetails(String paymentAmount) {
        txtId.setText("Transaction ID -- "+responseData.getResponse().getId());
        txtStatus.setText("Status -- "+responseData.getResponse().getState());
        txtAmount.setText("Amount -- $" + paymentAmount);
    }
    }
