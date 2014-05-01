package in.rajegannathan.grewordcards.async;

import in.rajegannathan.grewordcards.models.UsageDTO;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class UsageDownloader implements Callable<UsageDTO>{

	private static final Logger logger = Logger.getLogger(UsageDownloader.class.getName());
	private String word;
	
	@SuppressWarnings("unused")
	private UsageDownloader(){
	}

	public UsageDownloader(String word) {
		this.word = word;
	}

	@Override
	public UsageDTO call() throws Exception {
		logger.info("in usageDownloader's call method");
		UsageDTO usageDto = new UsageDTO();
		Thread.sleep(300L);
		logger.info("returning from usageDownloader");
		return usageDto;
	}

}
