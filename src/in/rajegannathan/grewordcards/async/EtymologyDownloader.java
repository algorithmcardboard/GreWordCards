package in.rajegannathan.grewordcards.async;

import in.rajegannathan.grewordcards.models.EtymologyDTO;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class EtymologyDownloader implements Callable<EtymologyDTO>{

	private static final Logger logger = Logger.getLogger(EtymologyDownloader.class.getName());
	private String word;
	
	@SuppressWarnings("unused")
	private EtymologyDownloader(){
		
	}

	public EtymologyDownloader(String word) {
		this.word = word;
	}

	@Override
	public EtymologyDTO call() throws Exception {
		logger.info("in Etymologydownloaader's call method");
		EtymologyDTO etymologyDTO = new EtymologyDTO();
		Thread.sleep(300L);
		logger.info("returning from etymologydownloader");
		return etymologyDTO;
	}

}
