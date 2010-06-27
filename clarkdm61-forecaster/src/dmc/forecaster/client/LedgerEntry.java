package dmc.forecaster.client;

import java.util.Date;

import dmc.forecaster.shared.FinancialEventType;

public class LedgerEntry implements Comparable<LedgerEntry>{

	private String name;
	private Double incomeAmount;
	private Double expenseAmount;
	private Date date;
	private Double balance;
	
	public LedgerEntry() {}
	
	/**
	 * Constructor that applies amount to either income or expense based on FinancialEventType
	 * @param name
	 * @param type
	 * @param amount
	 * @param date
	 */
	public LedgerEntry(String name, FinancialEventType type, Double amount, Date date) {
		super();
		this.name = name;
		if (type.equals(FinancialEventType.Income)) {
			setIncomeAmount(amount);
			setExpenseAmount(0d);
		} else {
			setIncomeAmount(0d);
			setExpenseAmount(amount);
		}
		this.date = date;
		setBalance(0d);
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

	public Double getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public Double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(Double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
