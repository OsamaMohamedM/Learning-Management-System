package com.LMSAssginment.Code.BusinessLayers.Services;

import com.LMSAssginment.Code.DateLayers.Model.Course.Assessment;
import com.LMSAssginment.Code.DateLayers.Model.Course.Course;
import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import com.LMSAssginment.Code.DateLayers.Repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.management.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AssessmentServices {
    @Autowired
    private AssessmentRepository assessmentRepository;
    @Autowired
    private InstructorCourseRepo courseRepo;
    @Autowired
    private QuestionsRepo questionsRepo;

    @Autowired
    private McqQuestionRepo mcqQuestionRepo;

    @Autowired
    private ShortAnswerQuestionRepo shortAnswerQuestionRepo;

    @Autowired
    private TrueAndFalseQuestionRepo trueAndFalseQuestionRepo;

    public Question getQuestionById(int question_id){
        return  questionsRepo.findById(question_id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course getCourseById(int course_id){
        return  courseRepo.findById(course_id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Assessment createAssessment(Course course, Map<String, Object> assessment){
        //  int totalGrades, Double duration, Date startDate, String type,
        //   course, List<Question> questions , int totalNumberOfQuestions
        // he will give us a list of question ids , so I will turn each to a question object
        Boolean random = (Boolean) assessment.get("random");
        int numberOfQuestion =  (int) assessment.get("totalNumberOfQuestions");
        List<Question> pass = new ArrayList<>();
        if (!random){
            List<Integer> tmp = (List<Integer>) assessment.get("questions");
            for (int id : tmp) {
                Question current = getQuestionById(id);
                pass.add(current);
            }
        }
        else{
            int mcqQuestion = numberOfQuestion / 2;
            int shortAnswerQuestion = numberOfQuestion / 4;
            int trueAndFalseQuestion = numberOfQuestion - (mcqQuestion + shortAnswerQuestion);

            List<Question> mcqQuestions = getRandomQuestions("mcq", mcqQuestion);
            pass.addAll(mcqQuestions);
            List<Question> trueAndFalse = getRandomQuestions("sa", trueAndFalseQuestion);
            pass.addAll(trueAndFalse);
            List<Question> shortAnswer = getRandomQuestions("tf", shortAnswerQuestion);
            pass.addAll(shortAnswer);

        }
        try {
            String sth = (String) assessment.get("startDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date passs = sdf.parse(sth);
            System.out.println("Parsed Date: " + passs);

            Assessment assessment1 = new Assessment(
                    (int) assessment.get("totalGrades"),
                    (double) assessment.get("duration"),
                    passs,
                    (String) assessment.get("type"),
                    course,
                    pass,
                    numberOfQuestion
            );

            return assessmentRepository.save(assessment1);
        } catch (ParseException e) {
            System.out.println("Error parsing the date: " + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("Error casting the value to String: " + e.getMessage());
        }
        return null;

    }


    public List<Question> getRandomQuestions(String questionType, int num) {
        // Retrieve random questions from the repository
        List<Question> tmp = questionsRepo.getRandomQuestions(questionType, num);

        // Shuffle the list to randomize the order
        Collections.shuffle(tmp);

        // Return the first 'num' elements from the shuffled list
        return tmp.subList(0, Math.min(num, tmp.size()));
    }




}
