package br.com.softcube.javers.services;

import br.com.softcube.javers.domains.EProduct;
import br.com.softcube.javers.models.dto.audit.DAudit;
import br.com.softcube.javers.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.commit.CommitMetadata;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ObjectRemoved;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public EProduct createProduct(EProduct product) {
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Optional<EProduct> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            productRepository.delete(optional.get());
        }
    }

    public List<DAudit> getUpdatesAtProductById(Long id) {
        Changes changes = javers.findChanges(QueryBuilder.byInstanceId(id, EProduct.class).withNewObjectChanges(true).build());

        return groupAllChanges(changes);
    }

    public List<DAudit> getUpdatesAtProduct() {
        Changes changes = javers.findChanges(QueryBuilder.byClass(EProduct.class).withNewObjectChanges(true).build());

        return groupAllChanges(changes);
    }

    private List<DAudit> groupAllChanges(Changes changes) {
        List<DAudit> audits = new ArrayList<>(0);

        List<ValueChange> valueChanges = changes.getChangesByType(ValueChange.class);
        audits.addAll(valueChanges.stream().map(this::fromChangesToDAudit).collect(Collectors.toList()));

        List<ObjectRemoved> objectRemoveds = changes.getChangesByType(ObjectRemoved.class);
        audits.addAll(objectRemoveds.stream().map(this::fromChangesToDAudit).collect(Collectors.toList()));

        List<NewObject> newObjects = changes.getChangesByType(NewObject.class);
        audits.addAll(newObjects.stream().map(this::fromChangesToDAudit).collect(Collectors.toList()));

        return audits.stream().sorted(Comparator.comparing(DAudit::getWhen).reversed()).collect(Collectors.toList());
    }

    private DAudit fromChangesToDAudit(Change change) {
        DAudit.DAuditBuilder builder = DAudit.builder();

        if (change instanceof ValueChange) {
            ValueChange valueChange = (ValueChange) change;

            Optional<CommitMetadata> optional = change.getCommitMetadata();
            if (optional.isPresent()) {
                CommitMetadata metadata = optional.get();

                builder
                        .when(metadata.getCommitDate())
                        .who(metadata.getAuthor());
            }

            builder
                    .objectId((Long) valueChange.getAffectedLocalId())
                    .what(valueChange.getPropertyName())
                    .from(valueChange.getLeft() == null ? "" : valueChange.getLeft().toString())
                    .to(valueChange.getRight() == null ? "" : valueChange.getRight().toString())
                    .type(ChangeType.UPDATE.name());

        } else if (change instanceof ObjectRemoved) {
            ObjectRemoved objectRemoved = (ObjectRemoved) change;

            Optional<CommitMetadata> optional = objectRemoved.getCommitMetadata();
            if (optional.isPresent()) {
                CommitMetadata metadata = optional.get();

                builder
                        .objectId((Long) objectRemoved.getAffectedLocalId())
                        .who(metadata.getAuthor())
                        .when(metadata.getCommitDate())
                        .type(ChangeType.DELETE.name());
            }

        } else {
            NewObject newObject = (NewObject) change;

            Optional<CommitMetadata> optional = newObject.getCommitMetadata();
            if (optional.isPresent()) {
                CommitMetadata metadata = optional.get();

                builder
                        .objectId((Long) newObject.getAffectedLocalId())
                        .who(metadata.getAuthor())
                        .when(metadata.getCommitDate())
                        .type(ChangeType.INSERT.name());
            }
        }

        return builder.build();
    }

    private enum ChangeType {
        UPDATE,
        DELETE,
        INSERT;
    }
}