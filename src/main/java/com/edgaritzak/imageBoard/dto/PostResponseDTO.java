package com.edgaritzak.imageBoard.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDTO {
	private Long id;
	private String idposter;
	private String nickname;
	private String title;
	private String content;
	private String fileName;
	private LocalDateTime creationDateTime;
	private Long parentId;
	private boolean isPinned;
	private int responseCount;
	private List<PostResponseDTO> responses;
	
	
	public PostResponseDTO() {}
	

	public PostResponseDTO(Long id, String idposter, String nickname, String title, String content, String fileName,
			LocalDateTime creationDateTime, Long parentId, boolean isPinned, int responseCount,
			List<PostResponseDTO> responses) {
		super();
		this.id = id;
		this.idposter = idposter;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.fileName = fileName;
		this.creationDateTime = creationDateTime;
		this.parentId = parentId;
		this.isPinned = isPinned;
		this.responseCount = responseCount;
		this.responses = responses;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdposter() {
		return idposter;
	}
	public void setIdposter(String idposter) {
		this.idposter = idposter;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public boolean isPinned() {
		return isPinned;
	}
	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}
	public int getResponseCount() {
		return responseCount;
	}
	public void setResponseCount(int responseCount) {
		this.responseCount = responseCount;
	}
	public List<PostResponseDTO> getResponses() {
		return responses;
	}
	public void setResponses(List<PostResponseDTO> responses) {
		this.responses = responses;
	}

	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	
	
}
