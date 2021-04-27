package zeek1910.com.notificationandserviceexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.TimeUnit;

public class NotificationService extends Service {

    public static final String CHANNEL_ID = "channelId";
    public static final String KEY_PROGRESS = "KEY_PROGRESS";
    public static final String BROADCAST_ACTION = "myAction";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Log.d("devcpp","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("devcpp","onStartCommand");
        Log.d("devcpp","startId = "+ startId);

        int time = 0;
        if (intent != null){
            time = intent.getIntExtra(MainActivity.TIME, 0);
        }
        final int time1 = time;

        Thread thread = new Thread(new Runnable() {
            int progress = 0;
            @Override
            public void run() {
                while(progress < time1){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress++;
                    sendBroadcast(new Intent(BROADCAST_ACTION).putExtra(KEY_PROGRESS,progress));
                }
                showNotification("Title", "Text",startId);
            }
        });
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }


    private void showNotification(String title, String text,int id) {


        //Content Intent
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent1 = new Intent(this, PhotoActivity.class);
        PendingIntent photoIntent = PendingIntent.getActivity(this, 1,intent1,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);
        builder.setContentIntent(contentIntent);
        builder.addAction(R.drawable.ic_baseline_camera_alt_24,"Take Photo",photoIntent);
        builder.addAction(R.drawable.ic_baseline_call_24,"Make a call",photoIntent);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }


    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Description");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
