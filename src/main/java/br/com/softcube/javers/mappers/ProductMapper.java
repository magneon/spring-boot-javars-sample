package br.com.softcube.javers.mappers;

import br.com.softcube.javers.domains.EProduct;
import br.com.softcube.javers.models.dto.product.DProduct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    List<DProduct> fromEProductListToDProductList(List<EProduct> products);

    DProduct fromEProductToDProduct(EProduct product);
}