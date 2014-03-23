package com.example.audioplayer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service implements OnCompletionListener {

	statusOfMusic statusMusic = statusOfMusic.idle;

	AudioManager audioManager;
	MediaPlayer mediaPlayer;

	public String Play = "play";
	public String Idle = "idle";
	public String Pause = "pause";

	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.v(this.getClass().getName(), "������ 2");
		return mBinder;

	}

	@Override
	public void onCreate() {
		mediaPlayer = MediaPlayer.create(this, R.raw.a);
		Log.v(this.getClass().getName(), "�� ����� 2");
	}

	public String c() { // ���������� ������ ������� ������
		String s = "Play";
		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {

			Log.v(this.getClass().getName(), "���� ���� ������");
			s = "Play";

		} else if (statusMusic == statusOfMusic.play) {

			Log.v(this.getClass().getName(), "���� ���� ������");
			s = "Pause";
		}
		return s;
	}

	public String d() { // ���������� ������ ��� ������� ����� ������ ������
		String s = "idle";

		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {

			Log.v(this.getClass().getName(), "���� ���� ������");
			s = "Pause";

		} else if (statusMusic == statusOfMusic.play) {

			Log.v(this.getClass().getName(), "���� ���� ������");
			s = "Play";
		}

		return s;
	}

	public void a() { // ������������� ������, ����� ��� � � �

		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {
			statusMusic = statusOfMusic.play;
			Log.v(this.getClass().getName(), "���� ����");
			if (!mediaPlayer.isPlaying())
				mediaPlayer.start();

		} else if (statusMusic == statusOfMusic.play) {
			statusMusic = statusOfMusic.pause;
			Log.v(this.getClass().getName(), "���� ����");
			if (mediaPlayer.isPlaying())
				mediaPlayer.pause();

		}
		mediaPlayer.setOnCompletionListener(this);
	}

	public void b() {
		statusMusic = statusOfMusic.pause;
		if (mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}

	public class LocalBinder extends Binder {
		PlayerService getService() {

			Log.v(this.getClass().getName(),
					"���� �� �������� � ����� ������ �����������");
			return PlayerService.this;

		}
	}

	@Override
	public void onDestroy() {

		Log.v(this.getClass().getName(), "������ ������� ����� 2");
	}

	@Override
	public void onStart(Intent intent, int startid) {

		Log.v(this.getClass().getName(), "�������� 2");
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		statusMusic = statusOfMusic.idle;

	}
}