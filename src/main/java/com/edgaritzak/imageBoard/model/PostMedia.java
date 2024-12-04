package com.edgaritzak.imageBoard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "post_media")
public class PostMedia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post1 post;
	
	@Column(name = "filename")
	private String filename;

	public PostMedia() {}
	public PostMedia(Post1 post, String filename) {
		this.post = post;
		this.filename = filename;
	}
}
