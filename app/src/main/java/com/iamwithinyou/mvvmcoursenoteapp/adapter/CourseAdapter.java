package com.iamwithinyou.mvvmcoursenoteapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.iamwithinyou.mvvmcoursenoteapp.R;
import com.iamwithinyou.mvvmcoursenoteapp.databinding.CourseListItemBinding;
import com.iamwithinyou.mvvmcoursenoteapp.diffutil.CourseDiffCallBack;
import com.iamwithinyou.mvvmcoursenoteapp.model.Course;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {


    private onItemClickListener listener;
    private ArrayList<Course> courses = new ArrayList<>();

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CourseListItemBinding courseListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.course_list_item,
                parent,
                false
        );
        return new CourseViewHolder(courseListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.courseListItemBinding.setCourse(course);

    }

    @Override
    public int getItemCount() {
        return null != courses ? courses.size(): 0 ;
    }

    //inner class viewholder
    public class CourseViewHolder extends RecyclerView.ViewHolder{
        private  CourseListItemBinding courseListItemBinding;


        public CourseViewHolder( CourseListItemBinding courseListItemBinding) {
            super(courseListItemBinding.getRoot());
            this.courseListItemBinding = courseListItemBinding;
            courseListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickPostion = getAdapterPosition();
                    if (listener != null && clickPostion != RecyclerView.NO_POSITION){
                        listener.onItemClicked(courses.get(clickPostion));
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClicked(Course course);
    }
    public void setListener(onItemClickListener listener){
        this.listener = listener;
    }


    public void setCourses(ArrayList<Course> newCourses) {
//        this.courses = courses;
//        notifyDataSetChanged();

        final DiffUtil.DiffResult result =
                DiffUtil.calculateDiff(new CourseDiffCallBack(courses, newCourses), false);

        courses = newCourses;
        result.dispatchUpdatesTo(CourseAdapter.this);
    }
}

