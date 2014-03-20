package com.example.audioplayer;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnCompletionListener,
		OnSeekBarChangeListener {

	public int status = 0; // 0-Idle,1-Play,2-Pause

	Button button1;
	TextView textView1;
	AudioManager audioManager;
	SeekBar seekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setOnSeekBarChangeListener(this);
		buttonPlay();
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
		seekBar.setProgress(0);

	}

	public void buttonPlay() {

		final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.a);

		textView1 = (TextView) findViewById(R.id.textView1);
		button1 = (Button) findViewById(R.id.button1);

		OnClickListener oclBtnOk = new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (status == 0 || status == 2) {
					status = 1;
					button1.setText("Pause");
					textView1.setText("Status: Play");
					// text();
					if (!mediaPlayer.isPlaying())
						mediaPlayer.start();

				} else if (status == 1) {
					status = 2;
					button1.setText("Play");
					textView1.setText("Status: Pause");

					if (mediaPlayer.isPlaying())
						mediaPlayer.pause();
				}

			}
		};
		mediaPlayer.setOnCompletionListener(this);

		button1.setOnClickListener(oclBtnOk);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		status = 0;
		textView1.setText("Status: Idle");
		button1.setText("Play");
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int currentSeekBar, boolean arg2) {
		// TODO Auto-generated method stub

		final int volume = (int) (currentSeekBar * 15 / 100);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);

	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

}
