package com.vavisa.monasabatcom.notification;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class ExampleNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;
        String notificationID = notification.payload.notificationID;
        String title = notification.payload.title;
        String body = notification.payload.body;
        String smallIcon = notification.payload.smallIcon;
        String largeIcon = notification.payload.largeIcon;
        String bigPicture = notification.payload.bigPicture;
        String smallIconAccentColor = notification.payload.smallIconAccentColor;
        String sound = notification.payload.sound;
        String ledColor = notification.payload.ledColor;
        int lockScreenVisibility = notification.payload.lockScreenVisibility;
        String groupKey = notification.payload.groupKey;
        String groupMessage = notification.payload.groupMessage;
        String fromProjectNumber = notification.payload.fromProjectNumber;
        String rawPayload = notification.payload.rawPayload;

        String customKey;

       // LogUtils.Print("OneSignalExample", "NotificationID received: " + notificationID);

        if (data != null) {
            customKey = data.optString("customkey", null);
        //    if (customKey != null)
             //   LogUtils.Print("OneSignalExample", "customkey set with value: " + customKey);
        }

    }
}
