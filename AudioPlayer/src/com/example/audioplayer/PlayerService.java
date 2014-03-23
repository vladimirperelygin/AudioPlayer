package com.example.audioplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerService extends Service implements OnCompletionListener,
		OnSeekBarChangeListener {

	public int status = 0; // 0-Idle,1-Play,2-Pause

	Button button1;
	TextView textView1;
	AudioManager audioManager;

	// statusOfMusic statusMusic;

	//
	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	@Override
	public void onCreate() {
		// Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
		// Player = MediaPlayer.create(this, R.raw.a);
		// buttonPlay();
		// Player.setLooping(true);

	}

	public void a() {
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.a);
		mediaPlayer.start();
	}

	public class LocalBinder extends Binder {
		PlayerService getService() {
			// Return this instance of LocalService so clients can call public
			// methods
			return PlayerService.this;
		}
	}

	public void buttonPlay() {

		final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.a);
		statusOfMusic statusMusic = statusOfMusic.idle;
		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {
			statusMusic = statusOfMusic.play;

			if (!mediaPlayer.isPlaying())
				mediaPlayer.start();

		} else if (statusMusic == statusOfMusic.play) {
			statusMusic = statusOfMusic.pause;

			if (mediaPlayer.isPlaying())
				mediaPlayer.pause();

		}
		mediaPlayer.setOnCompletionListener(this);

	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onStart(Intent intent, int startid) {
		// Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

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

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		// status = 0;
		// textView1.setText("Status: Idle");
		// button1.setText("Play");
	}
}