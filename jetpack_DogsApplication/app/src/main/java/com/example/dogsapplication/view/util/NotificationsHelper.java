package com.example.dogsapplication.view.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dogsapplication.R;
import com.example.dogsapplication.view.view.MainActivity;

public class NotificationsHelper {

    private static final String CHANNEL_ID = "Dogs channel id";
    private static final int NOTIFICATION_ID = 123;

    /* singleton pattern */
    private static NotificationsHelper instance;
    private Context context;

    private NotificationsHelper(Context context) {
        this.context = context;
    }

    public static NotificationsHelper getInstance(Context context) {
        if(instance == null) {
            instance = new NotificationsHelper(context);
        }
        return instance;
    }

    public void createNotification() {
        createNotificationChannel();

        Intent intent = new Intent(context, MainActivity.class);    //notification 클릭시 이동 페이지 : 어플리케이션 메인
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        /* The setFlags() method shown above helps preserve the user's expected navigation experience after they open your app via the notification
        * */
        //FLAG_ACTIVITY_NEW_TASK : 새로운 어플리케이션으로 오픈 | 스택에 쌓여있는 엑티비티들을 클리어하고 매인 엑티비티를 띄운다.

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.dog_icon)
                .setLargeIcon(icon)
                .setContentTitle("Dogs retrieved")
                .setContentText("this is a notification to let you know that the dog info has been retrieved")
                .setStyle(
                        new NotificationCompat.BigPictureStyle()
                        .bigPicture(icon)   //expanded 되었을때 보이는 그림
                        .bigLargeIcon(null)
                )
                .setContentIntent(pendingIntent)    //유저가 notification 클릭하면 이동할 곳 정하는 intent
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification);
    }

    /* notification 만들기 전에, channel 생성
    * channel id : 안드로이드 설정 > 알림 > DogsApplication 알림에서 확인할 수 있는 알림 내용
    * description : 알림 내용 클릭하면 확인할 수 있는 알림 디테일
    * importance : 중요도 설정
    *  */
    private void createNotificationChannel() {
        /* version o 이상일 때만 channel 생성 */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = CHANNEL_ID;
            String description = "Dogs retrieved notifications channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
