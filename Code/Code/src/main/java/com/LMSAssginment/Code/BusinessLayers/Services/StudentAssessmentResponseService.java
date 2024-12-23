package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.DateLayers.Model.Answers.FileAnswer;
import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.AssessmentGrade;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Instructor.Instructor;
import com.LMSAssginment.Code.DateLayers.Model.Questions.McqQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Model.Questions.ShortAnswerQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Questions.TrueAndFalseQuestion;
import com.LMSAssginment.Code.DateLayers.Model.Student.StudentAssessmentResponse;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentAssessmentResponseService {

    @Autowired
    StudentAssessmentRepo studentAssessmentRepo;

    @Autowired
    AssessmentGradesRepo assessmentGradesRepo;
    @Autowired
    InstructorCourseRepo instructorCourseRepo;

    @Autowired
    private NotificationService notificationService;


    @Autowired
    private McqQuestionRepo mcqQuestionRepo;

    @Autowired
    private ShortAnswerQuestionRepo shortAnswerQuestionRepo;

    @Autowired
    private TrueAndFalseQuestionRepo trueAndFalseQuestionRepo;

    public Assessment getAssessment(int assessmentId, int courseId) {
        return studentAssessmentRepo.findAssessmentById(assessmentId, courseId);
    }

    public McqQuestion getMcqQuestionbyQuestionId(int id) {
        return mcqQuestionRepo.findMcqQuestionbyQuestionId(id);
    }

    public ShortAnswerQuestion getShortAnswerQuestionbyQuestionId(int id) {
        return shortAnswerQuestionRepo.findShortAnswerQuestionbyQuestionId(id);
    }

    public TrueAndFalseQuestion getTrueAndFalseQuestionbyQuestionId(int id) {
        return trueAndFalseQuestionRepo.findTrueAndFalseQuestionbyQuestionId(id);
    }

    public AssessmentGrade saveAssessmentGrade(AssessmentGrade assessmentGrade) {
        return assessmentGradesRepo.save(assessmentGrade);
    }

    public void saveAssignment(MultipartFile multipartFile, Course course, int user_id, int assessment_id, int course_id, Assessment assessment) throws Exception {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        FileAnswer fileAnswer = new FileAnswer(fileName, multipartFile.getContentType(), multipartFile.getBytes(), course);
        AssessmentGrade assessmentGrade = new AssessmentGrade(user_id, assessment_id, course_id, 0);
        List<FileAnswer> fileAnswers = new ArrayList<>();
        fileAnswers.add(fileAnswer);
        StudentAssessmentResponse studentAssessmentResponse = new StudentAssessmentResponse(assessment, user_id, course_id, fileAnswers);
        studentAssessmentRepo.save(studentAssessmentResponse);
        assessmentGradesRepo.save(assessmentGrade);

    }

    public List<String> getQuestionAnswer(Assessment assessment){
        List<String> questionsAnswer = new ArrayList<>();
        for (Question q : assessment.getQuestions()){
            if (q.getQuestionType().equals("mcq")){
                McqQuestion mcqQuestion = getMcqQuestionbyQuestionId(q.getId());
                questionsAnswer.add(mcqQuestion.getAnswer().toString().charAt(0) + "");
            }
            else if (q.getQuestionType().equals("tf")){
                TrueAndFalseQuestion trueAndFalseQuestion = getTrueAndFalseQuestionbyQuestionId(q.getId());
                questionsAnswer.add(trueAndFalseQuestion.getAnswer() ? "true" : "false");
            }
            else{
                ShortAnswerQuestion shortAnswerQuestion = getShortAnswerQuestionbyQuestionId(q.getId());
                questionsAnswer.add(shortAnswerQuestion.getAnswer());
            }
        }
        return questionsAnswer;
    }


    public List<String> showAssessment(int assessment_id, int course_id) {
        Assessment assessment =  getAssessment(assessment_id, course_id);
        List<String> result = new ArrayList<>();
        result.add(assessment.getType().toString() + "#" + assessment.getId());
        result.add("Start date " + assessment.getStartDate().toString());
        result.add("Duration " + assessment.getDuration().toString());
        result.add("Total grades " + assessment.getTotalGrades());
        int grades = assessment.getTotalGrades() / assessment.getTotalNumberOfQuestions();
        result.add("Total number of question is " + assessment.getTotalGrades() + " so each question will have about " + grades + " grade and bones = " + (assessment.getTotalGrades() - (grades * assessment.getTotalNumberOfQuestions())) + " grade");
        int counter = 1;
        for (Question q : assessment.getQuestions()) {
            result.add(counter + ") " + q.getText());
            if (q.getQuestionType().equals("mcq")) {
                McqQuestion mcqQuestion = getMcqQuestionbyQuestionId(q.getId());
                result.add("a) " + mcqQuestion.getOptionA());
                result.add("b) " + mcqQuestion.getOptionB());
                result.add("c) " + mcqQuestion.getOptionC());
                result.add("d) " + mcqQuestion.getOptionD());
            } else if (q.getQuestionType().equals("tf")) {
                result.add("1) True");
                result.add("2) False");
            } else {
                result.add("........................................");
            }

            counter++;
        }
        return result;
    }


    public String submitQuiz(int user_id, int assessment_id, int course_id, Map<String, String> studentAnswers){
        Assessment assessment =  getAssessment(assessment_id, course_id);
        List<String> questionsAnswer = getQuestionAnswer(assessment);
        String result;
        int count = 1, totalNumberOfGrades = 0, correct = 0;
        AssessmentGrade assessmentGrade;
        for (String str : questionsAnswer){
            String questionNumber = "q" + count;
            if (str.equals(studentAnswers.get(questionNumber))){
                totalNumberOfGrades += (assessment.getTotalGrades() / assessment.getTotalNumberOfQuestions());
                correct++;
            }
            count++;
        }
        assessmentGrade = new AssessmentGrade(user_id, assessment_id, course_id, totalNumberOfGrades);
        result = "Your correct questions is: " + correct + ", Your grades is: " + totalNumberOfGrades;
        saveAssessmentGrade(assessmentGrade);
        return result;
    }

@Modifying
@Transactional
    public AssessmentGrade giveManualFeedback(int assessment_id, int course_id, Map<String, String> instructorFeedback){
        int student_id = Integer.parseInt(instructorFeedback.get("student_id"));
        int grade = Integer.parseInt(instructorFeedback.get("grade"));
        String feed_back = instructorFeedback.get("feed_back");
  assessmentGradesRepo.updateAssessmentGrade(student_id, course_id, assessment_id, grade, feed_back);
         AssessmentGrade x=assessmentGradesRepo.getAssessmentGrade(student_id, course_id, assessment_id);
    Map<String, Object> mp = new HashMap<>();
    Course course= instructorCourseRepo.findById(course_id).orElse(null);
    if(course!=null) {
        mp.put("notificationContent", "" +
                "Feedback: " + feed_back
                + "\n" +
                "Grade: " + grade +
                "\n" + course.getName());

        List<Integer> sth = new ArrayList<>();
        sth.add(student_id);
        mp.put("Students", sth);

        notificationService.createNotificationforAlist(mp, course_id);

    }

    return x;
    }
}
