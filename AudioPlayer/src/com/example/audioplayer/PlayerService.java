package com.example.audioplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class PlayerService extends Service implements OnCompletionListener {

	statusOfMusic statusMusic = statusOfMusic.idle; // status player

	MediaPlayer mediaPlayer;

	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return mBinder;

	}

	@Override
	public void onCreate() {
		mediaPlayer = MediaPlayer.create(this, R.raw.a);
		Log.v(this.getClass().getName(), "OnCreateService");
	}

	public int textButtonPlayPause() {

		int status = R.string.ButtonTextPlay;
		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {

			status = R.string.ButtonTextPlay;
			;

		} else if (statusMusic == statusOfMusic.play) {

			status = R.string.ButtonTextPause;
		}
		return status;
	}

	public int textStatusOfPlayer() {
		int status = R.string.StatusIdle;

		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {

			status = R.string.StatusPause;

		} else if (statusMusic == statusOfMusic.play) {

			status = R.string.StatusPlay;
		}
		return status;
	}

	public void PlayPausePlayer() {

		if (statusMusic == statusOfMusic.idle
				|| statusMusic == statusOfMusic.pause) {
			statusMusic = statusOfMusic.play;
			Log.v(this.getClass().getName(), "Play player");
			if (!mediaPlayer.isPlaying())
				mediaPlayer.start();

		} else if (statusMusic == statusOfMusic.play) {
			statusMusic = statusOfMusic.pause;
			Log.v(this.getClass().getName(), "Pause player");
			if (mediaPlayer.isPlaying())
				mediaPlayer.pause();

		}
		mediaPlayer.setOnCompletionListener(this);
	}

	public void PlayerPause() {
		statusMusic = statusOfMusic.pause;
		if (mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}

	public class LocalBinder extends Binder {
		PlayerService getService() {

			return PlayerService.this;

		}
	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void onStart(Intent intent, int startid) {

	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		statusMusic = statusOfMusic.idle;
		Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
		sendBroadcast(intent);

	}
}