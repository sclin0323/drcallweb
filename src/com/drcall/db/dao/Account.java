package com.drcall.db.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Account entity. @author MyEclipse Persistence Tools
 * 2014-09-10 by Sam
 */
@Entity
@Table(name = "ACCOUNT", catalog = "drcalldb")
public class Account implements java.io.Serializable {
	private static final int TYPE_DEPOSIT_WEB = 0;
	private static final int TYPE_DEPOSIT_APP = 1;
	private static final int TYPE_WITHDRAW_WEB = 2;
	private static final int TYPE_WITHDRAW_APP = 3;
	private static final int TYPE_APPOINT_CANCEL = 4;
	private static final int TYPE_RECOMMEND = 5;
	private static final int TYPE_BE_RECOMMEND = 6;
	private static final int TYPE_NEW_MEMBER = 7;
	
	private static final int POINT_RECOMMEND = 1;
	private static final int POINT_BE_RECOMMEND = 1;
	private static final int POINT_NEW_MEMBER = 2;
	private static final int POINT_APPOINTMENT = 2;
	
	
	
	// Fields

	private Long accountId;
	private Member member;
	private Timestamp datetime;
	private Integer withdraw;
	private Integer deposit;
	private Integer balance;
	private Integer type;
	private String note;

	// Constructors

	/** default constructor */
	public Account() {
	}

	/** minimal constructor */
	public Account(Member member, Timestamp datetime, Integer balance,
			Integer type) {
		this.member = member;
		this.datetime = datetime;
		this.balance = balance;
		this.type = type;
	}

	/** full constructor */
	public Account(Member member, Timestamp datetime, Integer withdraw,
			Integer deposit, Integer balance, Integer type, String note) {
		this.member = member;
		this.datetime = datetime;
		this.withdraw = withdraw;
		this.deposit = deposit;
		this.balance = balance;
		this.type = type;
		this.note = note;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ACCOUNT_ID", unique = true, nullable = false)
	public Long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID", nullable = false)
	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@Column(name = "DATETIME", nullable = false, length = 19)
	public Timestamp getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	@Column(name = "WITHDRAW")
	public Integer getWithdraw() {
		return this.withdraw;
	}

	public void setWithdraw(Integer withdraw) {
		this.withdraw = withdraw;
	}

	@Column(name = "DEPOSIT")
	public Integer getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}

	@Column(name = "BALANCE", nullable = false)
	public Integer getBalance() {
		return this.balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	@Column(name = "TYPE", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "NOTE", length = 160)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	// GET CURRENT BALANCE
	public int getBalanceByMember(AccountDAO accountDAO, Member member){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Account.class);
		criteria.add(Restrictions.eq("member", member));
		criteria.addOrder(Order.asc("datetime"));
		List<Account> lists = accountDAO.getHibernateTemplate().findByCriteria(criteria);
		
		if(lists.size() == 0){
			return 0;
		} else {
			return lists.get(lists.size()-1).getBalance();
		} 
	}
	
	// WITHDRAW
	public void withdrawByWebAppoint(AccountDAO accountDAO, Member member) {
		Account account = new Account();
		account.setType(TYPE_WITHDRAW_WEB);
		account.setWithdraw(POINT_APPOINTMENT);
		account.setDatetime(new java.sql.Timestamp(new java.util.Date().getTime()));
		
		int balance = getBalanceByMember(accountDAO, member);
		account.setBalance(balance-POINT_APPOINTMENT);
		account.setMember(member);
		
		accountDAO.save(account);
	}
	
	// DEPOSIT
	public void depositForCreditPaymentByWeb(AccountDAO accountDAO, int point, Member member, int sum) {
		Account account = new Account();
		account.setType(TYPE_DEPOSIT_WEB);
		account.setDeposit(point);
		account.setDatetime(new java.sql.Timestamp(new java.util.Date().getTime()));
		
		int balance = getBalanceByMember(accountDAO, member);
		account.setBalance(balance+point);
		account.setNote(sum+" NT");
		account.setMember(member);
		
		accountDAO.save(account);
	}
	
	public void depositForRecommend(AccountDAO accountDAO, Member recommender) {
		
		Account account = new Account();
		account.setType(TYPE_RECOMMEND);
		account.setDeposit(POINT_RECOMMEND);
		account.setDatetime(new java.sql.Timestamp(new java.util.Date().getTime()));
		
		int balance = getBalanceByMember(accountDAO, recommender);
		account.setBalance(balance+POINT_RECOMMEND);
		account.setMember(recommender);

		accountDAO.save(account);
	}
	


	public void depositForBeRecommend(AccountDAO accountDAO, Member member) {
		Account account = new Account();
		account.setType(TYPE_BE_RECOMMEND);
		account.setDeposit(POINT_BE_RECOMMEND);
		account.setDatetime(new java.sql.Timestamp(new java.util.Date().getTime()));
		
		int balance = getBalanceByMember(accountDAO, member);
		account.setBalance(balance+POINT_BE_RECOMMEND);
		account.setMember(member);

		accountDAO.save(account);
		
	}

	public void depositForCreateNewMember(AccountDAO accountDAO, Member member) {
		Account account = new Account();
		account.setType(TYPE_NEW_MEMBER);
		account.setDeposit(POINT_NEW_MEMBER);
		account.setDatetime(new java.sql.Timestamp(new java.util.Date().getTime()));
		
		int balance = getBalanceByMember(accountDAO, member);
		
		account.setBalance(balance+POINT_NEW_MEMBER);
		account.setMember(member);

		accountDAO.save(account);
		
	}

}