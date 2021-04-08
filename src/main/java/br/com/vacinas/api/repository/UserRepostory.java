package br.com.vacinas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vacinas.api.model.User;

@Repository
public interface UserRepostory extends JpaRepository<User, Long> {
    
}
