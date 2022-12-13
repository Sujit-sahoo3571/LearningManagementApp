package com.iamwithinyou.mvvmcoursenoteapp.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "course_table", foreignKeys = @ForeignKey(entity = Category.class,
parentColumns = "id", childColumns = "category_id", onDelete = CASCADE
))
public class Course extends BaseObservable {
    @ColumnInfo(name = "course_id")
    @PrimaryKey(autoGenerate = true)
    private int courseId;

    @ColumnInfo(name = "course_name")
    private String courseName;
    @ColumnInfo(name = "course_price")
    private String coursePrice;
    @ColumnInfo(name = "category_id")
    private int categoryId;


    @Ignore
    public Course() {
    }

    public Course(int courseId, String courseName, String coursePrice, int categoryId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coursePrice = coursePrice;
        this.categoryId = categoryId;
    }

    @Bindable
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
        notifyPropertyChanged(BR.courseId);
    }

    @Bindable
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
        notifyPropertyChanged(BR.courseName);
    }

    @Bindable
    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
        notifyPropertyChanged(BR.coursePrice);

    }

    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId); //TODO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId && categoryId == course.categoryId && courseName.equals(course.courseName) && coursePrice.equals(course.coursePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, coursePrice, categoryId);
    }
}
