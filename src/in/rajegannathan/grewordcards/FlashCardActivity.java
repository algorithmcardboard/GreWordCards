package in.rajegannathan.grewordcards;

import java.util.logging.Logger;

import android.app.Activity;
import android.os.Bundle;

public class FlashCardActivity extends Activity {


	private static final Logger logger = Logger.getLogger(FlashCardActivity.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_card);
//		getActivity().getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
	}
}
