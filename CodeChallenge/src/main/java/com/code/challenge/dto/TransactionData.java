package com.code.challenge.dto;

import java.io.Serializable;
import java.util.Date;

public class TransactionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2392677059762484324L;
	private String id;
	private Date date;
	private float amount;
	private String merchant;
	private String type;
	private String relatedTransaction;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRelatedTransaction() {
		return relatedTransaction;
	}

	public void setRelatedTransaction(String relatedTransaction) {
		this.relatedTransaction = relatedTransaction;
	}

}
