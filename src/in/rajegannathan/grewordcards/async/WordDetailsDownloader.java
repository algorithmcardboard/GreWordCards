package in.rajegannathan.grewordcards.async;

import java.util.List;
import java.util.logging.Logger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.wordnik.client.api.WordApi;
import com.wordnik.client.common.ApiException;
import com.wordnik.client.model.Definition;
import com.wordnik.client.model.WordObject;

public class WordDetailsDownloader extends Thread {

	public static final int INITIALIZED = 0;
	public static final int MEANING = 1;
	public static final int USAGE = 4;
	public static final int ETYMOLOGY = 3;
	public static final int DERIVATIVE = 2;
	
	private Handler uiHandler;
	private Handler downloadHandler;
	private boolean handlerInitialized = false;
	
	private WordApi wordAPI = new WordApi();
	
	private static final Logger logger = Logger.getLogger(WordDetailsDownloader.class.getName());

	public WordDetailsDownloader(Handler uiHandler) {
		this.uiHandler = uiHandler;
		wordAPI.getInvoker().addDefaultHeader("api_key", "f202890d818b6b25a3b0e000a700f2032187316965dabaeaf");
	}

	@Override
	public void run() {
		logger.info(Thread.currentThread().getId()+"");
		Looper.prepare();
		logger.info("in WordDetailsDownloader's new thread");
		downloadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				quitIfInterrupted();
				String word = msg.obj.toString();
				logger.info("in downloadThread's handle message" + msg.obj.toString());
				try {
					long startTime = System.currentTimeMillis();
					List<Definition> definitions = wordAPI.getDefinitions(word, null, null, null, null, null, null);
					for(Definition defn : definitions){
						logger.info("defn: "+definitions.get(0).getText());
					}
					logger.info("Time taken for "+ word +" is " +(System.currentTimeMillis() - startTime));
				} catch (ApiException e) {
					e.printStackTrace();
				}
				dispatchToUiQueue("downloaded " + msg.obj.toString());
			}

			private void quitIfInterrupted() {
				if(isInterrupted()){
					logger.info("quitting looper");
					Looper.myLooper().quit();
					return;
				}
			}
		};
		if(!handlerInitialized){
			sendInitializedMessage();
		}
		Looper.loop();
		logger.info("out of looper's loop");
	}

	private void sendInitializedMessage() {
		Message message = Message.obtain();
		message.what = INITIALIZED;
		message.obj = "";
		uiHandler.sendMessage(message);
	}

	private void dispatchToUiQueue(String string) {
		Message message = Message.obtain();
		message.obj = string;
		uiHandler.sendMessage(message);
	}

	public void fetchDetails(String word) {
		Message message = Message.obtain();
		message.obj = word;
		if(downloadHandler != null){
			downloadHandler.sendMessage(message);
		}
	}

	public void quit() {
		logger.info(Thread.currentThread().getId()+"");
	}

	public void stopProcessing() {
		this.interrupt();
		this.fetchDetails("");
	}
}
