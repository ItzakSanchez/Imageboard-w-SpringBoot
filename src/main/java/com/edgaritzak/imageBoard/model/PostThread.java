package com.edgaritzak.imageBoard.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "thread")
@PrimaryKeyJoinColumn(name = "id")
public class PostThread extends Post{

	@Column(name="title")
	private String title;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name ="last_bump_at")
	private LocalDateTime lastBumpAt;
	
	@Column(name = "is_pinned")
	private boolean isPinned;

	@OneToMany(mappedBy = "thread")
	private List<Reply> replies;

	public PostThread() {}
	public PostThread(Long postNumber, String authorId, String content, String nickname, Board board, String title) {
		super(postNumber, authorId, content, nickname, board);
		this.title = title;
		this.updatedAt = LocalDateTime.now();
		this.lastBumpAt = LocalDateTime.now();
		this.isPinned = false;
		this.replies = new ArrayList<>();
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
    public List<Reply> getReplies() {return replies;}
    public void setReplies(List<Reply> replies) {this.replies = replies;}
}
