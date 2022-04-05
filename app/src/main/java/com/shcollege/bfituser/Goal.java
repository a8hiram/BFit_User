package com.shcollege.bfituser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class Goal extends AppCompatActivity {

    TextView confirm;
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    String value;
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        confirm = (TextView) findViewById(R.id.textView_confirm);

        database = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = database.getReference("User Details");

        models = new ArrayList<>();
        models.add(new Model(R.drawable.improve_shape, "Improve Shape", "I have a low amount of body fat and want to build more muscle mass"));
        models.add(new Model(R.drawable.lean_and_tone, "Lean & Tone", "I’m “skinny fat”. I look thin but have no shape. I want to add lean muscle in the right way"));
        models.add(new Model(R.drawable.lose_a_fat, "Lose a Fat", "I have over 20 lbs to lose. I want to drop all this fat and gain muscle mass"));

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPageMargin(125);

        Integer[] colors_temp = {
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.white)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

                Model obj = models.get(position);
                value = obj.getTitle();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object> data = new HashMap<>();
                data.put("plan",value);

                reference.child(firebaseUser.getUid()).updateChildren(data)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    StyleableToast.makeText(Goal.this, "Plan selected successfully\nSelected plan: "+value, R.style.customtoast).show();
                                    startActivity(new Intent(Goal.this,RegistrationSuccessful.class));
                                }
                                else
                                {
                                    StyleableToast.makeText(Goal.this, task.getException().getMessage(), R.style.customtoast).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                StyleableToast.makeText(Goal.this, e.getMessage(), R.style.customtoast).show();
                            }
                        });

            }
        });
    }
}