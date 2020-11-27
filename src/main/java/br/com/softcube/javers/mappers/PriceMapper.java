package br.com.softcube.javers.mappers;

import br.com.softcube.javers.domains.EPrice;
import br.com.softcube.javers.models.dto.price.DPrice;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    List<DPrice> fromEPriceListToDPriceList(List<EPrice> prices);

    DPrice fromEPriceToDPrice(EPrice price);

}