package com.example.audioplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
	final int maxVolume = 15;
	PlayerService mService;

	statusOfMusic statusMusic;

	BroadcastReceiver mResiver;

	public final static String BROADCAST_ACTION = "PlayerFlag";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonPlayPause = (Button) findViewById(R.id.buttonPlayPause);
		textStatusPlayer = (TextView) findViewById(R.id.textStatusPlayer);

		SeekBar seekBarVolume = (SeekBar) findViewById(R.id.seekBarVolumeLevel);
		seekBarVolume.setOnSeekBarChangeListener(this);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
		seekBarVolume.setProgress(100);

		ButtonPlayStopOnClick();

		startService();

		Recive();

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(this.getClass().getName(), "RESUME");

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onStop() {

		super.onStop();

	}

	private ServiceConnection mConnection2 = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {

			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			Log.v(this.getClass().getName(), "Service connected2");

		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.v(this.getClass().getName(), "Service Disconnected2");
			mService = null;
		}
	};

	@Override
	protected void onDestroy() {

		Intent intent = new Intent(MainActivity.this, PlayerService.class);
		bindService(intent, mConnection2, Context.BIND_AUTO_CREATE);
		mConnection2 = mConnection;

		unbindService(mConnection);

		unregisterReceiver(mResiver);

		startService();

		mConnection = mConnection2;
		unbindService(mConnection2);

		super.onDestroy();

	}

	public void Recive() {
		mResiver = new BroadcastReceiver() {

			public void onReceive(Context context, Intent intent) {

				buttonPlayPause.setText(R.string.ButtonTextPlay);
				textStatusPlayer.setText(R.string.StatusIdle);

			}

		};
		IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
		registerReceiver(mResiver, intentFilter);
	}

	public void setStatusText() {
		if (mService.statusMusic == statusOfMusic.pause) {

			buttonPlayPause.setText(R.string.ButtonTextPlay);
			textStatusPlayer.setText(R.string.StatusPause);

		} else if (mService.statusMusic == statusOfMusic.play) {

			buttonPlayPause.setText(R.string.ButtonTextPause);
			textStatusPlayer.setText(R.string.StatusPlay);
		}
	}

	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {

			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			Log.v(this.getClass().getName(), "Service connected");
			setStatusText();

		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.v(this.getClass().getName(), "Service Disconnected");
			mService = null;
		}
	};

	public void startService() {
		Intent intent = new Intent(MainActivity.this, PlayerService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

		Log.v(this.getClass().getName(), "StartService");
	}

	public void ButtonPlayStopOnClick()

	{

		OnClickListener oclBtnOk = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.buttonPlayPause:
					mService.PlayPausePlayer();
					Log.v(this.getClass().getName(), "onClick");

					setStatusText();
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
