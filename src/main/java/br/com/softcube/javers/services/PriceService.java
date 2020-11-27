package br.com.softcube.javers.services;

import br.com.softcube.javers.domains.EPrice;
import br.com.softcube.javers.repositories.PriceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PriceService {

    private PriceRepository priceRepository;

    public List<EPrice> getPrices() {
        return priceRepository.findAll();
    }
}