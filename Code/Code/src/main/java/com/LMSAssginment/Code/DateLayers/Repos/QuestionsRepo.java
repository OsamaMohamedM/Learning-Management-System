package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Questions.Question;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;




public interface QuestionsRepo extends JpaRepository<Question,Integer>{






    @Query(value = "SELECT a FROM Question a WHERE a.questionType=:questionType")
    List<Question> getRandomQuestions(@Param("questionType") String questionType, @Param("num") int num);

//    @Query(value = "SELECT * FROM Orders WHERE Amount BETWEEN :startAmt AND :endAmt" , nativeQuery=true)
//    List<OrderEntity> findOrdersBy(@Param("startAmt") int startAmt, @Param("endAmt") int endAmt);
//
//
//    // Repository Method with Manual Pagination Parameters
//    @Query(value = "SELECT * FROM users WHERE status = 'active' LIMIT :limit OFFSET :offset", nativeQuery = true)
//    List<User> findActiveUsersWithPagination(@Param("limit") int limit, @Param("offset") int offset);
}


/*
    @Query(value = "SELECT a FROM Notification a WHERE a.user.id = :user_id")
    List<Notification> findNotificationsByUserId(@Param("user_id") int user_id);


    @Query(value = "SELECT a FROM Notification a WHERE a.user.id = :user_id and a.read = false")
    List<Notification> getUnreadNotification(@Param("user_id") int user_id);

    @Modifying
    @Transactional
    @Query("UPDATE Notification a SET a.read = true WHERE a.id = :NOTid")
    void changeToRead(@Param("NOTid") int NOTid);

*/