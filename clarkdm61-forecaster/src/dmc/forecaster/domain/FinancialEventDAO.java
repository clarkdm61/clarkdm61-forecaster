package dmc.forecaster.domain;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import dmc.forecaster.PMF;

public class FinancialEventDAO {
	private PersistenceManager persistenceManager = null;
	
	public FinancialEventDAO() {
		super();
	}
	
	public void create(FinancialEvent newInstance) {
		getPersistenceManager().makePersistent(newInstance);
	}
	
	public List<FinancialEvent> findAll() {
		Extent<FinancialEvent> extent = getPersistenceManager().getExtent(FinancialEvent.class);
		ArrayList<FinancialEvent> financialEvents = new ArrayList<FinancialEvent>();
		for(FinancialEvent fe: extent) {
			financialEvents.add(fe);
		}
		return financialEvents;
	}
	
	public void update(FinancialEvent detatchedInstance) {
		getPersistenceManager().makePersistent(detatchedInstance);
	}
	
	public void delete(Long id) {
		FinancialEvent fe = findById(id);
		getPersistenceManager().deletePersistent(fe);
	}
	
	public FinancialEvent findById(Long id) {
		return (FinancialEvent) getPersistenceManager().getObjectById(FinancialEvent.class, id);
	}
	
	public PersistenceManager getPersistenceManager() {
		if (persistenceManager == null) {
			persistenceManager = PMF.get().getPersistenceManager();
		}
		return persistenceManager;
	}
	
}
