package br.com.softcube.javers.repositories;

import br.com.softcube.javers.domains.EProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<EProduct, Integer> {}