package com.LMSAssginment.Code.DateLayers.Model.Answers;


import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import jakarta.persistence.*;

import java.io.File;

@Entity
public class FileAnswer extends Answer {
    private String fileName;
    private String fileType;

    @Lob
    private byte[] data; // store all the data in the file you are upload into database as lob so directly the entire file will be stored in your database
    // whatever the file that you're going to upload that will be stored as a byte array data in the lob okay, so directly the entire file will be stored in your database, if you want you can fetch the data as well from that directly
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public FileAnswer(){

    }

    public FileAnswer(String fileName, String fileType, byte[] data, Course course) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.course = course;
    }


    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void display() {

    }



    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
