package com.edgaritzak.imageBoard.dto;

public class RequestReplyDTO {
    private String authorId;
    private String nickname;
    private String content;
    private Long threadId;
    private Long boardId;

    public RequestReplyDTO(String authorId, String nickname, String content, Long boardId, Long threadId) {
        this.authorId = authorId;
        this.nickname = nickname;
        this.threadId = threadId;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getThreadId() {
        return threadId;
    }
    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }
    public Long getBoardId() {
        return boardId;
    }
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
}