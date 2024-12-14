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
