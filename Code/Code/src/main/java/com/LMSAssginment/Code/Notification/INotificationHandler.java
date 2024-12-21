package com.LMSAssginment.Code.Notification;

import com.LMSAssginment.Code.DateLayers.Model.User;

public interface INotificationHandler {

     void Notify(NotificationInfo info);

     void AddSubscriber(User user);

     void RemoveSubscriber(User user);
}
