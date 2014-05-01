package in.rajegannathan.grewordcards.fragments;

import in.rajegannathan.grewordcards.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UsageFragment extends Fragment {
	private View v;
	private TextView usageField;
	private String usageText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.usage_fragment, container, false);
		usageField = (TextView)v.findViewById(R.id.usage_fragment_text);
		usageField.setText(usageText);
		return v;
	}
	
	public void setUsage(String usageText){
		this.usageText = usageText;
		if(v != null){
			usageField.setText(usageText);
		}
	}
}
