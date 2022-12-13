package com.iamwithinyou.mvvmcoursenoteapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.iamwithinyou.mvvmcoursenoteapp.R;
import com.iamwithinyou.mvvmcoursenoteapp.databinding.ActivityAddEditBinding;
import com.iamwithinyou.mvvmcoursenoteapp.model.Course;

public class AddEditActivity extends AppCompatActivity {
    private Course course;
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_ID = "courseId";
    public static final String COURSE_PRICE = "coursePrice";
    private ActivityAddEditBinding activityAddEditBinding;
    private AddEditActivityHandlers handlers;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        course = new Course();
        activityAddEditBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_add_edit
        );
        activityAddEditBinding.setCourse(course);
        handlers = new AddEditActivityHandlers(this);
        activityAddEditBinding.setClickHandler(handlers);

        Intent i = getIntent();
        if (i.hasExtra(COURSE_ID)){
            //user click on the recyclerview
            setTitle("Edit Course ");
            course.setCourseName(i.getStringExtra(COURSE_NAME));
            course.setCoursePrice(i.getStringExtra(COURSE_PRICE));

        }else {
            // user click on FAB
            setTitle("Create New Course");
        }


    }
    
    
    
    public class AddEditActivityHandlers{
        Context context;

        public AddEditActivityHandlers(Context context) {
            this.context = context;
        }
        
        public void onSubmit(View view){
            if (course.getCourseName() == null ){
                Toast.makeText(context, "course name is Empty.", Toast.LENGTH_SHORT).show();
            }else{
                Intent  i = new Intent();
                i.putExtra(COURSE_NAME, course.getCourseName());
                i.putExtra(COURSE_PRICE, course.getCoursePrice());
                setResult(RESULT_OK, i);
                finish();
            }
//            Toast.makeText(context, "Course submitted", Toast.LENGTH_SHORT).show();
        }
    }
}