package dmc.forecaster.domain;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class FinancialEvent implements java.io.Serializable {
	
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
	//@Persistent
	private FinancialEventType type;
	//@Persistent
	private Reoccurrence reoccurrence;
	
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
	
	public String toString() {
		return "id="+getId()+", name="+getName()+", type="+ getReoccurrence() + " " + getType();
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
}