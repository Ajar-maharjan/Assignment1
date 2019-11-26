package com.novc.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView tvChecking, tvCheckingShow, tvCheckout, tvCheckoutShow,
            tvTotal, tvVat, tvGrossTotal;
    Spinner sRoomtype;
    EditText etAdult, etChildren, etRoom;
    Button btncalculate;
    AlertDialog.Builder builder;

    private String[] Roomtype = {"Deluxe : Rs 2000", "Presidential : Rs 5000", "Premium : Rs 4000"};
    double total, grosstotal, vat, roomcost, noofday;
    Date dateChecking, dateCheckout;

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
        builder = new AlertDialog.Builder(this);

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
                if (TextUtils.isEmpty(tvCheckingShow.getText())) {
                    tvCheckingShow.setError("Checking date not selected");
                    return;
                }
                if (TextUtils.isEmpty(tvCheckoutShow.getText())) {
                    tvCheckoutShow.setError("Checkout date not selected");
                    return;
                }
                if (TextUtils.isEmpty(etAdult.getText())) {
                    etAdult.setError("Enter number of adult");
                    return;
                }
                if (TextUtils.isEmpty(etChildren.getText())) {
                    etChildren.setError("Enter number of adult");
                    return;
                }
                if (TextUtils.isEmpty(etRoom.getText())) {
                    etRoom.setError("Enter number of adult");
                    return;
                }
                if (etAdult.getText().toString().equals("0")) {
                    etAdult.setError("Adult cannot be 0");
                    return;
                }
                if (etRoom.getText().toString().equals("0")) {
                    etRoom.setError("Room cannot be 0");
                    return;
                }
                if (dateChecking.before(dateCheckout)) {
                    noofday = Double.parseDouble(GetDay());
                    CalculateGross();
                    tvCheckingShow.setError(null);
                    tvCheckoutShow.setError(null);
                } else {

                    dialogmsg();
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

    }


    private void loadCheckingDate() {
        final Calendar date1 = Calendar.getInstance();
        int year = date1.get(Calendar.YEAR);
        int month = date1.get(Calendar.MONTH);
        int day = date1.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerChecking = new DatePickerDialog(
                this, this, year, month, day);
        datePickerChecking.show();
    }

    private void loadCheckoutDate() {
        final Calendar date2 = Calendar.getInstance();
        int year = date2.get(Calendar.YEAR);
        int month = date2.get(Calendar.MONTH);
        int day = date2.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerCheckOut = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "-" + month + "-" + year;
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    dateCheckout = format.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tvCheckoutShow.setText(date);
            }
        }, year, month, day);
        datePickerCheckOut.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = dayOfMonth + "-" + month + "-" + year;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dateChecking = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvCheckingShow.setText(date);
    }

    private void CalculateGross() {
        if (sRoomtype.getSelectedItem().toString().equals("Deluxe : Rs 2000")) {
            roomcost = 2000;
        } else if (sRoomtype.getSelectedItem().toString().equals("Presidential : Rs 5000")) {
            roomcost = 5000;
        } else if (sRoomtype.getSelectedItem().toString().equals("Premium : Rs 4000")) {
            roomcost = 4000;
        }
        int noofroom = Integer.parseInt(etRoom.getText().toString());
        total = roomcost * noofroom * noofday;
        vat = (0.13 * total);
        grosstotal = total + vat;
        String setTotal = "Total : Rs." + total;
        String setVat = "Vat (13%) : Rs." + vat;
        String setGrossTotal = "Gross Total : Rs." + grosstotal;
        tvTotal.setText(setTotal);
        tvVat.setText(setVat);
        tvGrossTotal.setText(setGrossTotal);
    }

    private String GetDay() {
        Calendar cCal = Calendar.getInstance();
        cCal.setTime(dateChecking);
        int cYear = cCal.get(Calendar.YEAR);
        int cMonth = cCal.get(Calendar.MONTH);
        int cDay = cCal.get(Calendar.DAY_OF_MONTH);

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(dateCheckout);
        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        return dayCount + "";
    }

    private void dialogmsg() {
        builder.setTitle("Date mismatch")
                .setMessage("Invalid Checkout date. Checkout date cannot greater than checking date")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
    }
}
