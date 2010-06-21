package dmc.forecaster.domain;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;

import dmc.forecaster.PMF;

public class FinancialEventDAO {
	
	public FinancialEventDAO() {
		super();
	}
	
	public void create(FinancialEvent newInstance) {
		PMF.get().getPersistenceManager().makePersistent(newInstance);
	}
	
	public List<FinancialEvent> findAll() {
		Extent<FinancialEvent> extent = PMF.get().getPersistenceManager().getExtent(FinancialEvent.class);
		ArrayList<FinancialEvent> financialEvents = new ArrayList<FinancialEvent>();
		for(FinancialEvent fe: extent) {
			financialEvents.add(fe);
		}
		return financialEvents;
	}
	
	public void update(FinancialEvent detatchedInstance) {
		PMF.get().getPersistenceManager().makePersistent(detatchedInstance);
	}
	
	public void delete(Long id) {
		FinancialEvent fe = findById(id);
		PMF.get().getPersistenceManager().deletePersistent(fe);
	}
	
	public FinancialEvent findById(Long id) {
		return (FinancialEvent) PMF.get().getPersistenceManager().getObjectById(id);
	}
	
}
