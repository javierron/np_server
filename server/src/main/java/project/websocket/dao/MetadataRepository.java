package project.websocket.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import project.websocket.model.Connection;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface MetadataRepository extends JpaRepository<Connection,Long> {

}
