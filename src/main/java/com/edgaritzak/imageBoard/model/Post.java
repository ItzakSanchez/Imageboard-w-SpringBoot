package com.edgaritzak.imageBoard.model;

import java.time.LocalDateTime;

import com.edgaritzak.imageBoard.dto.PostDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_post")
public class Post {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name="id_poster")
	private String idposter;
	@Column(name="nickname")
	private String nickname;
	@Column(name="title")
	private String title;
	@Column(name="content")
	private String content;
	@Column(name="image_path")
	private String imagePath;
	@Column(name = "creation_date_time")
	private LocalDateTime creationDateTime;
	@Column(name = "parent_post_id")
	private Long parentId;
	@Column(name = "is_pinned")
	private boolean isPinned;
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	@Column(name = "response_count")
	private int responseCount;
	/*POST CONSTRUCTOR*/
	public Post() {}
	
	private Post(Builder builder) {
		super();
		this.idposter = builder.idposter;
		this.nickname = builder.nickname;
		this.title = builder.title;
		this.content = builder.content;
		this.imagePath = builder.imagePath;
		this.creationDateTime = builder.creationDateTime;
		this.parentId = builder.parentId;
		this.isPinned = builder.isPinned;
		this.updatedAt = builder.updatedAt;
		this.responseCount = builder.responseCount;
	}
	
	/*BUILDER*/
	public static class Builder{
		
		private String idposter;
		private String nickname;
		private String title;
		private String content;
		private String imagePath;
		private LocalDateTime creationDateTime;
		private Long parentId;
		private boolean isPinned; 
		private LocalDateTime updatedAt;
		private int responseCount;
		
		/*BUILDER CONSTRUCTOR*/
		public Builder(PostDTO postDTO) {
			this.idposter = postDTO.getIdposter();
			this.content = postDTO.getContent();
			this.parentId = postDTO.getParentId();
			this.creationDateTime = LocalDateTime.now();
			this.updatedAt = LocalDateTime.now();
			this.nickname = "Anonymous";
			this.responseCount = 0;
			
		}
		
		public Builder nickname(String nickname) {
			if(!nickname.isBlank()) {
				this.nickname = nickname;
			}
			return this;
		}
		public Builder title(String title) {
			if (!title.isBlank()) {
				this.title = title;
			}
			return this;
		}
		public Builder imagePath(String imagePath) {
			this.imagePath = imagePath;
			return this;
		}
		public Builder isPinned(boolean isPinned) {
			this.isPinned = isPinned;
			return this;
		}
		
		public Post build() {
			return new Post(this);
		}
	}
	
	/*GETTERS*/
	public Long getId() {
		return id;
	}
	public String getIdposter() {
		return idposter;
	}
	public String getNickname() {
		return nickname;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getImagePath() {
		return imagePath;
	}
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}
	public Long getParentId() {
		return parentId;
	}
	public boolean isPinned() {
		return isPinned;
	}
	public LocalDateTime getUpdatedAt() {
		return creationDateTime;
	}
	public int getResponseCount() {
		return responseCount;
	}



	/*SETTERS*/
	public void setIsPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}
	public void setUpdateLastUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public void setResponseCount(int responseCount) {
		this.responseCount = responseCount;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", idposter=" + idposter + ", nickname=" + nickname + ", title=" + title
				+ ", content=" + content + ", imagePath=" + imagePath + ", creationDateTime=" + creationDateTime
				+ ", parentId=" + parentId + ", isPinned=" + isPinned + ", updatedAt=" + updatedAt + ", responseCount="
				+ responseCount + "]";
	}


	
	
}


