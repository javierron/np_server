package project.websocket.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import project.websocket.model.Metadata;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface MetadataRepository extends JpaRepository<Metadata,Long> {
    List<Metadata> findFirst10ByOrderByIdDesc();
}
