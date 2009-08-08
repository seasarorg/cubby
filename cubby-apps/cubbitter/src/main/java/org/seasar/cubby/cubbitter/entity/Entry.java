package org.seasar.cubby.cubbitter.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ENTRY")
public class Entry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "TEXT", length = 1024)
	private String text;

	@Column(name = "POST")
	@Temporal(TemporalType.TIMESTAMP)
	private Date post;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "FAVORITES", joinColumns = @JoinColumn(name = "ENTRY_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"ACCOUNT_ID", "ENTRY_ID" }))
	private Set<Account> favorites;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPost() {
		return post;
	}

	public void setPost(Date post) {
		this.post = post;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<Account> getFavorites() {
		if (favorites == null) {
			this.favorites = new LinkedHashSet<Account>();
		}
		return favorites;
	}

	public void setFavorites(Set<Account> favorites) {
		this.favorites = favorites;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entry other = (Entry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
