package com.edgaritzak.imageBoard.model;

import jakarta.persistence.*;

@Entity()
@Table(name = "reply")
@PrimaryKeyJoinColumn(name = "id")
public class Reply extends Post {
	
	@JoinColumn(name = "thread_id")
	@ManyToOne(cascade = CascadeType.REMOVE)
	private PostThread thread;

	public Reply() {}
	public Reply(Long postNumber, String authorId, String content, String nickname, PostThread thread) {
		super(postNumber, authorId, content, nickname);
		this.thread = thread;
	}
	
	
	
	public PostThread getThread() {
		return thread;
	}
	public void setThread(PostThread thread) {
		this.thread = thread;
	}
	
}