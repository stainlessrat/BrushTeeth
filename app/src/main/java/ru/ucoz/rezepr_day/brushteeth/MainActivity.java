package ru.ucoz.rezepr_day.brushteeth;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this, R.raw.first_message_audio);

        ImageView img_btn_start = findViewById(R.id.image_view_caries);

        img_btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, StartActivity.class);
                if(player.isPlaying()) player.stop();
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        player.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player.isPlaying()) {
            player.stop();
        }
    }
}
