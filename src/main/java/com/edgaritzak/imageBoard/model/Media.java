<<<<<<< HEAD
package com.edgaritzak.imageBoard.model;

import jakarta.persistence.*;


@Entity
@Table(name = "media")
public class Media {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "filename")
	private String filename;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "post_id")
	private Post post;

	public Media() {}
	public Media(Post post, String filename) {
		this.post = post;
		this.filename = filename;
	}
}
=======
package com.edgaritzak.imageBoard.model;

import jakarta.persistence.*;


@Entity
@Table(name = "media")
public class Media {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "filename")
	private String filename;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "post_id")
	private Post post;

	public Media() {}
	public Media(Post post, String filename) {
		this.post = post;
		this.filename = filename;
	}
}
>>>>>>> 7a9c4e126cdc1ac05c7c7c9741595f4b40c47553
