package com.edgaritzak.imageBoard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edgaritzak.imageBoard.model.Media;

public interface MediaRepository extends JpaRepository<Media, Long>{

  public List<Media> findByPostId(Long id);
}
