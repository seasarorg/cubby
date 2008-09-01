package org.seasar.cubby.cubbitter.entity;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", unique = true, nullable = false, length = 31)
	private String name;

	@Column(name = "FULL_NAME", nullable = false, length = 255)
	private String fullName;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "OPEN", nullable = false)
	public boolean open;

	@Column(name = "MAIL", nullable = true)
	private String mail;

	@Column(name = "LOCATION", nullable = true)
	private String location;

	@Column(name = "WEB", nullable = true)
	private String web;

	@Column(name = "BIOGRAPHY", nullable = true)
	private String biography;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "REQUESTS", joinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "REQUEST_ACCOUNT_ID", referencedColumnName = "ID"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"ACCOUNT_ID", "REQUEST_ACCOUNT_ID" }))
	private Set<Account> requests;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "FOLLOWINGS", joinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "FOLLOWING_ACCOUNT_ID", referencedColumnName = "ID"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"ACCOUNT_ID", "FOLLOWING_ACCOUNT_ID" }))
	private Set<Account> followings;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "FOLLOWERS", joinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "FOLLOWER_ACCOUNT_ID", referencedColumnName = "ID"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"ACCOUNT_ID", "FOLLOWER_ACCOUNT_ID" }))
	private Set<Account> followers;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	@OrderBy("post DESC")
	private Set<Entry> entries;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "FAVORITES", joinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ENTRY_ID", referencedColumnName = "ID"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"ACCOUNT_ID", "ENTRY_ID" }))
	private Set<Entry> favorites;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "REPLIES", joinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ENTRY_ID", referencedColumnName = "ID"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"ACCOUNT_ID", "ENTRY_ID" }))
	private Set<Entry> replies;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SMALL_IMAGE_ID")
	private Image smallImage;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MEDIUM_IMAGE_ID")
	private Image mediumImage;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "LARGE_IMAGE_ID")
	private Image largeImage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String email) {
		this.mail = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Set<Account> getRequests() {
		if (requests == null) {
			requests = new LinkedHashSet<Account>();
		}
		return requests;
	}

	public void setRequests(Set<Account> requests) {
		this.requests = requests;
	}

	public Set<Account> getFollowings() {
		if (followings == null) {
			followings = new LinkedHashSet<Account>();
		}
		return followings;
	}

	public void setFollowings(Set<Account> followings) {
		this.followings = followings;
	}

	public Set<Account> getFollowers() {
		if (followers == null) {
			followers = new LinkedHashSet<Account>();
		}
		return followers;
	}

	public void setFollowers(Set<Account> followers) {
		this.followers = followers;
	}

	public Set<Entry> getEntries() {
		if (entries == null) {
			entries = new LinkedHashSet<Entry>();
		}
		return entries;
	}

	public void setEntries(Set<Entry> entries) {
		this.entries = entries;
	}

	public Set<Entry> getFavorites() {
		if (favorites == null) {
			favorites = new LinkedHashSet<Entry>();
		}
		return favorites;
	}

	public void setFavorites(Set<Entry> favoriteEntries) {
		this.favorites = favoriteEntries;
	}

	public Set<Entry> getReplies() {
		if (replies == null) {
			replies = new LinkedHashSet<Entry>();
		}
		return replies;
	}

	public void setReplies(Set<Entry> replies) {
		this.replies = replies;
	}

	public Image getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(Image smallIage) {
		this.smallImage = smallIage;
	}

	public Image getMediumImage() {
		return mediumImage;
	}

	public void setMediumImage(Image mediumImage) {
		this.mediumImage = mediumImage;
	}

	public Image getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(Image largeImage) {
		this.largeImage = largeImage;
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
