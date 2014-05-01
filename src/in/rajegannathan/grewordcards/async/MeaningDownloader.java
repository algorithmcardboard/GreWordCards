package in.rajegannathan.grewordcards.async;

import in.rajegannathan.grewordcards.models.MeaningDTO;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class MeaningDownloader implements Callable<MeaningDTO>{
	
	private static final Logger logger = Logger.getLogger(MeaningDownloader.class.getName());
	private String word;
	private WordnikDownloader downloader = new WordnikDownloader();
	
	@SuppressWarnings("unused")
	private MeaningDownloader(){}

	public MeaningDownloader(String word) {
		this.word = word;
	}

	@Override
	public MeaningDTO call() throws Exception {
		logger.info("in MeaningDownloader.  About to sleep for 300 milliseconds"+downloader.getName());
		MeaningDTO meaningDTO = new MeaningDTO();
 		logger.info("returning from meaningDownloader");
		return meaningDTO;
	}

}
