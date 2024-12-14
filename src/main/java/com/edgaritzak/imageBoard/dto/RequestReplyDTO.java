package com.edgaritzak.imageBoard.dto;

public class RequestReplyDTO {
    private String authorId;
    private String nickname;
    private String threadId;
    private String content;
    private Long boardId;

    public RequestReplyDTO(String authorId, String nickname, String threadId, String content, Long boardId) {
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
    public String getThreadId() {
        return threadId;
    }
    public void setThreadId(String threadId) {
        this.threadId = threadId;
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
