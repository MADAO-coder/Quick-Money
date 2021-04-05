package com.CSCI.a3130_group_6.PayPal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import android.content.Intent;

import com.CSCI.a3130_group_6.HelperClases.PaymentModel;
import com.CSCI.a3130_group_6.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class PaymentStatus extends AppCompatActivity {

    TextView txtId, txtAmount, txtStatus;
    String employeeName, listingKey, employerName, amount, wallet;
    PaymentModel responseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);


        txtId = findViewById(R.id.txtId);
        txtAmount = findViewById(R.id.txtAmount);
        txtStatus = findViewById(R.id.txtStatus);

        Intent intent = getIntent();
        employeeName = intent.getStringExtra("employeeName");
        amount = intent.getStringExtra("Amount");
        listingKey = intent.getStringExtra("key");
        employerName = intent.getStringExtra("employerName");
        wallet = intent.getStringExtra("wallet");

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
        updateWallet();
    }

    private void showDetails(String paymentAmount) {
        txtId.setText("Transaction ID -- "+responseData.getResponse().getId());
        txtStatus.setText("Status -- "+responseData.getResponse().getState());
        txtAmount.setText("Amount -- $" + paymentAmount);

    }
    private void updateWallet(){
        DatabaseReference listingRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employer/");
        //listingRef.child(employerName).child("Listing").child(listingKey).child("Applicants").child("Accepted").child(employeeName).setValue(null);
        listingRef.child(employerName).child("Listing").child(listingKey).child("Applicants").child("Paid").child(employeeName).child("Message").setValue("Payment from a Nigerian Prince");

        // update wallet reference under employeeName
        DatabaseReference employeeRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://group-6-a830d-default-rtdb.firebaseio.com/Employee/"+ employeeName + "/");
        if(wallet==null){
            wallet = "0";
        }
        String postPay = wallet;
        if(responseData.getResponse().getState().equals("approved")){
            int amnt = Integer.parseInt(wallet) + Integer.parseInt(amount);
            postPay = String.valueOf(amnt);
        }
        employeeRef.child("wallet").setValue(postPay);
    }

    // update @employeeName wallet based on @amount
}
