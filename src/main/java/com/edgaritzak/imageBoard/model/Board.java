package com.edgaritzak.imageBoard.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Board name can not be blank")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Board name can only include letters and numbers")
	@Column(name = "name", unique = true)
	private String name;

	@NotBlank(message = "Board code can not be blank")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Board code can only include letters and numbers")
	@Length(min = 1, max = 5, message = "Board code must be 1 to 5 characters length")
	@Column(name = "code_name", unique = true)
	private String codeName;
	
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
	private Set<PostThread> threads;
	
	@OneToOne(mappedBy = "board", cascade = CascadeType.ALL)
	private BoardIdCounter boardIdCounter;
	
	public Board() {}
	public Board(String name, String codeName) {
		this.name = name;
		this.codeName = codeName;
		this.threads = new HashSet<PostThread>();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public Set<PostThread> getThreads() {
		return threads;
	}
	public void setThreads(Set<PostThread> threads) {
		this.threads = threads;
	}
	public BoardIdCounter getNextPostId() {
		return boardIdCounter;
	}
	public void setNextPostId(BoardIdCounter boardIdCounter) {
		this.boardIdCounter = boardIdCounter;
	}
}