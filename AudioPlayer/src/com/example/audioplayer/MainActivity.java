package com.example.audioplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
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

public class MainActivity extends Activity implements OnSeekBarChangeListener {

	Button buttonPlayPause;
	TextView textStatusPlayer;

	AudioManager audioManager;
	final int maxVolume = 15; // константа максимального значения громкости
	PlayerService mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonPlayPause = (Button) findViewById(R.id.buttonPlayPause);
		textStatusPlayer = (TextView) findViewById(R.id.textStatusPlayer);

		SeekBar seekBarVolume = (SeekBar) findViewById(R.id.seekBarVolumeLevel);
		seekBarVolume.setOnSeekBarChangeListener(this);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 15, 0);
		seekBarVolume.setProgress(100);

		ButtonPlayStopOnClick();
		startService();

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {

			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			Log.v(this.getClass().getName(), "Service connected");

			// передаются из сервиса значения текстовых полей
			if (mService.statusMusic == mService.statusMusic.play
					|| mService.statusMusic == mService.statusMusic.pause) {
				textStatusPlayer.setText(mService.textStatusOfPlayer());
				buttonPlayPause.setText(mService.textButtonPlayPause());
			} else {
				textStatusPlayer.setText(R.string.StatusIdle);
				buttonPlayPause.setText(mService.textButtonPlayPause());
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.v(this.getClass().getName(), "Service Disconnected");
		}
	};

	public void startService() {
		Intent intent = new Intent(MainActivity.this, PlayerService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		Log.v(this.getClass().getName(), "сработал метод запуска Service");
	}

	// выполняется при нажатии кнопки
	public void ButtonPlayStopOnClick()

	{

		OnClickListener oclBtnOk = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.buttonPlayPause:
					mService.PlayPausePlayer();
					Log.v(this.getClass().getName(), "onClick");
					textStatusPlayer.setText(mService.textStatusOfPlayer());
					buttonPlayPause.setText(mService.textButtonPlayPause());
					break;

				}
			}
		};

		buttonPlayPause.setOnClickListener(oclBtnOk);
	}

	@Override
	public void onBackPressed() {

		Log.v(this.getClass().getName(), "Stop service");
		stopService(new Intent(MainActivity.this, PlayerService.class));
		mService.PlayerPause();
		this.moveTaskToBack(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int currentSeekBar, boolean arg2) {
		// TODO Auto-generated method stub

		final int setVolume = (int) (currentSeekBar * maxVolume / 100);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, setVolume, 0);

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
