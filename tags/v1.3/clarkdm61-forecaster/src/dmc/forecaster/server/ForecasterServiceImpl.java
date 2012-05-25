package dmc.forecaster.server;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import dmc.forecaster.client.ForecasterService;
import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.Reoccurrence;
import dmc.forecaster.shared.UserPreference;

@SuppressWarnings("serial")
public class ForecasterServiceImpl extends RemoteServiceServlet implements
	ForecasterService {

	private static final long serialVersionUID = 2779871172875732317L;
	private static Logger logger = Logger.getLogger(ForecasterServiceImpl.class.getName());
	private FinancialEventDAO _financialEventDAO = null;
	private UserPreferenceDAO _userPreferenceDAO = null;
	
	public FinancialEventDAO getFinancialEventDao() {
		if (_financialEventDAO == null) {
			_financialEventDAO = new FinancialEventDAO();
		}
		return _financialEventDAO;
	}
	
	public UserPreferenceDAO getUserPreferenceDao() {
		if (_userPreferenceDAO == null) {
			_userPreferenceDAO = new UserPreferenceDAO();
		}
		return _userPreferenceDAO;
	}

	@Override
	public void create(FinancialEvent fe) throws IllegalArgumentException {
		this.createUpdate(fe);
	}

	private void createUpdate(FinancialEvent fe) throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		fe.setUserId(userService.getCurrentUser().getUserId());
		getFinancialEventDao().createUpdate(fe);
	}

	@Override
	public List<FinancialEvent> getAllEvents() throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		
		
		List<FinancialEvent> events = getFinancialEventDao().findAll(userService.getCurrentUser().getUserId());
		
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
		getFinancialEventDao().delete(id);
	}

	@Override
	public UserPreference getUserPreference() throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		UserPreference upref = getUserPreferenceDao().findByUserId(userService.getCurrentUser().getUserId());
		if (upref == null) {
			// generate a UserPreference record, set the ledger spread to 12 weeks
			upref = new UserPreference();
			Date todayDt = new Date();
			Date nextDt = new Date( todayDt.getTime()+Reoccurrence.WEEK*12 );
			upref.setLedgerStartDate(todayDt);
			upref.setLedgerEndDate(nextDt);
		}
		return upref;
	}

	@Override
	public void updateUserPreference(UserPreference upref)
			throws IllegalArgumentException {
		UserService userService = UserServiceFactory.getUserService();
		upref.setUserId(userService.getCurrentUser().getUserId());
		getUserPreferenceDao().createUpdate(upref);
	}
}
