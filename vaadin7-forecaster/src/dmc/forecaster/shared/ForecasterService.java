package dmc.forecaster.shared;

import java.util.List;

/**
 * The service specification.
 */
public interface ForecasterService  {
	List<FinancialEvent> getAllEvents() throws IllegalArgumentException;
	void create(FinancialEvent fe) throws IllegalArgumentException;
	void update(FinancialEvent fe) throws IllegalArgumentException;
	void delete(Long id) throws IllegalArgumentException;
	UserPreference getUserPreference() throws IllegalArgumentException;
	void updateUserPreference(UserPreference upref) throws IllegalArgumentException;
}
