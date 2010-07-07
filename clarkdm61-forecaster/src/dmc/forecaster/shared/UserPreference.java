package dmc.forecaster.shared;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;

/**
 * UserPreference stores a user's preferences
 * @author dclark
 *
 */
@PersistenceCapable(detachable="true")
public class UserPreference implements java.io.Serializable {

	private String userId;
	private String userEmail;
	private Date ledgerStartDate;
	private Date ledgerEndDate;
	
	public UserPreference() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getLedgerStartDate() {
		return ledgerStartDate;
	}

	public void setLedgerStartDate(Date ledgerStartDate) {
		this.ledgerStartDate = ledgerStartDate;
	}

	public Date getLedgerEndDate() {
		return ledgerEndDate;
	}

	public void setLedgerEndDate(Date ledgerEndDate) {
		this.ledgerEndDate = ledgerEndDate;
	}

}
