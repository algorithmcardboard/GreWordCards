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
	private View v2;

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
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		logger.info("in onViewCreated");
		v2 = view;
		super.onViewCreated(view, savedInstanceState);
	}

	public void setCurrentWord(String currentWord) {
		logger.info("setting word to "+currentWord);
		printViews();
//		TextView wordText = (TextView)getView().findViewById(R.id.word_fragment_text);
//		logger.info("Setting string as "+currentWord + wordText.toString());
		wordField.setText(currentWord);
	}

	public void printViews() {
		logger.info("view from field is "+(v==null?" null":v.toString()));
		logger.info("v2 from field is "+(v2==null?" null":v.toString()));
		View view = getView();
		logger.info("getView() is "+ (view == null? "null ": getView().toString()) );
	}

	public void setText(String currentWord) {
		word = currentWord;
	}
}
