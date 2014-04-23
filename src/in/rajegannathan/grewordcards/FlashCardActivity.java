package in.rajegannathan.grewordcards;

import java.util.logging.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class FlashCardActivity extends Activity {


	private static final Logger logger = Logger.getLogger(FlashCardActivity.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flash_card);
		
		findViewById(R.id.fullscreen_content).setOnClickListener(handler);
	}
	
	View.OnClickListener handler = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			logger.info("in onclick listener");
		}
		
	};
}
