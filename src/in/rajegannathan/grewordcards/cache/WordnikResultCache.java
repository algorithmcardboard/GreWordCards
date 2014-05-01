package in.rajegannathan.grewordcards.cache;

import in.rajegannathan.grewordcards.models.WordnikCacheObject;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WordnikResultCache {

	public static final int NTHREADS = 5;

	ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);

	private final int MAX_ENTRIES = 300;
	private final Map<String, WordnikCacheObject> cache = Collections
			.synchronizedMap(new LinkedHashMap<String, WordnikCacheObject>(
					MAX_ENTRIES + 1, .75F, true));

	private static final WordnikResultCache resultCache = new WordnikResultCache();

	private WordnikResultCache() {
	}

	public static final WordnikResultCache getInstance() {
		return resultCache;
	}

	public WordnikCacheObject getWordnikCacheObject(String word){
		if(cache.get(word) == null){
			final WordnikCacheObject cacheObject = new WordnikCacheObject(word);
			cache.put(word, cacheObject);
			
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					cacheObject.populateWordDetails();
				}
			});
		}
		return cache.get(word);
	}
}
