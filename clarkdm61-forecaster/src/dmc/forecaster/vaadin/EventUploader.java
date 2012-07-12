package dmc.forecaster.vaadin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.FinancialEventType;
import dmc.forecaster.shared.Reoccurrence;

/**
 * Worker class for uploading FinancialEvents from an XML file. Event IDs are
 * ignored, so all event's are added event if the are duplicate.
 * http://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html
 */
public class EventUploader implements Receiver, SucceededListener,
		FailedListener, Serializable {

	private static final long serialVersionUID = -3129552144357529508L;
	public static boolean UNIT_TESTING = false;

	// The stream buffer is populated by the Vaadin upload framework,
	// then processed once succeeded.
	// Marked transient since Vaadin tries to serialize everything.
	private transient ByteArrayOutputStream _out = null;
	private transient FinancialEvent financialEvent = null;
	private ManagerTab m_managerTab = null;

	/** enum so tags can be used in a switch (could use reflection) */
	private enum Tags {
		eventList, financialEvent, id, name, description, reoccurrence, type, amount, startDt, endDt;
	}

	/** inner class for SAX processing */
	private class UploadHandler extends DefaultHandler {
		
		// holds value of last tag
		private Tags tag = null;
		
		/**
		 * Process tags
		 */
		public void startElement(String namespaceURI, String localName,
				String qName, Attributes atts) throws SAXException {

			tag = Tags.valueOf(qName);
			// financialEvent - create new entity
			if (tag == Tags.financialEvent) {
				//log().fine("Create Event.");
				log().log(Level.INFO, "Create Uploaded Event.");
				financialEvent = new FinancialEvent();
			} 
		}
		
		public void endElement(String uri, String localName,
				String qName) throws SAXException {

			// financialEvent - time to save entity, null out temp
			if (Tags.valueOf(qName) == Tags.financialEvent) {
				log().fine("Save Event: " + financialEvent);
				if (!UNIT_TESTING) {
					AppData.getForecasterService().create(financialEvent);
				}
			}
			// eventList - done processing
		}
		
		/**
		 * Process any chars within a tag
		 */
		public void characters(char[] ch, int start, int length) {
			// based on current tag, populate data into entity
			
			String value = String.copyValueOf(ch, start, length);
			if (value.trim().length() > 0 && !value.equals("null")) {
				// set value
				log().fine(tag + ": " + value);
				switch (tag){
					case amount:
						financialEvent.setAmount(new Double(value));
						break;
					case description:
						financialEvent.setDescription(value);
						break;
					case endDt:
						financialEvent.setEndDt(new java.util.Date(value));
						break;
					case id:
						// keep the ID unset, so that a new record is always created
						// financialEvent.setId(new Long(value));
						break;
					case name:
						financialEvent.setName(value);
						break;
					case reoccurrence:
						financialEvent.setReoccurrence(Reoccurrence.valueOf(value));
						break;
					case startDt:
						financialEvent.setStartDt(new java.util.Date(value));
						break;
					case type:
						financialEvent.setType(FinancialEventType.valueOf(value));
				
				}
			} 
		}
	} /** end UploadHandler */

	public EventUploader(ManagerTab managerTab) {
		m_managerTab = managerTab;
	}
	@Override
	public void uploadFailed(FailedEvent event) {
		log().fine("uploadFailed:" + event);

	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		log().fine("uploadSucceeded:" + event);
		byte[] bytes = _out.toByteArray();
		String xml = new String(bytes);
		System.out.println(xml);
		try {
			importEvents(bytes);
			m_managerTab.refreshManagerTable();
		} catch (Exception e) {
			throw new RuntimeException (e);
		}
		_out = null;
	}

	protected void importEvents(byte[] bytes) throws Exception {
		
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
		
		ByteArrayInputStream inStream = new ByteArrayInputStream(bytes);
		
		saxParser.parse(inStream, new UploadHandler());
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		log().fine("receiveUpload:" + filename + ", " + mimeType);
		_out = new ByteArrayOutputStream();
		return _out;
	}

	private Logger log() {
		return Logger.getLogger(getClass().getName());
	}

}
