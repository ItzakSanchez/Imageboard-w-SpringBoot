<<<<<<< HEAD
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
	public Reply(Long postNumber, String authorId, String content, String nickname, Board board, PostThread thread) {
		super(postNumber, authorId, content, nickname, board);
		this.thread = thread;
	}
	
	public PostThread getThread() {
		return thread;
	}
	public void setThread(PostThread thread) {
		this.thread = thread;
	}
	
}
=======
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
	public Reply(Long postNumber, String authorId, String content, String nickname, Board board, PostThread thread) {
		super(postNumber, authorId, content, nickname, board);
		this.thread = thread;
	}
	
	public PostThread getThread() {
		return thread;
	}
	public void setThread(PostThread thread) {
		this.thread = thread;
	}
	
}
>>>>>>> 7a9c4e126cdc1ac05c7c7c9741595f4b40c47553
