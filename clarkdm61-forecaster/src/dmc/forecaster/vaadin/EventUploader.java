package dmc.forecaster.vaadin;

import java.io.OutputStream;
import java.util.logging.Logger;

import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class EventUploader implements Receiver, SucceededListener,
		FailedListener {
	

	@Override
	public void uploadFailed(FailedEvent event) {
		log().fine("uploadFailed:" + event);

	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		log().fine("uploadSucceeded:" + event);

	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		log().fine("receiveUpload:" + filename + ", " + mimeType);
		return null;
	}
	
	private Logger log() {
		return Logger.getLogger(getClass().getName());
	}

}
