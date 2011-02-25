package dmc.forecaster.shared;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * UserPreference stores a user's preferences
 * @author dclark
 *
 */
@PersistenceCapable(detachable="true")
public class UserPreference implements java.io.Serializable {

	private static final long serialVersionUID = -8770822656351367393L;

	// Fields
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	@Persistent
	private String userId;
	@Persistent
	private Date ledgerStartDate;
	@Persistent
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString() {
		return "userId="+getUserId()+", ledgerStartDate="+getLedgerStartDate()+", ledgerEndDate="+getLedgerEndDate();
	}

}
