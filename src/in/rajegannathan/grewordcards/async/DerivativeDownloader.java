package in.rajegannathan.grewordcards.async;

import in.rajegannathan.grewordcards.models.DerivativeDTO;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class DerivativeDownloader implements Callable<DerivativeDTO>{

	private static final Logger logger = Logger.getLogger(DerivativeDownloader.class.getName());
	private String word;
	
	@SuppressWarnings("unused")
	private DerivativeDownloader(){
	}

	public DerivativeDownloader(String word) {
		this.word = word;
	}

	@Override
	public DerivativeDTO call() throws Exception {
		logger.info("in derivativedownloader's call");
		DerivativeDTO derivativeDTO = new DerivativeDTO();
		Thread.sleep(300L);
		logger.info("returngin from derivativedownloader");
		return derivativeDTO;
	}

}
