package in.rajegannathan.grewordcards.fragments;

import java.util.logging.Logger;

import in.rajegannathan.grewordcards.R;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BoundaryFragment extends Fragment {
	
	private static final Logger logger = Logger.getLogger(BoundaryFragment.class.getName());
	private TextView wordText;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		logger.info("in oncreateView of boundary fragment");
		View v = inflater.inflate(R.layout.boundary_fragment, container, false);
		wordText = (TextView) v.findViewById(R.id.boundary_fragment_text);
		return v;
	}

	public void reachedEnd() {
		wordText.setTypeface(Typeface.DEFAULT);
		wordText.setText("Reached the end of your list. Swipe up for previous words.");
	}

	public void reachedStart() {
		wordText.setTypeface(Typeface.DEFAULT);
		wordText.setText("Reached the end of your list. Swipe up for previous words.");
	}

}
