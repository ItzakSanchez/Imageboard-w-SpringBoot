package com.edgaritzak.imageBoard.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn(name = "board_id")
	private Board board;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
	private Set<Media> media;
	
	@Column(name ="real_board_id")
	private Long realId;

	public Post() {}
	public Post(String authorId, String content, String nickname, Board board) {
		this.authorId = authorId.isBlank() ? "0" : authorId;
		this.content = content;
		this.nickname = nickname.isBlank() ? "Anonymous" : nickname;
		this.createdAt = LocalDateTime.now();
		this.board = board;
		this.media = new HashSet<Media>();
		this.realId = 1L;//CREAR EL SERVICIO DE NEXTPOSTIDSERVICE CON LA FUNCION .GETNEXTID(LONG BOARDID);
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
	public Set<Media> getMedia() {
		return media;
	}
	public void setMedia(Set<Media> media) {
		this.media = media;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public Long getRealId() {
		return realId;
	}
	public void setRealId(Long realId) {
		this.realId = realId;
	}
	
}
