package ru.ucoz.rezepr_day.brushteeth;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView imageViewButton;
    TextView text_view_messages;

    int count_progress = 0; //Изменение прогресс бара
    final int PROGRESS_MAX_COUNT = 90;//Максимальное время таймера
    private Timer timer;
    int my_count = 0;
    boolean my_switsh = false;//переключатель проверяющий включен ли таймер
    boolean repiat_audio = false;

    MediaPlayer player, finish_audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        player = MediaPlayer.create(this, R.raw.second_message_audio);
        finish_audio = MediaPlayer.create(StartActivity.this, R.raw.finish_audio);

        Init();
        imageViewButton.setBackgroundResource(R.drawable.teeth_two);
        timer = new Timer();
        final TimerTask myTimerTask = new MyTimerTask();

        imageViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer = new Timer();
                my_switsh = true;
                player.stop();
                timer.schedule(myTimerTask, 1000, 1000);

                imageViewButton.setClickable(false);
                text_view_messages.setText(R.string.text_final_message);
            }
        });
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run(){
            count_progress++;
            progressBar.setProgress(count_progress);

            runOnUiThread(new Runnable() {//Обновление UI
                @Override
                public void run() {
                    if(my_count == 0){//Отображать первый текст
                        text_view_messages.setText(R.string.text_view_up_teeth);
                        if(!repiat_audio){
                            MediaPlayer first_process = MediaPlayer.create(StartActivity.this, R.raw.first_process_audio);
                            first_process.start();
                            repiat_audio = true;
                        }
                    }
                    if(my_count == 1){//Отображать второй тектс
                        text_view_messages.setText(R.string.text_view_down_teeth);
                        if(repiat_audio){
                            MediaPlayer second_process = MediaPlayer.create(StartActivity.this, R.raw.second_process_audio);
                            second_process.start();
                            repiat_audio = false;
                        }

                    }
                }
            });

            if(count_progress == PROGRESS_MAX_COUNT){//Обновления и перезапуск прогресс бара
                count_progress = 0;
                progressBar.setProgress(count_progress);
                my_count++;
            }
            if(my_count == 2){//Изменение тескта после завершения работы таймера
                text_view_messages.setText(R.string.text_final_message);
                finish_audio.start();
                timer.cancel();
            }
        }
    }

    private void Init() {//Инициализация UI
        progressBar = findViewById(R.id.progress_bar);
        imageViewButton = findViewById(R.id.image_view_brush);
        text_view_messages = findViewById(R.id.btn_text_dialog);
    }

    @Override
    protected void onStart() {
        super.onStart();
        player.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(my_switsh){
            timer.cancel();
            my_switsh = false;
        }
        if(player.isPlaying()) player.stop();
        if(finish_audio.isPlaying()) finish_audio.stop();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player.isPlaying()) player.stop();
        if(finish_audio.isPlaying()) finish_audio.stop();
        System.exit(0);
    }
}
