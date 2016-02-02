package com.sample.battery;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class Batteryservice extends Service implements OnInitListener{

	public static int level=0;
	BroadcastReceiver batteryLevelReceiver ;
	public static TextToSpeech textToSpeech;
	Handler h;
	Handler m_handler = new Handler();
	MediaPlayer mp=new MediaPlayer();
	boolean playMusic = false;
	private AudioManager myAudioManager;
	
	//Service will start from this method
	//@Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
	      // Let it continue running until it is stopped.
	     //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
	      //Log.d("onstertcmnd","Service starting");

			// TODO Auto-generated method stub
			 // Toast.makeText(getApplicationContext(), "Service started", Toast.LENGTH_SHORT).show();
			  //Log.d("onstertcmnd","Service binded");
		   //Text to Speech engine for Voice message
			myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

			  textToSpeech = new TextToSpeech(this, this);
			 batteryLevelReceiver = new BroadcastReceiver() {
		         public void onReceive(Context context, Intent intent) {
		        
		             context.unregisterReceiver(this);
		             int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		             int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		             level = -1;
		             if (currentLevel >= 0 && scale > 0) {
		            	 //level object will indicate current battery state
		                 level = (currentLevel * 100) / scale;
		             }
		           
		         }
		     }; 
		     IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		     registerReceiver(batteryLevelReceiver, batteryLevelFilter);
		
		
		     //Log.d("onstertcmnd","Service exe");
		  //   Toast.makeText(getApplicationContext(), "Battery above", Toast.LENGTH_SHORT).show();
		
	      return START_STICKY;
	   }
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	//Runnable thread for continuously checking battery level
	Runnable m_handlerTask = new Runnable()   
	   {
	        @Override 
	        public void run() {
	         
	        	  IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
	 		      registerReceiver(batteryLevelReceiver, batteryLevelFilter);
			      Intent intent1 = new Intent();
			      intent1.setAction("com.tutorialspoint.CUSTOM_INTENT");
			      sendBroadcast(intent1);
			      //if level meets 20% or 15% of battery level then it will call the broadcast reciever
			      if(level==20 || level==15){
			    	  //Checking for background music active state
			    	  if(myAudioManager.isMusicActive()){
			    		  playMusic=true;
			    	  }
			    	  Intent i = new Intent("com.android.music.musicservicecommand");
				      i.putExtra("command", "pause");
				      Batteryservice.this.sendBroadcast(i); 
			      }else{
			    	  //if music is active this will work
			    	  if(playMusic){
			    		  Intent i1 = new Intent("com.android.music.musicservicecommand");
					      i1.putExtra("command", "play");
					      Batteryservice.this.sendBroadcast(i1);
			    	  }
			    	  
			      }
			      
				  m_handler.postDelayed(m_handlerTask, 10000);
	        }
	   };
	   
	   public float getBatteryLevel(Intent batteryIntent) {  
           int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);  
           int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);  
           if (level == -1 || scale == -1) {  
                return 50.0f;  
           }  
           return ((float) level / (float) scale) * 100.0f;  
      }  

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		//starting runnable thread
	     m_handlerTask.run(); 
	}
	@Override
	public void onDestroy() {
		//Kill Text to speech and runnable thread
		textToSpeech.shutdown();
		m_handler.removeCallbacks(m_handlerTask);
		
		//mp.start();
	
	}


}
