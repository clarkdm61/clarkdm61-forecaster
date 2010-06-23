package dmc.forecaster.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import dmc.forecaster.client.ForecasterService;
import dmc.forecaster.shared.FinancialEvent;

@SuppressWarnings("serial")
public class ForecasterServiceImpl extends RemoteServiceServlet implements
	ForecasterService {

	private static final long serialVersionUID = 2779871172875732317L;

	@Override
	public void create(FinancialEvent fe) throws IllegalArgumentException {
		System.out.println("ForecasterServiceImpl.create() - " + fe);
	}

	@Override
	public List<FinancialEvent> getAllEvents() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(FinancialEvent fe) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}


}
