package com.iamwithinyou.mvvmcoursenoteapp.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.iamwithinyou.mvvmcoursenoteapp.dao.CategoryDAO;
import com.iamwithinyou.mvvmcoursenoteapp.dao.CourseDAO;
import com.iamwithinyou.mvvmcoursenoteapp.model.Category;
import com.iamwithinyou.mvvmcoursenoteapp.model.Course;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Course.class, Category.class}, version = 1)
public abstract class CourseDatabase extends RoomDatabase {
    public abstract CategoryDAO categoryDAO();
    public abstract CourseDAO courseDAO();

    //singleton pattern
    private static CourseDatabase instance;

    public static synchronized CourseDatabase getInstance(Context context){
        if (instance == null ){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CourseDatabase.class,
                    "course_database"
            ).fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }
        return instance;
    }

    //callback
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // insert data when database created.
            initialized();
        }
    };

    private static void initialized() {
        CourseDAO courseDAO = instance.courseDAO();
        CategoryDAO categoryDAO = instance.categoryDAO();

        //thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //category
                Category category1 = new Category();
                category1.setCategoryName("FrontEnd");
                category1.setCategoryDescription("Web development interface ");

                Category category2 = new Category();
                category2.setCategoryName("Apps");
                category2.setCategoryDescription("Mobile development ");

//                Category category3 = new Category();
//                category3.setCategoryName("BackEnd");
//                category3.setCategoryDescription("Web development Database ");

                categoryDAO.insert(category1);
                categoryDAO.insert(category2);
//                categoryDAO.insert(category3);


                // course
                Course course1 = new Course();
                course1.setCourseName("HTML");
                course1.setCoursePrice("Rs. 700");
                course1.setCategoryId(1);

                Course course2 = new Course();
                course2.setCourseName("CSS");
                course2.setCoursePrice("Rs. 700");
                course2.setCategoryId(1);

                Course course3 = new Course();
                course3.setCourseName("Word Press");
                course3.setCoursePrice("Rs. 700");
                course3.setCategoryId(2);

                courseDAO.insert(course1);
                courseDAO.insert(course2);
                courseDAO.insert(course3);


            }
        });
    }
}
