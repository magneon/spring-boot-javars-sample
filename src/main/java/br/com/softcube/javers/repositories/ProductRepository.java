package br.com.softcube.javers.repositories;

import br.com.softcube.javers.domains.EProduct;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface ProductRepository extends JpaRepository<EProduct, Long> {}