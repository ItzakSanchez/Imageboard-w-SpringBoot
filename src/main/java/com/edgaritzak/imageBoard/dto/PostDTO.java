package com.edgaritzak.imageBoard.dto;

public class PostDTO {
	private String idposter;
	private String nickname;
	private String title;
	private String content;
	private String imagePath;
	private Long parentId;
	private boolean isPinned;
	
	public PostDTO() {
		
	}
	
	public PostDTO(String idposter, String nickname, String title, String content, String imagePath, Long parentId,
			boolean isPinned) {
		super();
		this.idposter = idposter;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
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
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
