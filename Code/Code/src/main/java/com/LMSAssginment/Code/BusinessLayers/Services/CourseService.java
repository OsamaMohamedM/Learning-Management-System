
package com.LMSAssginment.Code.BusinessLayers.Services;
import java.io.IOException;
import java.util.*;

import com.LMSAssginment.Code.DateLayers.Model.User;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class CourseService {
    private final InstructorCourseRepo courseRepository;
    private final UserRepo userRepo;
    private final LessonRepo lessonRepo;
    private NotificationService notificationService;
    public CourseService(InstructorCourseRepo courseRepository, UserRepo instructorCourseRepo, LessonRepo lessonRepo,NotificationService notificationService) {
        this.courseRepository = courseRepository;
        this.userRepo = instructorCourseRepo;
        this.lessonRepo = lessonRepo;
        this.notificationService=notificationService;
    }

    public void addCourse(@Autowired Course course,@Autowired int instructorId) {
        try{
            if (instructorId != 0 && course != null) {
                boolean f = false;
                String name = course.getName();
                List<Course> courses = courseRepository.findAll();
                for (Course c : courses) {
                    if (c.getName().equals(name)) {
                        System.out.println("Course already exists");
                        f = true;
                        break;
                    }
                }
                if (f) {
                    System.out.println("Course already exists");
                    return;
                }
                if (userRepo.findById(instructorId) != null) {
                    courseRepository.save(course);
                    // notify everyone
                    List<User> everyone=userRepo.findAll();
                    List<Integer> pass=new ArrayList<>();
                    for(User us: everyone) pass.add(us.getId());
                    Map<String, Object> mp = new HashMap<>();
                    mp.put("notificationContent","New Course added !! Check it out: "+course.getName());
                    mp.put("Students",pass);
                    notificationService.createNotificationforAlist(mp,course.getId());
                }
            }
            else System.out.println("Invalid data");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void uploadMedia(int lessonId, MultipartFile file) throws IOException {
        // تحقق من وجود الدرس
        Optional<Lesson> optionalLesson = lessonRepo.findById(lessonId);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();

            // تحقق من صحة الملف
            if (file != null && !file.isEmpty()) {
                lesson.setContentFile(file.getBytes());
                lesson.setMediaType(file.getContentType());
                // notify all enrolled that the media has been uploaded
                Course course=lesson.getCourse();
                notificationService.createNotificationforALL("New Media has been Uploaded for the course: "+course.getName(),course.getId());

            } else {
                throw new IllegalArgumentException("Media file is required.");
            }

            lessonRepo.save(lesson);
        } else {
            throw new EntityNotFoundException("Lesson with ID " + lessonId + " not found.");
        }
    }




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

            // notify all enrolled that a new lesson has been added :D
            notificationService.createNotificationforALL("New Lesson has been added for the course: "+course.getName(),course.getId());


        } else {
            throw new EntityNotFoundException("Course with ID " + lesson.getCourse().getId() + " not found.");
        }
    }

    public void updateCourse(@Autowired Course course) {
        try{
            for(Course c : courseRepository.findAll()) {
                if(c.getId() == course.getId()) {
                    System.out.println("Found");
                    if(course.getName() != null) {
                        c.setName(course.getName());
//                        notificationService.createNotificationforALL("Course name has been changed to: "+c.getName(),course.getId());
                    }
                    // mn a2y fakes 3la kol kbyra w so8ya notification for all , msh lazem
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

    public byte[] getLessonMedia(int lessonId) {
        Lesson lesson = lessonRepo.findById(lessonId)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found with ID: " + lessonId));
        if (lesson.getContentFile() == null || lesson.getContentFile().length == 0) {
            throw new IllegalArgumentException("No media found for the given lesson.");
        }
        return lesson.getContentFile();
    }


    public void deleteCourse(@Autowired int id) {
        try{
            for(Course c : courseRepository.findAll()) {
                if(c.getId() == id) {
                    notificationService.createNotificationforALL("this course has been deleted :("+c.getName(),c.getId());
                    courseRepository.delete(c);
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    public Course getCourseById(int id){
        return courseRepository.findById(id).orElse(null);
    }
}


//package com.LMSAssginment.Code.BusinessLayers.Services;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
//import com.LMSAssginment.Code.DateLayers.Model.Course.CourseDTO;
//import com.LMSAssginment.Code.DateLayers.Model.Course.Lesson;
//import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
//import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
//import com.LMSAssginment.Code.DateLayers.Repos.LessonRepo;
//import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;
//import jakarta.persistence.EntityNotFoundException;
//
//@Service
//public class CourseService {
//    private final InstructorCourseRepo courseRepository;
//    private final UserRepo instructorCourseRepo;
//    private final LessonRepo lessonRepo;
//    public CourseService(InstructorCourseRepo courseRepository, UserRepo instructorCourseRepo, LessonRepo lessonRepo) {
//        this.courseRepository = courseRepository;
//        this.instructorCourseRepo = instructorCourseRepo;
//        this.lessonRepo = lessonRepo;
//    }
//
//    public void addCourse(@Autowired Course course,@Autowired int instructorId) {
//        try{
//            if (instructorId != 0) {
//                if (instructorCourseRepo.findById(instructorId) != null) {
//                    courseRepository.save(course);
//                }
//            }
//            else System.out.println("Instructor not found");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    @Autowired
//    public List<CourseDTO> viewAll() {
//        List<Course> courses = courseRepository.findAll();
//        List<CourseDTO> courseDTOs = new ArrayList<>();
//        for (Course course : courses) {
//            Instructor instructor = course.getInstructor();  // Get Instructor for the Course
//            CourseDTO courseDTO = new CourseDTO(
//                course.getId(),
//                course.getName(),
//                course.getMaxNumberOfStudent(),
//                course.getDescription(),
//                instructor.getName() // Add only the instructor's ID
//            );
//            courseDTOs.add(courseDTO);
//        }
//        return courseDTOs;
//    }
//
//    public void addLesson(@Autowired Lesson lesson) {
//        if (lesson.getCourse() == null || lesson.getCourse().getId() == 0) {
//            throw new IllegalArgumentException("Lesson must have an associated course.");
//        }
//        Optional<Course> optionalCourse = courseRepository.findById(lesson.getCourse().getId());
//        if (optionalCourse.isPresent()) {
//            Course course = optionalCourse.get();
//            lesson.setCourse(course);
//            course.addLessons(lesson);
//            lessonRepo.save(lesson);
//            courseRepository.save(course);
//        } else {
//            throw new EntityNotFoundException("Course with ID " + lesson.getCourse().getId() + " not found.");
//        }
//    }
//
//    public void updateCourse(@Autowired Course course) {
//        try{
//            for(Course c : courseRepository.findAll()) {
//                if(c.getId() == course.getId()) {
//                    System.out.println("Found");
//                    if(course.getName() != null)
//                        c.setName(course.getName());
//                    if(course.getMaxNumberOfStudent() != 0)
//                        c.setMaxNumberOfStudent(course.getMaxNumberOfStudent());
//                    if(course.getDescription() != null)
//                        c.setDescription(course.getDescription());
//                    if(course.getInstructor() != null)
//                        c.setInstructor(course.getInstructor());
//                    if(course.getLessons() != null)
//                        c.setLessons(course.getLessons());
//                    if(course.getStudentCourses() != null)
//                        c.setStudentCourses(course.getStudentCourses());
//                    courseRepository.save(c);
//                }
//            }
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//    }
//
//    public void deleteCourse(@Autowired int id) {
//        try{
//            for(Course c : courseRepository.findAll()) {
//                if(c.getId() == id) {
//                    courseRepository.delete(c);
//                }
//            }
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//    }
//}
//
//
//

