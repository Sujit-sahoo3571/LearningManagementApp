package com.iamwithinyou.mvvmcoursenoteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.iamwithinyou.mvvmcoursenoteapp.activity.AddEditActivity;
import com.iamwithinyou.mvvmcoursenoteapp.adapter.CourseAdapter;
import com.iamwithinyou.mvvmcoursenoteapp.databinding.ActivityMainBinding;
import com.iamwithinyou.mvvmcoursenoteapp.model.Category;
import com.iamwithinyou.mvvmcoursenoteapp.model.Course;
import com.iamwithinyou.mvvmcoursenoteapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private ArrayList<Category> categoriesList = new ArrayList<>() ;
    private MainActivityViewModel mainActivityViewModel;
    private MainActivityClickHandler clickHandler;
    private Category selectedCategory;

    //Recycler view
    private RecyclerView courseRecyclerview;
    private CourseAdapter courseAdapter;
    public int selectedCourseId;

    //courses
    private ArrayList<Course> courselist = new ArrayList<>();
    private static final int ADD_CODE_REQUEST_CODE = 1 ;
    private static final int EDIT_CODE_REQUEST_CODE = 2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        clickHandler = new MainActivityClickHandler(this);
        activityMainBinding.setClickHandler(clickHandler);


        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getSelectedCategory().observe(
                this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesList = (ArrayList<Category>) categories;
                for (Category c: categories){
                    Log.i("TAG",c.getCategoryName());
                }
                showSpinner();
            }
        });

        mainActivityViewModel.getAllCoursesInCategory(1).observe(
                this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                for (Course c: courses){
                    Log.v("TAG", c.getCourseName());
                }
            }
        });
    }

    //load
    public void loadCourseArrayList(int courseId){
        mainActivityViewModel.getAllCoursesInCategory(courseId).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                courselist = (ArrayList<Course>) courses ; // Error code
                loadRecyclerView ();
            }
        });
    }

    private void loadRecyclerView() {
        courseRecyclerview = activityMainBinding.secondaryLayout.recyclerview;
        courseRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerview.setHasFixedSize(true);
        courseAdapter = new CourseAdapter();
        courseRecyclerview.setAdapter(courseAdapter);
        courseAdapter.setCourses(courselist);

        //edit the course
        courseAdapter.setListener(new CourseAdapter.onItemClickListener() {
            @Override
            public void onItemClicked(Course course) {
                selectedCourseId = course.getCourseId();
                Intent i = new Intent(MainActivity.this, AddEditActivity.class);
                i.putExtra(AddEditActivity.COURSE_ID, selectedCourseId);
                i.putExtra(AddEditActivity.COURSE_NAME, course.getCourseName());
                i.putExtra(AddEditActivity.COURSE_PRICE, course.getCoursePrice());
                startActivityForResult(i, EDIT_CODE_REQUEST_CODE);

            }
        });

        //delete the course by swiping
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course courseToDelete = courselist.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteCourse(courseToDelete);
                //TODO

            }
        }).attachToRecyclerView(courseRecyclerview);

    }


    private void showSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.layout_spinner,
                categoriesList
        );
        categoryArrayAdapter.setDropDownViewResource(R.layout.layout_spinner);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);


    }


    //click handler class
    public  class MainActivityClickHandler{
        Context context;

        public MainActivityClickHandler(Context context) {
            this.context = context;
        }

        public void onFABClick(View view){
//            Toast.makeText(MainActivity.this, "Fab clicked", Toast.LENGTH_SHORT).show();
            // create the course
            Intent i = new Intent(MainActivity.this, AddEditActivity.class);
//            startActivityForResult(i, ADD_CODE_REQUEST_CODE);
            startActivityForResult(i,ADD_CODE_REQUEST_CODE);

        }

        public void onSelectedItem(AdapterView<?> parent, View view
         , int pos, long id){
            selectedCategory = (Category) parent.getItemAtPosition(pos);
            String message = "id is "+ selectedCategory.getId() + "\n"+
                    "Name is "+selectedCategory.getCategoryName();
            Log.v("TAG", ""+selectedCategory.getId());
            Toast.makeText(context, " "+message, Toast.LENGTH_SHORT).show();

            loadCourseArrayList(selectedCategory.getId());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedCategoryId = selectedCategory.getId();

        if (requestCode == ADD_CODE_REQUEST_CODE && resultCode == RESULT_OK) {
            Course course = new Course();
            course.setCategoryId(selectedCategoryId);
            course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setCoursePrice(data.getStringExtra(AddEditActivity.COURSE_PRICE));
            mainActivityViewModel.insertCourse(course);
        } else if (requestCode == EDIT_CODE_REQUEST_CODE && resultCode == RESULT_OK){

            Course course = new Course();
            course.setCategoryId(selectedCategoryId);
            course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setCoursePrice(data.getStringExtra(AddEditActivity.COURSE_PRICE));
            course.setCourseId(selectedCourseId);
            mainActivityViewModel.updateCourse(course);

        }
    }
}