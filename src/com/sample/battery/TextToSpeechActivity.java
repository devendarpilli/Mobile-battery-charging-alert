package com.sample.battery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TextToSpeechActivity extends Activity implements OnClickListener {

	Button btn_stop;

	//App starting method
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tts_layout);
		btn_stop = (Button) findViewById(R.id.btn_stopserv);
		btn_stop.setOnClickListener(this);
		//start service class
		Intent i = new Intent(this, Batteryservice.class);
		startService(i);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(btn_stop.getText().toString().equalsIgnoreCase("Stop service")){
			btn_stop.setText("Start service");
			//stop service
			Intent i = new Intent(getApplicationContext(), Batteryservice.class);
			stopService(i);
		}else{
			
			btn_stop.setText("Stop service");
			//start service
			Intent i = new Intent(this, Batteryservice.class);
			startService(i);
		}
	
	
	}

}
