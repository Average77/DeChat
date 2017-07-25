/**
 * 
 */
package com.isabella.dechat.speex;

import android.os.Handler;


import java.io.File;

public class SpeexPlayer {
	private String fileName = null;
	private SpeexDecoder speexdec = null;
	private Handler handler ;
	private long sendtimer ;
	private long atimer ;
	boolean isPlay = false;

	public SpeexPlayer(String fileName,Handler handler) {

		this.fileName = fileName;
		this.handler = handler ;
		this.sendtimer = sendtimer ;
		this.atimer = atimer;
		try {
			speexdec = new SpeexDecoder(new File(this.fileName),handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startPlay() {
		RecordPlayThread rpt = new RecordPlayThread();
        isPlay=true;
		Thread th = new  Thread(rpt);
		th.start();
	}
	
	public void stopPlay(boolean release){
		speexdec.isStop = true ;
		isPlay=false;
		speexdec.releaseMusic = release;
		System.err.println("stopPlay stop ");

	}



	public boolean isPlay() {
		return isPlay;
	}

	public void setPlay(boolean play) {
		isPlay = play;
	}

	class RecordPlayThread extends Thread {

		public void run() {
			try {
				if (speexdec != null)
					speexdec.decode();
			} catch (Exception t) {
				t.printStackTrace();
			}
		}
	};
}
