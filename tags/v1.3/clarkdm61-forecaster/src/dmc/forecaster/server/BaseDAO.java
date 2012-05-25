package dmc.forecaster.server;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import dmc.forecaster.PMF;

public class BaseDAO {

	private PersistenceManager persistenceManager = null;

	public BaseDAO() {
		super();
	}
	
	protected Logger getLogger() {
		return Logger.getLogger(getClass().getName());
	}

	protected PersistenceManager getPersistenceManager() {
		if (persistenceManager == null) {
			persistenceManager = PMF.get().getPersistenceManager();
		}
		return persistenceManager;
	}

}