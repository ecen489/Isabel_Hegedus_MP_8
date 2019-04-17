package com.example.mp_8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.*;

import java.util.ArrayList;

public class PullActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private RecyclerView rView;
private RecyclerView.Adapter rAdp;
private RecyclerView.LayoutManager rLay;
private ArrayList<Integer> ids;
private ArrayList<String> names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);

        mAuth=FirebaseAuth.getInstance();
        rView = (RecyclerView) findViewById(R.id.my_recycler_view);
        rLay = new LinearLayoutManager(this );
        rView.setLayoutManager(rLay);
        //rAdp = new MyAdapter
        ids=new ArrayList<>();
        names=new ArrayList<>();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


    }

    public void query1(View view){

        EditText stuId= findViewById(R.id.stuID);
        String temp = stuId.getText().toString();
        try{
            int id= Integer.parseInt(temp);
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference ref = db.getReference();
            DatabaseReference grades =ref.child("simpsons/grades");
            Query query1 = grades.orderByChild("student_id").equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GradeData[] data = new GradeData[(int) dataSnapshot.getChildrenCount()];
                    int count =0;


                    for(DataSnapshot grade:dataSnapshot.getChildren()){
                        Log.v("fb","grade "+grade.getKey()+" =" +grade.getValue() );
                        try {
                            JSONObject obj = new JSONObject(grade.getValue().toString());
                            String cName = obj.getString("course_name");
                            String grade1 = obj.getString("grade");
                            Log.v("fb", "n: " + cName + " G: " + grade1);
                            data[count]= new GradeData(cName, grade1);
                            count ++;
                        }
                        catch (org.json.JSONException e){

                        }

                    }
                    rAdp = new MyAdapter(data);
                    rView.setAdapter(rAdp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (NumberFormatException e){
            Toast.makeText(PullActivity.this, "Please enter an integer",
                    Toast.LENGTH_SHORT).show();
        }




    }

    public void query2(View view){
        EditText stuId= findViewById(R.id.stuID);
        String temp = stuId.getText().toString();
        try{
            int id= Integer.parseInt(temp);
            getStudentNames(id);
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference ref = db.getReference();
            DatabaseReference grades =ref.child("simpsons/grades");
            Query query2 = grades.orderByChild("student_id").startAt(id);
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GradeData[] data = new GradeData[(int) dataSnapshot.getChildrenCount()];
                    int count =0;


                    for(DataSnapshot grade:dataSnapshot.getChildren()){
                        Log.v("fb","grade "+grade.getKey()+" =" +grade.getValue() );
                        try {
                            JSONObject obj = new JSONObject(grade.getValue().toString());
                            String cName = obj.getString("course_name");
                            String grade1 = obj.getString("grade");
                            int id = obj.getInt("student_id");
                            String name ="";
                            //while(name.equals("")) {
                                for (int i = 0; i < ids.size(); i++) {
                                    Log.v("NOPE", id + ids.toString());
                                    if (ids.get(i) == id) {
                                        name = names.get(i);

                                    }
                                }
                                if (name.equals("")) {
                                    getStudentNames(id);
                                    Thread.sleep(10000);
                                    Log.v("BROKEN", ids.toString() + names.toString());
                                    for (int i = 0; i < ids.size(); i++) {
                                        Log.v("NOPE", id + ids.toString());
                                        if (ids.get(i) == id) {
                                            name = names.get(i);

                                        }
                                    }
                                }
                            //}
                            Log.v("fb", "n: " + cName + " G: " + grade1);
                            data[count]= new GradeData(cName, grade1, name);
                            count ++;
                        }
                        catch (org.json.JSONException e){

                        }
                        catch (InterruptedException i){

                        }

                    }
                    rAdp = new MyAdapter(data);
                    rView.setAdapter(rAdp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (NumberFormatException e){
            Toast.makeText(PullActivity.this, "Please enter an integer",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void signOut(View view){
        mAuth.signOut();
        Intent in = new Intent(getBaseContext(), MainActivity.class);
        startActivity(in);

    }

    public void toPush(View view){
        Intent in = new Intent(getBaseContext(),PushActivity.class);
        startActivity(in);
    }

    public void getStudentNames(int id){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        DatabaseReference grades =ref.child("simpsons/students");
        Query queryName = grades.orderByChild("id").startAt(id);

        queryName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.v("DATA",dataSnapshot.toString());
                    for(DataSnapshot student:dataSnapshot.getChildren()){
                        try{
                            JSONObject obj = new JSONObject(student.getValue().toString());
                            int id = obj.getInt("id");
                            String name = obj.getString("name");
                            ids.add(id);
                            names.add(name);
                            Log.v("BROKEN", ids.toString() + names.toString());
                            Log.v("SNAME","DOES IT ADD NAMES");
                        }
                        catch (JSONException e){

                        }
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
