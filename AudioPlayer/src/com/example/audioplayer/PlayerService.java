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
		Log.v(this.getClass().getName(), "онбинд 2");
		return mBinder;

	}

	@Override
	public void onCreate() {
		mediaPlayer = MediaPlayer.create(this, R.raw.a);
		Log.v(this.getClass().getName(), "он креет 2");
	}

	public String c() { // возвращает строку статуса кнопки
		String s = "Play";
		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {

			Log.v(this.getClass().getName(), "типа плей строка");
			s = "Play";

		} else if (statusMusic == statusOfMusic.play) {

			Log.v(this.getClass().getName(), "типа стоп строка");
			s = "Pause";
		}
		return s;
	}

	public String d() { // возвращает строку для тексвью какой сейчас статус
		String s = "idle";

		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {

			Log.v(this.getClass().getName(), "типа плей строка");
			s = "Pause";

		} else if (statusMusic == statusOfMusic.play) {

			Log.v(this.getClass().getName(), "типа стоп строка");
			s = "Play";
		}

		return s;
	}

	public void a() { // воспроизводит музыку, паузу там и т п

		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {
			statusMusic = statusOfMusic.play;
			Log.v(this.getClass().getName(), "типа плей");
			if (!mediaPlayer.isPlaying())
				mediaPlayer.start();

		} else if (statusMusic == statusOfMusic.play) {
			statusMusic = statusOfMusic.pause;
			Log.v(this.getClass().getName(), "типа стоп");
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
					"каая то херотень в новом классе локалбиндер");
			return PlayerService.this;

		}
	}

	@Override
	public void onDestroy() {

		Log.v(this.getClass().getName(), "устрой дестрой йопта 2");
	}

	@Override
	public void onStart(Intent intent, int startid) {

		Log.v(this.getClass().getName(), "стартуем 2");
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		statusMusic = statusOfMusic.idle;

	}
}