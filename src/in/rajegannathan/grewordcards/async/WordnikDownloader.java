package in.rajegannathan.grewordcards.async;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import com.wordnik.client.api.WordApi;

public class WordnikDownloader {
	private static final String API_KEY = "apiKey";
	private WordApi wordApi;
	private static Properties props;
	private static final Logger logger = Logger.getLogger(WordnikDownloader.class.getName());
	
	static{
		props = new Properties();
		try {
			FileInputStream fn = new FileInputStream("local.properties");
			props.load(fn);
		} catch (FileNotFoundException e) {
			logger.info("no file found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("unable to read file");
		}
	}
	
	public WordnikDownloader(){
		wordApi = new WordApi();
		wordApi.addHeader("api_key", props.getProperty(API_KEY));
	}
	
	public String getName(){
		return props.getProperty(API_KEY);
	}
}
