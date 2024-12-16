package com.edgaritzak.imageBoard.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "code_name")
	private String codeName;
	
	@OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST)
	private Set<PostThread> threads;
	
	@OneToOne(mappedBy = "board", cascade = CascadeType.PERSIST)
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