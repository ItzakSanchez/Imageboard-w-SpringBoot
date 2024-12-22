package com.edgaritzak.imageBoard.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResponseReplyDTO {
	
	private Long postNumber;
	private String authorId;
	private String nickname;
	private String content;
	private LocalDateTime uploadedAt;
	private List<String> mediaFilenames;
	
	public ResponseReplyDTO(){}
	public ResponseReplyDTO(Long postNumber, String authorId, String nickname, String content, LocalDateTime uploadedAt, List<String> mediaFilenames) {
		this.postNumber = postNumber;
		this.authorId = authorId;
		this.nickname = nickname;
		this.content = content;
		this.uploadedAt = uploadedAt;
		this.mediaFilenames = mediaFilenames;
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
}
