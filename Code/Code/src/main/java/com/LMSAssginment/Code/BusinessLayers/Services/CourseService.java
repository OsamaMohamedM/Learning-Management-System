package com.LMSAssginment.Code.BusinessLayers.Services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Course.CourseDTO;
import com.LMSAssginment.Code.DateLayers.Model.Course.Lesson;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.LessonRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {
    private final InstructorCourseRepo courseRepository;
    private final UserRepo instructorCourseRepo;
    private final LessonRepo lessonRepo;
    public CourseService(InstructorCourseRepo courseRepository, UserRepo instructorCourseRepo, LessonRepo lessonRepo) {
        this.courseRepository = courseRepository;
        this.instructorCourseRepo = instructorCourseRepo;
        this.lessonRepo = lessonRepo;
    }

    public void addCourse(@Autowired Course course,@Autowired int instructorId) {
        try{
            if (instructorId != 0) {
                if (instructorCourseRepo.findById(instructorId) != null) {
                    courseRepository.save(course);
                }
            }
            else System.out.println("Instructor not found");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Autowired
    public List<CourseDTO> viewAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOs = new ArrayList<>();
        for (Course course : courses) {
            Instructor instructor = course.getInstructor();  // Get Instructor for the Course
            CourseDTO courseDTO = new CourseDTO(
                course.getId(),
                course.getName(),
                course.getMaxNumberOfStudent(),
                course.getDescription(),
                instructor.getName() // Add only the instructor's ID
            );
            courseDTOs.add(courseDTO);
        }
        return courseDTOs;
    }
    
    public void addLesson(@Autowired Lesson lesson) {
        if (lesson.getCourse() == null || lesson.getCourse().getId() == 0) {
            throw new IllegalArgumentException("Lesson must have an associated course.");
        }
        Optional<Course> optionalCourse = courseRepository.findById(lesson.getCourse().getId());
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            lesson.setCourse(course);
            course.addLessons(lesson);
            lessonRepo.save(lesson); 
            courseRepository.save(course);
        } else {
            throw new EntityNotFoundException("Course with ID " + lesson.getCourse().getId() + " not found.");
        }
    }

    public void updateCourse(@Autowired Course course) {
        try{
            for(Course c : courseRepository.findAll()) {
                if(c.getId() == course.getId()) {
                    System.out.println("Found");
                    if(course.getName() != null)
                        c.setName(course.getName());
                    if(course.getMaxNumberOfStudent() != 0)
                        c.setMaxNumberOfStudent(course.getMaxNumberOfStudent());
                    if(course.getDescription() != null)
                        c.setDescription(course.getDescription());
                    if(course.getInstructor() != null)
                        c.setInstructor(course.getInstructor());
                    if(course.getLessons() != null)
                        c.setLessons(course.getLessons());
                    if(course.getStudentCourses() != null)
                        c.setStudentCourses(course.getStudentCourses());
                    courseRepository.save(c);
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void deleteCourse(@Autowired int id) {
        try{
            for(Course c : courseRepository.findAll()) {
                if(c.getId() == id) {
                    courseRepository.delete(c);
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}


