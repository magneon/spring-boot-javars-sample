package br.com.softcube.javers.controllers;

import br.com.softcube.javers.domains.EProduct;
import br.com.softcube.javers.mappers.ProductMapper;
import br.com.softcube.javers.models.dto.product.DProduct;
import br.com.softcube.javers.models.dto.common.DResponse;
import br.com.softcube.javers.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/product")
public class ProductController {

    private ProductService productService;
    private ProductMapper productMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DResponse<List<DProduct>>> getProducts() {
        List<EProduct> products = productService.getAllProducts();
        List<DProduct> dProducts = productMapper.fromEProductListToDProductList(products);

        return ResponseEntity.ok(DResponse.ok(dProducts));
    }

}