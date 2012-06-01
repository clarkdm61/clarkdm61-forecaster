package dmc.forecaster.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import dmc.forecaster.shared.FinancialEvent;
import dmc.forecaster.shared.UserPreference;

public class UserPreferenceDAO extends BaseDAO {
	
	private PersistenceManager persistenceManager = null;
	private static Query userPreferenceByUserQuery = null;


	public UserPreferenceDAO() {
	}
	
	/**
	 * 
	 * @returns userPreferenceByUserQuery (initialized if needed)
	 */
	private Query getUserPreferenceByUserQuery() {
		if (userPreferenceByUserQuery == null) {
			userPreferenceByUserQuery = getPersistenceManager().newQuery(UserPreference.class);
			userPreferenceByUserQuery.setFilter("userId==userIdParam");
			userPreferenceByUserQuery.declareParameters("String userIdParam");
		}
		return userPreferenceByUserQuery;
	}
	
	/**
	 * Returns UserPreference for specified userId, or null if not in database.
	 * @param userId
	 * @return
	 */
	public UserPreference findByUserId(String userId) {
		getLogger().fine("UserPreferenceDAO.findByUserId(String userId): " + userId);
		
		List<UserPreference> extent = (List<UserPreference>) getUserPreferenceByUserQuery().execute(userId);
		
		if (extent.isEmpty()) {
			getLogger().fine("nothing found for " + userId);
			return null;
 		} else {
 			getLogger().fine("found " + extent.get(0));
 			return extent.get(0);
 		}
	}
	
	/**
	 * Persist by creating or updating
	 * @param newInstance
	 */
	public void createUpdate(UserPreference newInstance) {
		getLogger().fine("UserPreferenceDAO.createUpdate(..): " + newInstance);
		getPersistenceManager().makePersistent(newInstance);
	}

}
