package com.iamwithinyou.mvvmcoursenoteapp.repo;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.iamwithinyou.mvvmcoursenoteapp.dao.CategoryDAO;
import com.iamwithinyou.mvvmcoursenoteapp.dao.CourseDAO;
import com.iamwithinyou.mvvmcoursenoteapp.db.CourseDatabase;
import com.iamwithinyou.mvvmcoursenoteapp.model.Category;
import com.iamwithinyou.mvvmcoursenoteapp.model.Course;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseShopRepository {
    private CourseDAO courseDAO;
    private CategoryDAO categoryDAO;

    private LiveData<List<Category>> categories;
    private LiveData<List<Course>> courses;


    public CourseShopRepository(Application application) {
        CourseDatabase courseDatabase = CourseDatabase.getInstance(application);
        courseDAO = courseDatabase.courseDAO();
        categoryDAO = courseDatabase.categoryDAO();
        }
    public LiveData<List<Category>> getCategories(){
      return   categoryDAO.getAllCategories() ;
    }

//    public LiveData<List<Course>> getCourses() {
//        return courseDAO.getAllCourses();
//    }
    public LiveData<List<Course>> getCourses(int category_id) {
        return courseDAO.getAllCourses(category_id);
    }


    //category
    public void insertCategory(Category category){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //background task
                categoryDAO.insert(category);

                //post background task

            }
        });
    }

    public void deleteCategory(Category category){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //background task
                categoryDAO.delete(category);

                //post background task

            }
        });
    }

    public void updateCategory(Category category){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //background task
                categoryDAO.update(category);

                //post background task

            }
        });
    }

    //course
    public void insertCourse(Course course){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //background task
                courseDAO.insert(course);

                //post background task

            }
        });
    }

    public void deleteCourse(Course course){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new  Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseDAO.delete(course);
            }
        });
    }

    public void updateCourse(Course course){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new  Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                courseDAO.update(course);
            }
        });
    }

}
