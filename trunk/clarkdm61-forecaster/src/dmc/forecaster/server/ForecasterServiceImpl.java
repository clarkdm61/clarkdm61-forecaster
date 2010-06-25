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
		FinancialEventDAO dao = new FinancialEventDAO();
		dao.create(fe);
	}

	@Override
	public List<FinancialEvent> getAllEvents() throws IllegalArgumentException {
		FinancialEventDAO dao = new FinancialEventDAO();
		return dao.findAll();
	}

	@Override
	public void update(FinancialEvent fe) throws IllegalArgumentException {
		FinancialEventDAO dao = new FinancialEventDAO();
		dao.update(fe);
	}

	@Override
	public void delete(Long id) throws IllegalArgumentException {
		FinancialEventDAO dao = new FinancialEventDAO();
		dao.delete(id);
	}


}
