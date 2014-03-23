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

	Button button1;
	TextView textView1;

	AudioManager audioManager;

	PlayerService mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1 = (Button) findViewById(R.id.button1);
		textView1 = (TextView) findViewById(R.id.textView1);
		Log.v(this.getClass().getName(), "�������� ������� � �����");

		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setOnSeekBarChangeListener(this);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 15, 0);
		seekBar.setProgress(100);

		ololo();
		f();

	}

	@Override
	protected void onStart() {
		super.onStart();
		// Bind to LocalService
		Log.v(this.getClass().getName(), "�������� ������� � �����");

	}

	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			// We've bound to LocalService, cast the IBinder and get
			// LocalService instance
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			Log.v(this.getClass().getName(), "����� �� ����� � ��������");

			if (mService.statusMusic == mService.statusMusic.play
					|| mService.statusMusic == mService.statusMusic.pause) {
				textView1.setText(mService.d());
				button1.setText(mService.c());
			} else {
				textView1.setText("idle");
				button1.setText(mService.c());
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.v(this.getClass().getName(), "������ ���������");
		}
	};

	public void f() {
		Intent intent = new Intent(MainActivity.this, PlayerService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		Log.v(this.getClass().getName(), "�������� ����� �");
	}

	public void ololo()

	{

		OnClickListener oclBtnOk = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.button1:
					mService.a();
					Log.v(this.getClass().getName(),
							"onClick: Starting service ������ �������.");
					textView1.setText(mService.d());
					button1.setText(mService.c());
					break;

				}
			}
		};

		button1.setOnClickListener(oclBtnOk);
	}

	@Override
	public void onBackPressed() {

		Log.v(this.getClass().getName(), "onClick: Stopping service.");
		stopService(new Intent(MainActivity.this, PlayerService.class));
		mService.b();
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
