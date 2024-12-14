package com.edgaritzak.imageBoard.dto;

public class PostCreationDTO {
	private String idposter;
	private String nickname;
	private String title;
	private String content;
	private String fileName;
	private Long parentId;
	private boolean isPinned;
	
	public PostCreationDTO() {
		
	}
	
	public PostCreationDTO(String idposter, String nickname, String title, String content, String fileName, Long parentId,
			boolean isPinned) {
		super();
		this.idposter = idposter;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.fileName = fileName;
		this.parentId = parentId;
		this.isPinned = isPinned;
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
	
	
}
