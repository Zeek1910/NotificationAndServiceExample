package zeek1910.com.notificationandserviceexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button, button2;

    private BroadcastReceiver broadcastReceiver;

    public static final String TIME = "TIME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("devcpp",""+intent.getIntExtra(NotificationService.KEY_PROGRESS,0));
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(NotificationService.BROADCAST_ACTION));


        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, NotificationService.class).putExtra(TIME,10));
                //startService(new Intent(MainActivity.this, NotificationService.class).putExtra(TIME,));
                //startService(new Intent(MainActivity.this, NotificationService.class).putExtra(TIME,8));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}