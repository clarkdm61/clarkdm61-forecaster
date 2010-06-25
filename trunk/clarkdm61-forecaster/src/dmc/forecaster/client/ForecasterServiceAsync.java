package dmc.forecaster.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import dmc.forecaster.shared.FinancialEvent;

public interface ForecasterServiceAsync {

	void getAllEvents(AsyncCallback<List<FinancialEvent>> callback);

	void create(FinancialEvent fe, AsyncCallback<Void> callback);

	void update(FinancialEvent fe, AsyncCallback<Void> callback);

	void delete(Long id, AsyncCallback<Void> callback);

}
