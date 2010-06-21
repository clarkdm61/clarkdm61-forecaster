package dmc.forecaster.domain;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class FinancialEvent implements java.io.Serializable {
	

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
	
	// TODO: Reoccurrence (non, week, month, year)
	// TODO: Type (Income, Expense) required
	
	public FinancialEvent(String name, String description,
			Date startDt, Date endDt, Double amount) {
		super();
		//this.id = id;
		this.name = name;
		this.description = description;
		this.startDt = startDt;
		this.endDt = endDt;
		this.amount = amount;
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
}
