package dmc.forecaster.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import dmc.forecaster.PMF;
import dmc.forecaster.shared.FinancialEvent;

public class FinancialEventDAO {
	private PersistenceManager persistenceManager = null;
	
	public FinancialEventDAO() {
		super();
	}
	
	public void create(FinancialEvent newInstance) {
		System.out.println("FinancialEventDAO.create(..)");
		getPersistenceManager().makePersistent(newInstance);
	}
	
	public List<FinancialEvent> findAll() {
		System.out.println("FinancialEventDAO.findAll()");
		Extent<FinancialEvent> extent = getPersistenceManager().getExtent(FinancialEvent.class);
		ArrayList<FinancialEvent> financialEvents = new ArrayList<FinancialEvent>();
		for(FinancialEvent fe: extent) {
			financialEvents.add(fe);
		}
		return financialEvents;
	}
	
	public void update(FinancialEvent detatchedInstance) {
		System.out.println("FinancialEventDAO.update(..)");
		getPersistenceManager().makePersistent(detatchedInstance);
	}
	
	public void delete(Long id) {
		System.out.println("FinancialEventDAO.delete(Long id)");
		FinancialEvent fe = findById(id);
		getPersistenceManager().deletePersistent(fe);
	}
	
	public FinancialEvent findById(Long id) {
		System.out.println("FinancialEventDAO.findById(Long id)");
		return (FinancialEvent) getPersistenceManager().getObjectById(FinancialEvent.class, id);
	}
	
	public PersistenceManager getPersistenceManager() {
		if (persistenceManager == null) {
			persistenceManager = PMF.get().getPersistenceManager();
		}
		return persistenceManager;
	}
	
}
