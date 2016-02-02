package com.sample.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

public class Batterylevel extends BroadcastReceiver{
	
	//MediaPlayer mp=new MediaPlayer();

	//For charger connection state
    public static boolean isConnected(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
    }
    
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub	textToSpeech = new TextToSpeech(this, this);
				//Check for charger connected or not
		//isConnected method will return true if charger connected
		if(!isConnected(context)){
			 //Log.d("onstertcmnd","Not Charging");
			//	Toast.makeText(context, "Not charging"+Batteryservice.level, 1000).show();

			if(Batteryservice.level==20){
				//mp.pause();
				
				Batteryservice.textToSpeech.speak("Battery is low.Please charge me.", TextToSpeech.QUEUE_FLUSH, null);
				
				// System.out.println("LEVEL:"+Batteryservice.level);
				// Toast.makeText(context, "your battery down"+Batteryservice.level, Toast.LENGTH_LONG).show();
				}else if(Batteryservice.level==15){
					//mp.pause();
					Batteryservice.textToSpeech.speak("Battery is low.Please charge me.", TextToSpeech.QUEUE_FLUSH, null);
					
					 System.out.println("LEVEL:"+Batteryservice.level);
					// Toast.makeText(context, "your battery down"+Batteryservice.level, Toast.LENGTH_LONG).show();
					}else{
					 //Toast.makeText(context, "your battery is ok now"+Batteryservice.level, 1000).show();
						//Batteryservice.textToSpeech.speak("Battery is ok", TextToSpeech.QUEUE_FLUSH, null);

				}
			
		}else{
			//Log.d("onstertcmnd","Charging");
		//Toast.makeText(context, "charging"+Batteryservice.level, 1000).show();
		}
		
		
		 
	}
	
	



}
