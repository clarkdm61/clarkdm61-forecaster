package dmc.forecaster.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import dmc.forecaster.PMF;
import dmc.forecaster.shared.FinancialEvent;

public class FinancialEventDAO {
	private PersistenceManager persistenceManager = null;
	private static Logger logger = Logger.getLogger(FinancialEventDAO.class.getName());
	private static Query financialEventByUserQuery = null;
	
	public FinancialEventDAO() {
		super();
	}
	
	/**
	 * 
	 * @returns financialEventByUserQuery (initialized if needed)
	 */
	private Query getFinancialEventByUserQuery() {
		if (financialEventByUserQuery == null) {
			financialEventByUserQuery = getPersistenceManager().newQuery(FinancialEvent.class);
			financialEventByUserQuery.setFilter("userId==userIdParam");
			financialEventByUserQuery.declareParameters("String userIdParam");
		}
		return financialEventByUserQuery;
	}
	
	public void createUpdate(FinancialEvent newInstance) {
		logger.fine("FinancialEventDAO.createUpdate(..): " + newInstance);
		getPersistenceManager().makePersistent(newInstance);
	}
	
	@Deprecated
	public List<FinancialEvent> findAll() {
		logger.fine("FinancialEventDAO.findAll()...");
		
		Extent<FinancialEvent> extent = getPersistenceManager().getExtent(FinancialEvent.class);
		ArrayList<FinancialEvent> financialEvents = new ArrayList<FinancialEvent>();
		for(FinancialEvent fe: extent) {
			logger.fine("fe="+fe);
			financialEvents.add(fe);
		}
		logger.fine("found " + financialEvents.size());
		return financialEvents;
	}
	
	public List<FinancialEvent> findAll(String userId) {
		logger.fine("FinancialEventDAO.findAll(userId)...");
		
		List<FinancialEvent> extent = (List<FinancialEvent>) getFinancialEventByUserQuery().execute(userId);
		ArrayList<FinancialEvent> financialEvents = new ArrayList<FinancialEvent>();
		for(FinancialEvent fe: extent) {
			logger.fine("fe="+fe);
			financialEvents.add(fe);
		}

		
		logger.fine("found " + financialEvents.size());
		return financialEvents;
	}
	
	public void delete(Long id) {
		logger.fine("FinancialEventDAO.delete(Long id)");
		FinancialEvent fe = findById(id);
		getPersistenceManager().deletePersistent(fe);
	}
	
	public FinancialEvent findById(Long id) {
		logger.fine("FinancialEventDAO.findById(Long id)");
		return (FinancialEvent) getPersistenceManager().getObjectById(FinancialEvent.class, id);
	}
	
	public PersistenceManager getPersistenceManager() {
		if (persistenceManager == null) {
			persistenceManager = PMF.get().getPersistenceManager();
		}
		return persistenceManager;
	}
	
}
