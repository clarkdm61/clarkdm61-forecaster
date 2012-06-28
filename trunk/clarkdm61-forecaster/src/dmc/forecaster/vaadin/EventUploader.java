package dmc.forecaster.vaadin;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Logger;

import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class EventUploader implements Receiver, SucceededListener,
		FailedListener, Serializable {

	private static final long serialVersionUID = -3129552144357529508L;
	private class Serializer extends ByteArrayOutputStream implements Serializable {

		private static final long serialVersionUID = 4250831291234920573L;
		
	}
	Serializer _out = null;
	
	@Override
	public void uploadFailed(FailedEvent event) {
		log().fine("uploadFailed:" + event);

	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		log().fine("uploadSucceeded:" + event);
		byte[] bytes = _out.toByteArray();
		String s =new String(bytes);
		System.out.println(s);
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		log().fine("receiveUpload:" + filename + ", " + mimeType);
		_out = new Serializer();
		return _out;
	}
	
	private Logger log() {
		return Logger.getLogger(getClass().getName());
	}

}
