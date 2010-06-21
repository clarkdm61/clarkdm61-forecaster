package dmc.forecaster.domain;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

public class FinancialEvent {
	

	// Fields
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
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
	
	public FinancialEvent(Key key, String name, String description,
			Date startDt, Date endDt, Double amount) {
		super();
		this.key = key;
		this.name = name;
		this.description = description;
		this.startDt = startDt;
		this.endDt = endDt;
		this.amount = amount;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
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
}
