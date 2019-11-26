package com.novc.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView tvChecking, tvCheckingShow , tvCheckout , tvCheckoutShow,
    tvTotal, tvVat, tvGrossTotal;
    Spinner sRoomtype;
    EditText etAdult, etChildren, etRoom;
    Button btncalculate;

    private String[] Roomtype ={"Deluxe","Presidential","Premium"};
    double total, noofday, grosstotal,vat, roomcost;
    Date date1 ,date2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvChecking = findViewById(R.id.tvChecking);
        tvCheckingShow = findViewById(R.id.tvCheckingShow);
        tvCheckout = findViewById(R.id.tvCheckout);
        tvCheckoutShow = findViewById(R.id.tvCheckoutShow);
        tvTotal = findViewById(R.id.tvTotal);
        tvVat = findViewById(R.id.tvVat);
        tvGrossTotal = findViewById(R.id.tvGrosstotal);
        sRoomtype = findViewById(R.id.sRoomtype);
        etAdult = findViewById(R.id.etAdult);
        etChildren = findViewById(R.id.etChildren);
        etRoom = findViewById(R.id.etRoom);
        btncalculate = findViewById(R.id.btnCalculate);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, Roomtype);
        sRoomtype.setAdapter(adapter);

        tvChecking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCheckingDate();
            }
        });

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCheckoutDate();
            }
        });

        btncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etAdult.getText())){
                    etAdult.setError("Enter number of adult");
                    return;
                }
                if (TextUtils.isEmpty(etChildren.getText())){
                    etAdult.setError("Enter number of adult");
                    return;
                }
                if (TextUtils.isEmpty(etRoom.getText())){
                    etAdult.setError("Enter number of adult");
                    return;
                }
                /*if (etAdult.getText().toString().equals("0")){
                    etAdult.setError("Adult cannot be 0");
                    return;
                }
                if (etRoom.getText().toString().equals("0")){
                    etAdult.setError("Room cannot be 0");
                    return;
                }*/
                /*try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                    date1 = sdf.parse(tvCheckingShow.toString());
                    date2 = sdf.parse(tvCheckoutShow.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (date1.compareTo(date2) < 0) {

                 */
                    noofday = 2 ;
                    CalculateGross();
                    /*
                } else {

                }
                     */
            }
        });

    }


    private void loadCheckingDate(){
        final Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerChecking = new DatePickerDialog(
                this,this,year,month,day);
        datePickerChecking.show();
    }

    private void loadCheckoutDate(){
        final Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerCheckOut = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "-" + month + "-" + year;
                tvCheckoutShow.setText(date);
            }
        }, year, month, day);
        datePickerCheckOut.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = dayOfMonth + "-" + month + "-" + year;
        tvCheckingShow.setText(date);
    }

    public void CalculateGross(){
        if (sRoomtype.getSelectedItem().toString().equals("Deluxe")) {
            roomcost = 2000;
        }
        else if (sRoomtype.getSelectedItem().toString().equals("Presidential")) {
            roomcost = 5000;
        }
        else if (sRoomtype.getSelectedItem().toString().equals("Premium")) {
            roomcost = 4000;
        }
        int noofroom = Integer.parseInt(etRoom.getText().toString());
        total = roomcost * noofroom * noofday;
        vat = (13/100*total);
        grosstotal = total + vat;
        String setTotal = "Total : " + total;
        String setVat ="Vat (13%) : " + vat;
        String setGrossTotal = "Gross Total : "+ grosstotal;
        tvTotal.setText(setTotal);
        tvVat.setText(setVat);
        tvGrossTotal.setText(setGrossTotal);
    }


}
