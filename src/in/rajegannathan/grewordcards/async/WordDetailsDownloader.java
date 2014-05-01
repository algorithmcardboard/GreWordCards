package in.rajegannathan.grewordcards.async;

import in.rajegannathan.grewordcards.cache.WordnikResultCache;
import in.rajegannathan.grewordcards.models.WordnikCacheObject;

import java.util.logging.Logger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class WordDetailsDownloader extends Thread {

	public static final int INITIALIZED = 0;
	public static final int MEANING = 2;
	public static final int USAGE = 3;
	public static final int ETYMOLOGY = 5;
	public static final int DERIVATIVE = 7;

	public static final int PROCESSED_ALL = MEANING * USAGE * ETYMOLOGY
			* DERIVATIVE;

	private volatile int stackSize = 0;

	private Handler uiHandler;
	private Handler downloadHandler;
	private boolean handlerInitialized = false;
	private WordnikResultCache wrc = WordnikResultCache.getInstance();

	private static final Logger logger = Logger
			.getLogger(WordDetailsDownloader.class.getName());
	protected static final long DOWNLOADER_SLEEP_TIME = 200;

	public WordDetailsDownloader(Handler uiHandler) {
		this.uiHandler = uiHandler;
		// wordAPI.getInvoker().addDefaultHeader("api_key",
		// "f202890d818b6b25a3b0e000a700f2032187316965dabaeaf");
	}

	@Override
	public void run() {
		logger.info(Thread.currentThread().getId() + "");
		Looper.prepare();
		logger.info("in WordDetailsDownloader's new thread");
		downloadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				quitIfInterrupted();
				stackSize--;
				if (stackSize > WordnikResultCache.NTHREADS) {
					logger.info("returning as stackSize is greater than numthreads");
					return;
				}
				int processStatus = 1;
				String word = msg.obj.toString();
				logger.info("Stacksize is "+stackSize);
				logger.info("condition before entering while loop is "+(stackSize==0&&processStatus%PROCESSED_ALL != 0) );
				while (stackSize == 0 && processStatus % PROCESSED_ALL != 0) {
					processStatus = processWord(processStatus, word);
					try {
						Thread.sleep(DOWNLOADER_SLEEP_TIME);
					} catch (InterruptedException e) {
					}
					logger.warning("thread interrupted. Mostly due to activity exit");
				}
				logger.info("in downloadThread's handle message" + word);
			}

			private int processWord(int processStatus, String word) {
				WordnikCacheObject cacheObject = wrc.getWordnikCacheObject(word);
				
				if (cacheObject.getMeaning() != null && processStatus % MEANING != 0) {
					processStatus = processStatus * MEANING;
					dispatchToUiQueue(cacheObject.getMeaning().getDisplayText(), MEANING);
				}
				if (cacheObject.getDerivative() != null	&& processStatus % DERIVATIVE != 0) {
					processStatus = processStatus * DERIVATIVE;
					dispatchToUiQueue(cacheObject.getDerivative().getDisplayText(), DERIVATIVE);
				}
				if (cacheObject.getEtymology() != null && processStatus % ETYMOLOGY != 0) {
					processStatus = processStatus * ETYMOLOGY;
					dispatchToUiQueue(cacheObject.getEtymology()
							.getDisplayText(), ETYMOLOGY);
				}
				if (cacheObject.getUsage() != null && processStatus % USAGE != 0) {
					processStatus = processStatus * USAGE;
					dispatchToUiQueue(cacheObject.getUsage().getDisplayText(), USAGE);
				}
				
				return processStatus;
			}

			private void quitIfInterrupted() {
				if (isInterrupted()) {
					logger.info("quitting looper");
					Looper.myLooper().quit();
					return;
				}
			}
		};
		if (!handlerInitialized) {
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

	private void dispatchToUiQueue(String string, int what) {
		Message message = Message.obtain();
		message.obj = string;
		message.what = what;
		uiHandler.sendMessage(message);
	}

	public void fetchDetails(String word) {
		stackSize++;
		Message message = Message.obtain();
		message.obj = word;
		if (downloadHandler != null) {
			downloadHandler.sendMessage(message);
		}
		logger.info("Stacksize in fetchDetails is "+stackSize);
	}

	public void quit() {
		logger.info(Thread.currentThread().getId() + "");
	}

	public void stopProcessing() {
		this.interrupt();
		this.fetchDetails("");
	}
}
