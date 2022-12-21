package com.javafinal.WebMail.Model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends CrudRepository<Email, Integer> {

    List<Email> findEmailByReceiver(User receiver);
    List<Email> findEmailBySender(User sender);

    @Query(value = "Select * from email where receiver_user_id = ?1 or sender_user_id = ?1",nativeQuery = true)
    List<Email> findEmailBySenderOrReceiver(int userId);
    Email findEmailByEmailId(int emailId);

    List<Email> findEmailByReceiverAndArchive(User receiver, String archive);
}
