package com.edgaritzak.imageBoard.dto;

public class RequestThreadDTO {
    private String authorId;
    private String nickname;
    private String title;
    private String content;
    private Long boardId;

    public RequestThreadDTO(String authorId, String nickname, String title, String content, Long boardId) {
        this.authorId = authorId;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.boardId = boardId;
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
    public Long getBoardId() {
        return boardId;
    }
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
}



