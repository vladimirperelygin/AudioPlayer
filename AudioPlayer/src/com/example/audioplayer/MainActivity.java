package com.example.audioplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnPreparedListener,
		OnCompletionListener {

	public int k = 0;
	MediaPlayer mediaPlayer;
	TextView textView1;
	private OnCompletionListener ololo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text();
		button();

		// textView1.setText("Нажата кнопка ОК");
	}

	Button button1;

	public void button() {

		button1 = (Button) findViewById(R.id.button1);
		mediaPlayer = MediaPlayer.create(this, R.raw.a);
		// создаем обработчик нажатия
		OnClickListener oclBtnOk = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Меняем текст в TextView (tvOut)

				if (k == 0 || k == 2) {
					k = 1;
					button1.setText("Pause");
					text();
					if (!mediaPlayer.isPlaying())
						mediaPlayer.start();

				} else if (k == 1) {
					k = 2;
					button1.setText("Play");
					text();
					if (mediaPlayer.isPlaying())
						mediaPlayer.pause();
				}

			}
		};
		mediaPlayer.setOnCompletionListener(this);
		// присвоим обработчик кнопке OK (btnOk)
		button1.setOnClickListener(oclBtnOk);
	}

	public void text() {

		textView1 = (TextView) findViewById(R.id.textView1);
		if (k == 0)
			textView1.setText("Idle");
		else if (k == 1) {

			textView1.setText("Play");
		} else if (k == 2) {
			textView1.setText("Pause");

		}
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

		k = 0;
		text();
		button1.setText("Play");
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub

	}

}
