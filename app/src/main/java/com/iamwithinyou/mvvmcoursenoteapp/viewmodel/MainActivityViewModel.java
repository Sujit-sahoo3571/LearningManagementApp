package com.iamwithinyou.mvvmcoursenoteapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iamwithinyou.mvvmcoursenoteapp.model.Category;
import com.iamwithinyou.mvvmcoursenoteapp.model.Course;
import com.iamwithinyou.mvvmcoursenoteapp.repo.CourseShopRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    //Repository
    private CourseShopRepository repository;


    //Live data

    private LiveData<List<Category>> selectedCategory;
    private LiveData<List<Course>> allCoursesInCategory;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseShopRepository(application);

    }

    public LiveData<List<Category>> getSelectedCategory() {
        selectedCategory = repository.getCategories();
        return selectedCategory;
    }

    public LiveData<List<Course>> getAllCoursesInCategory(int id) {
        allCoursesInCategory = repository.getCourses(id);
        return allCoursesInCategory;
    }

    //courses
    public void insertCourse(Course course){
        repository.insertCourse(course);
    }

    public void updateCourse(Course course){
        repository.updateCourse(course);
    }

    public void deleteCourse(Course course){
        repository.deleteCourse(course);
    }
}
