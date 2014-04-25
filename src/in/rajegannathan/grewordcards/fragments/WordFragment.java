package in.rajegannathan.grewordcards.fragments;

import in.rajegannathan.grewordcards.R;

import java.util.logging.Logger;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WordFragment extends Fragment {
	
	private static final Logger logger = Logger.getLogger(WordFragment.class.getName());
	
	private View v;

	private String word;
	private TextView wordField;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		logger.info("in onCreateView");
		v = inflater.inflate(R.layout.word_fragment, container, false);
		wordField = (TextView)v.findViewById(R.id.word_fragment_text);
		wordField.setText(word);
		return v;
	}
	
	public void setCurrentWord(String currentWord) {
		
		word = currentWord;
		if(v != null){
			wordField.setText(currentWord);
		}
		logger.info("setting word to "+currentWord);
	}
}
