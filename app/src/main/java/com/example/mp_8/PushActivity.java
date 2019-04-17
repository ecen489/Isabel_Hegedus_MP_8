package com.example.mp_8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PushActivity extends AppCompatActivity {
private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void push(View v){
        EditText cidEt = findViewById(R.id.courseId);
        EditText cnameEt =findViewById(R.id.courseName);
        EditText gEt = findViewById(R.id.grade);

        int sid;
        RadioGroup rgp= findViewById(R.id.radGroup);
        int sel = rgp.getCheckedRadioButtonId();
        switch (sel) {
            default: sid=123; break;
            case R.id.bart: sid=123; break;
            case R.id.ralph: sid=404; break;
            case R.id.millhouse: sid=456; break;
            case R.id.lisa: sid=888;break;

        }
        Log.v("push", "id= " + sid);
        String cid= cidEt.getText().toString();
        String cname = cnameEt.getText().toString();
        String gd = gEt.getText().toString();

        int courseId;

        try{
            courseId =Integer.parseInt(cid);
            Grade g = new Grade(courseId,cname,gd,sid);

            DatabaseReference grades =mDatabase.child("simpsons/grades");
            grades.push().setValue(g);


        }
        catch( NumberFormatException e){
            courseId=-1;
            Toast.makeText(PushActivity.this, "Please enter an integer as course ID",
                    Toast.LENGTH_SHORT).show();

        }

    }


}
