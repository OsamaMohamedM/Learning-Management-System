//package com.LMSAssginment.Code.DateLayers.Repos;
//
//import com.LMSAssginment.Code.DateLayers.Model.Notification;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface NotificationRepo extends JpaRepository<Notification, Integer> {
//}
package com.LMSAssginment.Code.DateLayers.Repos;

import com.LMSAssginment.Code.DateLayers.Model.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {

    @Query(value = "SELECT a FROM Notification a WHERE a.user.id = :user_id")
    List<Notification> findNotificationsByUserId(@Param("user_id") int user_id);

    @Query(value = "SELECT a FROM Notification a WHERE a.user.id = :user_id and a.read = false")
    List<Notification> getUnreadNotification(@Param("user_id") int user_id);

    @Modifying
    @Transactional

    @Query("UPDATE Notification a SET a.read = true WHERE a.id = :NOTid ")
    void changeToRead(@Param("NOTid") int NOTid);


}
