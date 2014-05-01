package in.rajegannathan.grewordcards.fragments;

import in.rajegannathan.grewordcards.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EtymologyFragment extends Fragment {
	private View v;
	private TextView etymologyField;
	private String etymologyText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.etymology_fragment, container, false);
		etymologyField = (TextView)v.findViewById(R.id.etymology_fragment_text);
		etymologyField.setText(this.etymologyText);
		return v;
	}
	
	public void setEtymologyText(String etymologyText){
		this.etymologyText = etymologyText;
		if(v != null){
			etymologyField.setText(etymologyText);
		}
	}
}
