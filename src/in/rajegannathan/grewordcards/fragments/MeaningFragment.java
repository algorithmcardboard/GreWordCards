package in.rajegannathan.grewordcards.fragments;

import in.rajegannathan.grewordcards.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MeaningFragment extends Fragment {
	private View v;
	private String meaning;
	private TextView meaningField;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v =  inflater.inflate(R.layout.meaning_fragment, container, false);
		meaningField = (TextView)v.findViewById(R.id.meaning_fragment_text);
		meaningField.setText(meaning);
		return v;
	}
	
	public void setMeaning(String meaning){
		this.meaning = meaning;
		if(v != null){
			meaningField.setText(meaning);
		}
	}
}
