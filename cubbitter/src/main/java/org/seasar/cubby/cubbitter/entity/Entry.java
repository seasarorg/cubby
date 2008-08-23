package org.seasar.cubby.cubbitter.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ENTRY")
@NamedQueries( {
		@NamedQuery(name = "Entry.findByOpen", query = "SELECT e FROM Entry AS e WHERE e.account.open = :open ORDER BY e.post DESC"),
		@NamedQuery(name = "Entry.getCountByOpen", query = "SELECT COUNT(*) FROM Entry AS e WHERE e.account.open = :open"),

		@NamedQuery(name = "Entry.findByAccounts", query = "SELECT e FROM Entry AS e WHERE e.account IN (:accounts) ORDER BY e.post DESC"),
		@NamedQuery(name = "Entry.getCountByAccounts", query = "SELECT COUNT(*) FROM Entry AS e WHERE e.account IN (:accounts)"),

		@NamedQuery(name = "Entry.findByAccount", query = "SELECT e FROM Entry AS e WHERE e.account = :account ORDER BY e.post DESC"),
		@NamedQuery(name = "Entry.getCountByAccount", query = "SELECT COUNT(*) FROM Entry AS e WHERE e.account = :account"),

		@NamedQuery(name = "Entry.findFavoritesByAccount", query = "SELECT e FROM Account AS a JOIN a.favorites AS e WHERE a = :account ORDER BY e.post DESC"),
		@NamedQuery(name = "Entry.getFavoritesCountByAccount", query = "SELECT COUNT(*) FROM Account AS a JOIN a.favorites AS e WHERE a = :account"),

		@NamedQuery(name = "Entry.findRepliesByAccount", query = "SELECT e FROM Account AS a JOIN a.replies AS e WHERE a = :account ORDER BY e.post DESC"),
		@NamedQuery(name = "Entry.getRepliesCountByAccount", query = "SELECT COUNT(*) FROM Account AS a JOIN a.replies AS e WHERE a = :account")
})
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
	private List<Account> favorites;

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

	public List<Account> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<Account> favorites) {
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
