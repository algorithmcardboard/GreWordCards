package in.rajegannathan.grewordcards.models;

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
	}
}
