package dmc.forecaster.vaadin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import com.vaadin.Application;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.FileResource;

import dmc.forecaster.shared.FinancialEvent;

public class EventDownloadStream extends FileResource {
	
	private static final long serialVersionUID = -9205284785063275818L;
	public static final String FILE_NAME = "EventData.xml";

	public EventDownloadStream(Application application) {
		super(new File(""), application);
	}

	public EventDownloadStream(File sourceFile, Application application) {
		super(sourceFile, application);
	}

	@Override
	public DownloadStream getStream() {
		String xml = getXML();
		ByteArrayInputStream bas = new ByteArrayInputStream(xml.getBytes());
		DownloadStream ds = new DownloadStream(bas, "text/plain", FILE_NAME);
		ds.setParameter("Content-Disposition",    "attachment; filename="+FILE_NAME);
		return ds;
	}
	
	private String getXML() {
		List<FinancialEvent> list = AppData.getFinancialEventList();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<eventList>");
		for (FinancialEvent fe : list) {
			sb.append(
					financialEvent2XML(fe)
					);
		}
		
		sb.append("</eventList>");
		return sb.toString();
	}

	private String financialEvent2XML(FinancialEvent fe) {
		String s = " <financialEvent>";
		s += field2xml("id", fe.getId());
		s += field2xml("name", fe.getName());
		s += field2xml("description", fe.getDescription());
		s += field2xml("reoccurrence", fe.getReoccurrence());
		s += field2xml("type", fe.getType());
		s += field2xml("amount", fe.getAmountInt());
		s += field2xml("startDt", fe.getStartDt());
		s += field2xml("endDt", fe.getEndDt());
		s += "</financialEvent>\n";
		return s;
	}
	
	private String field2xml(String fieldName, Object fieldValue) {
		String s = "  <" + fieldName + ">";
		s += fieldValue;
		s += "</" + fieldName + ">\n";
		return s;
	}
}
