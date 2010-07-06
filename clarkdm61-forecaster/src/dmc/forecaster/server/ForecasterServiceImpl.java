package dmc.forecaster.server;

import java.util.List;
import java.util.logging.Level;
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
	
	@Override
	public void create(FinancialEvent fe) throws IllegalArgumentException {
		FinancialEventDAO dao = new FinancialEventDAO();
		dao.create(fe);
	}

	@Override
	public List<FinancialEvent> getAllEvents() throws IllegalArgumentException {
		try {
			UserService userService = UserServiceFactory.getUserService();
			if (userService != null) {
				String user = "user is [" + userService.getCurrentUser().getUserId() + "]" + userService.getCurrentUser().getEmail();
				logger.warning("getAllEvents() - "+ user);
			} else {
				logger.severe("getAllEvents() - UserService is null!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.severe("getAllEvents() failed in regards to user service. " + e.getMessage());
		}
 
		//logger.warning("getAllEvents() [user principal] - "+getThreadLocalRequest().getUserPrincipal());
		
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
