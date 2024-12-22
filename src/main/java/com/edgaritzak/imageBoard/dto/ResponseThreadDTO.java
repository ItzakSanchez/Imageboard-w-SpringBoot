package com.edgaritzak.imageBoard.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseThreadDTO {
	
	private Long id;
	private Long postNumber;
	private String authorId;
	private String nickname;
	private String title;
	private String content;
	private LocalDateTime uploadedAt;
	private List<String> mediaFilenames;
	private int numberOfReplies;
	private int numberOfMedia;
	
	public ResponseThreadDTO() {}
	public ResponseThreadDTO(Long id, Long postNumber, String authorId, String nickname, String title, String content,
			LocalDateTime uploadedAt, List<String> mediaFilenames, int numberOfReplies, int numberOfMedia) {
		this.id = id;
		this.postNumber = postNumber;
		this.authorId = authorId;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.uploadedAt = uploadedAt;
		this.mediaFilenames = mediaFilenames;
		this.numberOfReplies = numberOfReplies;
		this.numberOfMedia = numberOfMedia;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(Long postNumber) {
		this.postNumber = postNumber;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getUploadedAt() {
		return uploadedAt;
	}
	public void setUploadedAt(LocalDateTime uploadedAt) {
		this.uploadedAt = uploadedAt;
	}
	public List<String> getMediaFilenames() {
		return mediaFilenames;
	}
	public void setMediaFilenames(List<String> mediaFilenames) {
		this.mediaFilenames = mediaFilenames;
	}
	public int getNumberOfReplies() {
		return numberOfReplies;
	}
	public void setNumberOfReplies(int numberOfReplies) {
		this.numberOfReplies = numberOfReplies;
	}
	public int getNumberOfMedia() {
		return numberOfMedia;
	}
	public void setNumberOfMedia(int numberOfMedia) {
		this.numberOfMedia = numberOfMedia;
	}
}