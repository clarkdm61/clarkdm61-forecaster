package dmc.forecaster.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import dmc.forecaster.shared.FinancialEvent;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("forecaster")
public interface ForecasterService extends RemoteService {
	List<FinancialEvent> getAllEvents() throws IllegalArgumentException;
	void create(FinancialEvent fe) throws IllegalArgumentException;
	void update(FinancialEvent fe) throws IllegalArgumentException;
}
