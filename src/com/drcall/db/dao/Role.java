package com.drcall.db.dao;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROLE", catalog = "drcalldb")
public class Role implements java.io.Serializable {

	// Fields

	private String roleId;
	private String name;
	private String note;
	private Set<Usr> usrs = new HashSet<Usr>(0);
	private Set<Memu> memus = new HashSet<Memu>(0);

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(String roleId) {
		this.roleId = roleId;
	}

	/** full constructor */
	public Role(String roleId, String name, String note, Set<Usr> usrs,
			Set<Memu> memus) {
		this.roleId = roleId;
		this.name = name;
		this.note = note;
		this.usrs = usrs;
		this.memus = memus;
	}

	// Property accessors
	@Id
	@Column(name = "ROLE_ID", unique = true, nullable = false, length = 20)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "NAME", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NOTE", length = 40)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<Usr> getUsrs() {
		return this.usrs;
	}

	public void setUsrs(Set<Usr> usrs) {
		this.usrs = usrs;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ROLE_MENU", catalog = "drcalldb", joinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "MENU_ID", nullable = false, updatable = false) })
	public Set<Memu> getMemus() {
		return this.memus;
	}

	public void setMemus(Set<Memu> memus) {
		this.memus = memus;
	}

}