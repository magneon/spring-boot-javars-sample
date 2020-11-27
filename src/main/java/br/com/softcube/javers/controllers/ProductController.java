package br.com.softcube.javers.controllers;

import br.com.softcube.javers.domains.EProduct;
import br.com.softcube.javers.mappers.ProductMapper;
import br.com.softcube.javers.models.dto.common.DResponse;
import br.com.softcube.javers.models.dto.product.DProduct;
import br.com.softcube.javers.services.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DResponse<DProduct>> getProductById(@PathVariable("id") Long id) {
        EProduct product = productService.getProductById(id);

        if (product == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(DResponse.ok(productMapper.fromEProductToDProduct(product)));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProduct(@RequestBody DProduct dProduct) {
        EProduct product = productService.update(productMapper.fromDProductToEProduct(dProduct));
        if (product == null) {
            return null;
        }

        return ResponseEntity.ok(DResponse.ok(productMapper.fromEProductToDProduct(product)));
    }

    @GetMapping(value = "/audit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity audit(@PathVariable("id") Long id) {
        productService.getUpdatesAtProductById(id);
        return null;
    }

}