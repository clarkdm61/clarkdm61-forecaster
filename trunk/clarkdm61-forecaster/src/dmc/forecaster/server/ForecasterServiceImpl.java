package dmc.forecaster.server;

import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import dmc.forecaster.client.ForecasterService;
import dmc.forecaster.shared.FinancialEvent;

@SuppressWarnings("serial")
public class ForecasterServiceImpl extends RemoteServiceServlet implements
	ForecasterService {

	private static final long serialVersionUID = 2779871172875732317L;
	private static Logger logger = Logger.getLogger(ForecasterServiceImpl.class.getName());
	private FinancialEventDAO _dao = null;
	
	@Override
	public void create(FinancialEvent fe) throws IllegalArgumentException {
		this.createUpdate(fe);
	}

	private void createUpdate(FinancialEvent fe) throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		fe.setUserId(userService.getCurrentUser().getUserId());
		getDao().createUpdate(fe);
	}

	@Override
	public List<FinancialEvent> getAllEvents() throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		
		
		List<FinancialEvent> events = getDao().findAll(userService.getCurrentUser().getUserId());
		
		// run once
//		List<FinancialEvent> events = getDao().findAll();
//		FinancialEvent fe2 = null;
//		for(FinancialEvent fe:events) {
//			fe2 = fe.deepCopy();
//			getDao().delete(fe.getId());
//			// Deep copy during this 'run once'
//			createUpdate(fe2);
//		}
		
		return events;
	}

	@Override
	public void update(FinancialEvent fe) throws IllegalArgumentException {
		this.createUpdate(fe);
	}

	@Override
	public void delete(Long id) throws IllegalArgumentException {
		getDao().delete(id);
	}

	public FinancialEventDAO getDao() {
		if (_dao == null) {
			_dao = new FinancialEventDAO();
		}
		return _dao;
	}


}
