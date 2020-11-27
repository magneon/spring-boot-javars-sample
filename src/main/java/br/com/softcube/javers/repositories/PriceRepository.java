package br.com.softcube.javers.repositories;

import br.com.softcube.javers.domains.EPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<EPrice, Integer> {}