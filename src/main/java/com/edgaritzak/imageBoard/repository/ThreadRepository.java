package com.edgaritzak.imageBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.edgaritzak.imageBoard.model.PostThread;

public interface ThreadRepository extends JpaRepository<PostThread, Long>{

}
