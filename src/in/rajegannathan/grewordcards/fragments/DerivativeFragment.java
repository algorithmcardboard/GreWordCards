package in.rajegannathan.grewordcards.fragments;

import in.rajegannathan.grewordcards.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DerivativeFragment extends Fragment {
	private View v;
	private TextView derivativeField;
	private String derivativeText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.derivative_fragment, container, false);
		derivativeField = (TextView)v.findViewById(R.id.derivative_fragment_text);
		derivativeField.setText(derivativeText);
		return v;
	}
	
	public void setDerivativeText(String derivativeText){
		this.derivativeText = derivativeText;
		if(v != null){
			derivativeField.setText(derivativeText);
		}
	}
	
}
