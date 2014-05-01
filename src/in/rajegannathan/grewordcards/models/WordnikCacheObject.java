package in.rajegannathan.grewordcards.models;

import in.rajegannathan.grewordcards.async.DerivativeDownloader;
import in.rajegannathan.grewordcards.async.EtymologyDownloader;
import in.rajegannathan.grewordcards.async.MeaningDownloader;
import in.rajegannathan.grewordcards.async.UsageDownloader;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;



public class WordnikCacheObject {
	
	private static final Logger logger = Logger.getLogger(WordnikCacheObject.class.getName());

	private String word;
	private MeaningDTO meaning;
	private EtymologyDTO etymology;
	private DerivativeDTO derivative;
	private UsageDTO usage;
	
	private boolean populating = false;
	
	@SuppressWarnings("unused")
	private WordnikCacheObject(){}
	
	public WordnikCacheObject(String word){
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}

	public MeaningDTO getMeaning() {
		if(meaning == null){
		}
		return meaning;
	}

	public EtymologyDTO getEtymology() {
		return etymology;
	}

	public DerivativeDTO getDerivative() {
		return derivative;
	}

	public UsageDTO getUsage() {
		return usage;
	}

	public void populateWordDetails() {
		if(populating){
			return;
		}
		logger.info("starting to populate for wordDetails");
		
		FutureTask<MeaningDTO> meaningTask = new FutureTask<MeaningDTO>(new MeaningDownloader(this.word));
		new Thread(meaningTask).start();
		FutureTask<UsageDTO> usageTask = new FutureTask<UsageDTO>(new UsageDownloader(this.word));
		new Thread(usageTask).start();
		FutureTask<EtymologyDTO> etymologyTask = new FutureTask<EtymologyDTO>(new EtymologyDownloader(this.word));
		new Thread(etymologyTask).start();
		FutureTask<DerivativeDTO> derivativeTask = new FutureTask<DerivativeDTO>(new DerivativeDownloader(this.word));
		new Thread(derivativeTask).start();
		
		try {
			while(this.meaning == null || this.usage == null || this.derivative == null || this.etymology == null){
				this.meaning = meaningTask.get(); //100L, TimeUnit.MILLISECONDS
				this.usage = usageTask.get(); // 100L, TimeUnit.MILLISECONDS
				this.derivative = derivativeTask.get();// 100L, TimeUnit.MILLISECONDS
				this.etymology = etymologyTask.get(); //100L, TimeUnit.MILLISECONDS
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} /* catch (TimeoutException e) {
			e.printStackTrace();
		}*/
		logger.info("Done populating word details");
	}
}
