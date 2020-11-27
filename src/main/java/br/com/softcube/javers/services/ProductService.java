package br.com.softcube.javers.services;

import br.com.softcube.javers.domains.EProduct;
import br.com.softcube.javers.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Changes;
import org.javers.core.ChangesByCommit;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.PropertyChangeType;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private Javers javers;

    public List<EProduct> getAllProducts() {
        return productRepository.findAll();
    }

    public EProduct getProductById(Long id) {
        Optional<EProduct> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }

        return null;
    }

    public EProduct update(EProduct product) {
        return productRepository.save(product);
    }

    public void getUpdatesAtProductById(Long id) {
//        List<Shadow<Object>> shadows = javers.findShadows(QueryBuilder.byInstanceId(id, EProduct.class).build());
//        shadows.stream().forEach(shadow -> {log.info(shadow.toString());});
//
//        List<CdoSnapshot> snapshots = javers.findSnapshots(QueryBuilder.byInstanceId(id, EProduct.class).build());
//        snapshots.stream().forEach(snapshot -> {log.info(snapshot.toString());});

        Changes changes = javers.findChanges(QueryBuilder.byInstanceId(id, EProduct.class).build());
        log.info(changes.prettyPrint());

        List<ValueChange> changesByType = changes.getChangesByType(ValueChange.class);
        changesByType.stream().forEach(changeByType -> {
            log.info(changeByType.getPropertyName() + " mudou de " + changeByType.getLeft() + " para " + changeByType.getRight());
        });
    }
}