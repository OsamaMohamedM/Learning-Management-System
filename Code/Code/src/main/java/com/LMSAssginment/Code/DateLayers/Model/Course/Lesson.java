package com.LMSAssginment.Code.DateLayers.Model.Course;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Lesson implements Serializable {
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int otp;

    private int duration;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] contentFile;

    public Lesson() {
    }

    public Lesson(int id, int duration, byte[] contentFile) {
        this.id = id;
        this.duration = duration;
        this.contentFile = contentFile;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public byte[] getContentFile() {
        return contentFile;
    }

    public void setContentFile(byte[] contentFile) {
        this.contentFile = contentFile;
    }
}


