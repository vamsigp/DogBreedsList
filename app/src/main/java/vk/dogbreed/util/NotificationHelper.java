package vk.dogbreed.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import vk.dogbreed.R;
import vk.dogbreed.view.MainActivity;

public class NotificationHelper {

    private static final String CHANNEL_ID = "dog channel id";
    private static final String CHANNEL_GROUP = "ONE";
    private static final int NOTIFICATION_ID = 123;

    private static NotificationHelper instance;
    private Context context;

    private NotificationHelper(Context context) {
        this.context = context;
    }

    public static NotificationHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationHelper(context);
        }
        return instance;
    }

    public void createNotification() {
        createChannel();

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.dog);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.dog_icon)
                .setLargeIcon(bitmap)
                .setContentTitle("Dogs Retrieved")
                .setContentText("Notification text says dog list retreived")
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification);
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "Dog list retrieval notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;


            NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(CHANNEL_GROUP, CHANNEL_GROUP);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                notificationChannelGroup.setDescription("Notification group 1");
            }

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            channel.canShowBadge();
            channel.setDescription(description);
            channel.setGroup(CHANNEL_GROUP);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannelGroup(notificationChannelGroup);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
