package dmc.forecaster.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.Query;

import dmc.forecaster.shared.FinancialEvent;

public class FinancialEventDAO extends BaseDAO {
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
		getLogger().fine("FinancialEventDAO.createUpdate(..): " + newInstance);
		getPersistenceManager().makePersistent(newInstance);
	}
	
	/**
	 * Returns all financial events - should only be used for schema migration since events need to be filtered for the current user.
	 * @see FinancialEventDAO#findAll(String)
	 * @return
	 */
	@Deprecated
	public List<FinancialEvent> findAll() {
		getLogger().fine("FinancialEventDAO.findAll()...");
		
		Extent<FinancialEvent> extent = getPersistenceManager().getExtent(FinancialEvent.class);
		ArrayList<FinancialEvent> financialEvents = new ArrayList<FinancialEvent>();
		for(FinancialEvent fe: extent) {
			getLogger().fine("fe="+fe);
			financialEvents.add(fe);
		}
		getLogger().fine("found " + financialEvents.size());
		return financialEvents;
	}
	
	/**
	 * Returns all financial events belonging to the specified userId
	 * @param userId
	 * @return
	 */
	public List<FinancialEvent> findAll(String userId) {
		getLogger().fine("FinancialEventDAO.findAll(userId)...");
		
		List<FinancialEvent> extent = (List<FinancialEvent>) getFinancialEventByUserQuery().execute(userId);
		ArrayList<FinancialEvent> financialEvents = new ArrayList<FinancialEvent>();
		for(FinancialEvent fe: extent) {
			getLogger().fine("fe="+fe);
			financialEvents.add(fe);
		}

		
		getLogger().fine("found " + financialEvents.size());
		return financialEvents;
	}
	
	public void delete(Long id) {
		getLogger().fine("FinancialEventDAO.delete(Long id)");
		FinancialEvent fe = findById(id);
		getPersistenceManager().deletePersistent(fe);
	}
	
	public FinancialEvent findById(Long id) {
		getLogger().fine("FinancialEventDAO.findById(Long id)");
		return (FinancialEvent) getPersistenceManager().getObjectById(FinancialEvent.class, id);
	}
	
}
