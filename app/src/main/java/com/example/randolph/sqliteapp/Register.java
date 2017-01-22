package com.example.randolph.sqliteapp;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    DatabaseHelper myDb;
    Button btnCreate;
    Button btnView;
    EditText fname;
    EditText lname;
    EditText sid;
    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DatabaseHelper(this);
        btnCreate = (Button) findViewById(R.id.btn_signup);
        btnView = (Button) findViewById(R.id.btn_view);
        fname =(EditText) findViewById(R.id.input_fname);
        lname =(EditText) findViewById(R.id.input_lname);
        sid = (EditText) findViewById(R.id.input_sid);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);

        btn_onClickListener();
    }

    private void btn_onClickListener() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setFirstName(fname.getText().toString());
                student.setLastName(lname.getText().toString());
                student.setEmail(email.getText().toString());
                student.setPassword(password.getText().toString());
                student.setStudentID(sid.getText().toString());

                boolean isInserted = myDb.insertData(student);
                if(isInserted == true)
                    Toast.makeText(Register.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Register.this,"Data not Inserted",Toast.LENGTH_LONG).show();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("SID :"+ res.getString(0)+"\n");
                    buffer.append("FNAME :"+ res.getString(1)+"\n");
                    buffer.append("LNAME :"+ res.getString(2)+"\n");
                    buffer.append("EMAILADD :"+ res.getString(3)+"\n");
                    buffer.append("PASSWORD :"+ res.getString(4)+"\n\n");
                }

                // Show all data
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
