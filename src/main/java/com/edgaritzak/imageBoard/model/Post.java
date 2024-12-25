package com.edgaritzak.imageBoard.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name ="author_id")
	private String authorId;

	@Column(name ="content")
	private String content;

	@Column(name ="nickname")
	private String nickname;

	@Column(name ="created_at")
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
	private List<Media> media;
	
	@Column(name ="post_number")
	private Long postNumber;

	public Post() {}
	public Post(Long postNumber, String authorId, String content, String nickname) {
		this.postNumber = postNumber;
		this.authorId = authorId.isBlank() ? "0" : authorId;
		this.content = content;
		this.nickname = nickname.isBlank() ? "Anonymous" : nickname;
		this.createdAt = LocalDateTime.now();
		this.media = new ArrayList<Media>();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public List<Media> getMedia() {
		return media;
	}
	public void setMedia(List<Media> media) {
		this.media = media;
	}
	public Long getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(Long postNumber) {
		this.postNumber = postNumber;
	}
	
}
