package dmc.forecaster.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;

import dmc.forecaster.PMF;
import dmc.forecaster.shared.FinancialEvent;

public class FinancialEventDAO {
	private PersistenceManager persistenceManager = null;
	private static Logger logger = Logger.getLogger(FinancialEventDAO.class.getName());
	
	public FinancialEventDAO() {
		super();
	}
	
	public void create(FinancialEvent newInstance) {
		logger.fine("FinancialEventDAO.create(..): " + newInstance);
		getPersistenceManager().makePersistent(newInstance);
	}
	
	public List<FinancialEvent> findAll() {
		logger.fine("FinancialEventDAO.findAll()...");
		Extent<FinancialEvent> extent = getPersistenceManager().getExtent(FinancialEvent.class);
		ArrayList<FinancialEvent> financialEvents = new ArrayList<FinancialEvent>();
		for(FinancialEvent fe: extent) {
			financialEvents.add(fe);
		}
		logger.fine("found " + financialEvents.size());
		return financialEvents;
	}
	
	public void update(FinancialEvent detatchedInstance) {
		logger.fine("FinancialEventDAO.update(..): " + detatchedInstance);
		getPersistenceManager().makePersistent(detatchedInstance);
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
