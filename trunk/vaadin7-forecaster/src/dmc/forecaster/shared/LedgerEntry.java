package dmc.forecaster.shared;

import java.io.Serializable;
import java.util.Date;

import dmc.forecaster.shared.FinancialEventType;

public class LedgerEntry implements Comparable<LedgerEntry>, Serializable {

	private static final long serialVersionUID = 423920526198787425L;
	private String name;
	private Integer incomeAmount;
	private Integer expenseAmount;
	private Date date;
	private Integer balance;
	private boolean income = false;
	private String rowColor;
	
	public LedgerEntry() {}
	
	/**
	 * Constructor that applies amount to either income or expense based on FinancialEventType
	 * @param name
	 * @param type
	 * @param amount
	 * @param date
	 */
	public LedgerEntry(String name, FinancialEventType type, Integer amount, Date date) {
		super();
		this.name = name;
		if (type.equals(FinancialEventType.Income)) {
			setIncomeAmount(amount);
			setExpenseAmount(0);
			income=true;
		} else {
			setIncomeAmount(0);
			setExpenseAmount(amount);
			income=false;
		}
		this.date = date;
		setBalance(0);
	}

	@Override
	public int compareTo(LedgerEntry o) {
		// compare by date
		return this.date.compareTo(o.getDate());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(Integer incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public Integer getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(Integer expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
	public boolean isIncome() {
		return income;
	}

	public String getRowColor() {
		return rowColor;
	}

	public void setRowColor(String rowColor) {
		this.rowColor = rowColor;
	}
}
