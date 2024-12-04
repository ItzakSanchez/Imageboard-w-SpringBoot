package com.edgaritzak.imageBoard.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "thread")
@PrimaryKeyJoinColumn(name = "post_id")
public class Thread extends Post1{

	@Column(name="title")
	private String title;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name ="last_bump_at")
	private LocalDateTime lastBumpAt;
	
	@Column(name = "is_pinned")
	private boolean isPinned;

	public Thread() {}
	public Thread(String authorId, String content, String nickname, String title) {
		super(authorId, content, nickname);
		this.title = title;
		this.updatedAt = LocalDateTime.now();
		this.lastBumpAt = LocalDateTime.now();
		this.isPinned = false;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAtNow() {
		this.updatedAt = LocalDateTime.now();
	}
	public LocalDateTime getLastBumpAt() {
		return lastBumpAt;
	}
	public void setLastBumpAtNow() {
		this.lastBumpAt = LocalDateTime.now();
	}
	public boolean isPinned() {
		return isPinned;
	}
	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}
}
