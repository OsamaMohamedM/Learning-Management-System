package  com.LMSAssginment.Code.BusinessLayers.Services;
import java.util.ArrayList;
import java.util.List;

import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.LMSAssginment.Code.DateLayers.Model.Student.Student;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentCourse;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentDTO;
import com.LMSAssginment.Code.DateLayers.Repos.InstructorCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.StudentCourseRepo;
import com.LMSAssginment.Code.DateLayers.Repos.UserRepo;

@Service
public class EnrollService {
    @Autowired
    private final StudentCourseRepo repository;
    @Autowired
    private final InstructorCourseRepo courseRepository;

    @Autowired
    private final NotificationService notificationService;

    public EnrollService(StudentCourseRepo repository, InstructorCourseRepo courseRepository,NotificationService notificationService) {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.notificationService=notificationService;
    }
    @Autowired
    private UserRepo studentRepo;
    public void enroll(int courseId, int studentId) {
        try{
            if (studentId != 0) {
                //????//???? why not  just query?
                if (studentRepo.findById(studentId) != null) {
                    List<Student> students = studentRepo.findAllStudents();
                    Student student = new Student();
                    for (Student s : students) {
                        if (s.getId() == studentId) {
                            System.out.println("student id found");
                            student = s;
                            break;
                        }
                    }
                    StudentCourse studentCourse = new StudentCourse();
                    Course crs=courseRepository.findById(courseId).orElse(null);
                    studentCourse.setCourse(crs);
                    studentCourse.setStudent(student);
                    studentCourse.setEnrollmentDate(java.time.LocalDateTime.now());
                    repository.save(studentCourse);


                    notificationService.notifyInstructor(studentId,crs);

                }
            }
            else System.out.println("Student not found");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public List<StudentDTO> viewAll(int courseId) {
        List<StudentCourse> studentCourses = repository.findAll();
        List<StudentDTO> students = new ArrayList<>();
        for (StudentCourse studentCourse : studentCourses) {
            if (studentCourse.getCourse().getId() == courseId) {
                StudentDTO studentDTO = new StudentDTO(
                        studentCourse.getStudent().getId(),
                        studentCourse.getStudent().getName(),
                        studentCourse.getStudent().getEmail(),
                        studentCourse.getStudent().getGender(),
                        studentCourse.getStudent().getBirthDate(),
                        studentCourse.getStudent().getGpa()
                );
                students.add(studentDTO);

            }
        }
        return students;
    }

    public void drop(@Autowired int courseId,@Autowired int studentId) {
        try {
            if (studentId != 0) {
                List<StudentCourse> studentCourses = repository.findAll();
                for (StudentCourse sc : studentCourses) {
                    if (sc.getCourse().getId() == courseId && sc.getStudent().getId() == studentId) {
                        repository.delete(sc); // Delete the exact entity
                        System.out.println("Enrollment deleted successfully.");
                        return;
                    }
                }
                System.out.println("Enrollment not found.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
