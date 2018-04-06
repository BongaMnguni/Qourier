package com.icourier.qourier;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PackageEditor extends AppCompatActivity {
    ImageView isthombe;
    EditText EdName,EdDestination,EdPackName,EdDescription,EdAmount,EdDate;
    String company,destination,packName,packdescription,amount,date,picture,packkey;
    LinearLayout layoutupdate;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_editor);
        getSupportActionBar().setTitle("Update Package Details");
        Firebase.setAndroidContext(getApplicationContext());

        layoutupdate = (LinearLayout) findViewById(R.id.layout_update);

        EdName = (EditText) findViewById(R.id.edtname);
        EdDestination = (EditText) findViewById(R.id.edtdestination);
        EdPackName = (EditText) findViewById(R.id.edtpackname);
        EdDescription = (EditText) findViewById(R.id.edtdescription);
        EdAmount = (EditText) findViewById(R.id.edtamount);
        EdDate = (EditText) findViewById(R.id.edtdate);
        isthombe = (ImageView)findViewById(R.id.dImage);

        Bundle bundle = getIntent().getExtras();
        company = bundle.getString("name");
        packName = bundle.getString("packagename");
        amount = bundle.getString("amount");
        date = bundle.getString("date");
        destination = bundle.getString("destination");
        packdescription = bundle.getString("details");
        picture = bundle.getString("image");
        packkey = bundle.getString("packkey");

        EdName.setText(company);
        EdDestination.setText(destination);
        EdPackName.setText(packName);
        EdDescription.setText(packdescription);
        EdAmount.setText(amount);
        EdDate.setText(date);

        Glide.with(getApplicationContext()).load(picture).into(isthombe);

        layoutupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase ref = new Firebase("https://i-courier-18c0e.firebaseio.com/I-Courier/Poster");
                ref.child(packkey).child("name").setValue(EdName.getText().toString());
                ref.child(packkey).child("destination").setValue(EdDestination.getText().toString());
                ref.child(packkey).child("pName").setValue(EdPackName.getText().toString());
                ref.child(packkey).child("description").setValue(EdDescription.getText().toString());
                ref.child(packkey).child("amount").setValue(EdAmount.getText().toString());
                ref.child(packkey).child("date").setValue(EdDate.getText().toString());
                Toast.makeText(PackageEditor.this, "Update Successful !!!", Toast.LENGTH_SHORT).show();
            }
        });

        EdDate.setInputType(InputType.TYPE_NULL);
        EdDate.requestFocus();

        // date
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        EdDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
        setDateTimeField();
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                EdDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

}
