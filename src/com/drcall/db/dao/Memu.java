package com.drcall.db.dao;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Memu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEMU", catalog = "drcalldb")
public class Memu implements java.io.Serializable {

	// Fields

	private String menuId;
	private String name;
	private Set<Role> roles = new HashSet<Role>(0);

	// Constructors

	/** default constructor */
	public Memu() {
	}

	/** minimal constructor */
	public Memu(String menuId) {
		this.menuId = menuId;
	}

	/** full constructor */
	public Memu(String menuId, String name, Set<Role> roles) {
		this.menuId = menuId;
		this.name = name;
		this.roles = roles;
	}

	// Property accessors
	@Id
	@Column(name = "MENU_ID", unique = true, nullable = false, length = 20)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "NAME", length = 40)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "memus")
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}