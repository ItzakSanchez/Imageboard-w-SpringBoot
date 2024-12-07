package com.edgaritzak.imageBoard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity()
@Table(name = "reply")
@PrimaryKeyJoinColumn(name = "id")
public class Reply extends Post {
	
	@Column(name = "thread_id")
	private Long threadId;

	public Reply() {}
	public Reply(String authorId, String content, String nickname, Long threadId) {
		super(authorId, content, nickname);
		this.threadId = threadId;
	}

	
	public Long getThreadId() {
		return threadId;
	}
	public void setThreadId(Long threadId) {
		this.threadId = threadId;
	}
	
}
