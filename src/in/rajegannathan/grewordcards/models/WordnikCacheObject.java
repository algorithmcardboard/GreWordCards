package in.rajegannathan.grewordcards.models;



public class WordnikCacheObject {
	

	@SuppressWarnings("unused")
	private WordnikCacheObject(){}
	
	public WordnikCacheObject(String word){
		this.word = word;
	}
	
	private String word;
	private MeaningDTO meaning;
	private EtymologyDTO etymology;
	private DerivativeDTO derivative;
	private UsageDTO usage;
	
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
}
