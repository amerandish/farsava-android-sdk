package farsava.core.Util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import farsava.core.R;

/**
 * Created by MHK on 19/07/10.
 * www.MHKSoft.com
 */
public class NotificationHelper {

    private static final int NOTIFICATION_ID = 4661;
    private static final String NOTIFICATION_CHANNEL_ID = "46617273617661";
    private static final String NOTIFICATION_CHANNEL_NAME = "FARSAVA";
    private Context mContext;
    private NotificationManager mNotificationManager;
    private Notification.Builder mBuilder;

    public NotificationHelper(Context context) {
        mContext = context;
    }

    /**
     * Create and push the notification.
     */
    public void createNotification(String title, String message) {
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(mContext);

        mBuilder.setSmallIcon(R.drawable.ic_farsava)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setOngoing(true)
                .setOnlyAlertOnce(true);

        // Android O compatibility
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * Update existing notification.
     */
    public void updateNotification(String title, String message) {
        mBuilder.setContentTitle(title)
                .setContentText(message);

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * Delete ongoing notification.
     */
    public void closeNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}