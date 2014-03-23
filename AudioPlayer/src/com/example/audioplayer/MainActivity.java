package com.example.audioplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.audioplayer.PlayerService.LocalBinder;

public class MainActivity extends Activity implements OnCompletionListener,
		OnSeekBarChangeListener {

	public int status = 0; // 0-Idle,1-Play,2-Pause

	Button button1;
	TextView textView1;

	AudioManager audioManager;
	public statusOfMusic statusMusic = statusOfMusic.idle;
	PlayerService mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
		ololo();
		// f();

		seekBar.setOnSeekBarChangeListener(this);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 15, 0);
		seekBar.setProgress(0);

	}

	@Override
	protected void onStart() {
		super.onStart();
		// Bind to LocalService
		Intent intent = new Intent(MainActivity.this, PlayerService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			// We've bound to LocalService, cast the IBinder and get
			// LocalService instance
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();

		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {

		}
	};

	public void f() {
		startService(new Intent(MainActivity.this, PlayerService.class));
	}

	public void ololo()

	{
		button1 = (Button) findViewById(R.id.button1);
		textView1 = (TextView) findViewById(R.id.textView1);

		OnClickListener oclBtnOk = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.button1:
					mService.a();
					Log.v(this.getClass().getName(),
							"onClick: Starting service.");
					/*
					 * Log.v(this.getClass().getName(),
					 * "onClick: Starting service."); startService(new
					 * Intent(MainActivity.this, PlayerService.class));
					 */
					/*
					 * startService(new Intent(MainActivity.this,
					 * PlayerService.class)); // statusOfMusic statusMusic =
					 * statusOfMusic.idle; if (statusMusic == statusOfMusic.idle
					 * || statusMusic == statusOfMusic.pause) { statusMusic =
					 * statusOfMusic.play; button1.setText("Pause");
					 * textView1.setText("Status: Play");
					 * 
					 * } else if (statusMusic == statusOfMusic.play) {
					 * statusMusic = statusOfMusic.pause;
					 * button1.setText("Play");
					 * textView1.setText("Status: Pause");
					 * 
					 * }
					 */

					/*
					 * switch (statusMusic) { case idle:
					 * 
					 * 
					 * 
					 * textView1.setText("play"); button1.setText("pause");
					 * statusMusic = statusOfMusic.play; break; case play:
					 * 
					 * //stopService(new Intent(MainActivity.this,
					 * PlayerService.class)); button1.setText("play");
					 * textView1.setText("idle"); statusMusic =
					 * statusOfMusic.idle; break; case pause:
					 * button1.setText("play"); textView1.setText("pause");
					 * statusMusic = statusOfMusic.play;
					 * 
					 * break; }
					 */

					break;

				}
			}
		};

		button1.setOnClickListener(oclBtnOk);
	}

	@Override
	public void onBackPressed() {

		Log.v(this.getClass().getName(), "onClick: Stopping service.");
		stopService(new Intent(this, PlayerService.class));
		this.moveTaskToBack(true);
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
