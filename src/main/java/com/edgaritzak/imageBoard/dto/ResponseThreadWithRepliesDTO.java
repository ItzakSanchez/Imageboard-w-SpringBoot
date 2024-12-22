package com.edgaritzak.imageBoard.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseThreadWithRepliesDTO {
    private ResponseThreadDTO threadDTO;
    private List<ResponseReplyDTO> replies;
    
    
  public ResponseThreadWithRepliesDTO() {}
	public ResponseThreadWithRepliesDTO(ResponseThreadDTO threadDTO, List<ResponseReplyDTO> replies) {
		this.threadDTO = threadDTO;
		this.replies = replies;
	}
	
	
	public ResponseThreadDTO getThreadDTO() {
		return threadDTO;
	}
	public void setThreadDTO(ResponseThreadDTO threadDTO) {
		this.threadDTO = threadDTO;
	}
	public List<ResponseReplyDTO> getReplies() {
		return replies;
	}
	public void setReplies(List<ResponseReplyDTO> replies) {
		this.replies = replies;
	}
    
}