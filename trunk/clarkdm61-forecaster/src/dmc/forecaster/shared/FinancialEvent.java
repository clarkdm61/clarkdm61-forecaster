package dmc.forecaster.shared;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class FinancialEvent implements java.io.Serializable, Comparable<FinancialEvent> {
	
	public FinancialEvent() {
		super();
	}

	private static final long serialVersionUID = 7764556144531431668L;
	
	// Fields
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	@Persistent
	private String name;
	@Persistent
	private String description;
	@Persistent
	private Date startDt;
	@Persistent
	private Date endDt;
	@Persistent
	private Double amount;
	@Persistent
	private FinancialEventType type;
	@Persistent
	private Reoccurrence reoccurrence;
	// userId is never used by the client
	@Persistent
	private String userId;
	
	public FinancialEvent(String name, String description,
			Date startDt, Date endDt, Double amount, FinancialEventType type, Reoccurrence reoccurrence) {
		super();
		//this.id = id;
		this.name = name;
		this.description = description;
		this.startDt = startDt;
		this.endDt = endDt;
		this.amount = amount;
		this.type = type;
		this.reoccurrence = reoccurrence;
	}
	
	/**
	 * Returns a (transient) copy of this financial event.
	 * @return
	 */
	public FinancialEvent deepCopy() {
		FinancialEvent fe = new FinancialEvent(name, description, startDt, endDt, amount, type, reoccurrence);
		fe.setId(this.getId());
		return fe;
	}
	
	public String toString() {
		return "id="+getId()+", name="+getName()+", type="+ getReoccurrence() + " " + getType() + ", userId=" + getUserId();
	}
	
	public String getLabelString() {
		String label = Util.dateFormat(getStartDt());
		if (getEndDt() != null) {
			label += " - " + Util.dateFormat(getEndDt()); 
		}
		
		label += ", " + getName()+", "+ getReoccurrence() + " " + getType() +", " +  String.valueOf(getAmount().intValue());
		
		return label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FinancialEventType getType() {
		return type;
	}

	public void setType(FinancialEventType type) {
		this.type = type;
	}

	public Reoccurrence getReoccurrence() {
		return reoccurrence;
	}

	public void setReoccurrence(Reoccurrence reoccurrence) {
		this.reoccurrence = reoccurrence;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public int compareTo(FinancialEvent o) {
		return getStartDt().compareTo(o.getStartDt());
	}
	
	/**
	 * @param aDate
	 * @return true if specified date is between start and end dates (inclusive)
	 */
	public boolean isInDateRange(Date aDate) {
		if (getEndDt() == null) {
			return (aDate.equals(getStartDt()) || aDate.after(getStartDt()));
		}
		return (aDate.equals(getStartDt()) || aDate.after(getStartDt()))
				&&
				(aDate.equals(getEndDt()) || aDate.before(getEndDt()));
	}
	
	public Integer getAmountInt() {
		return getAmount().intValue();
	}
	
	public void setAmountInt(Integer value) {
		setAmount(new Double(value));
	}

}
