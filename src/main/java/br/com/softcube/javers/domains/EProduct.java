package br.com.softcube.javers.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "product")
@Table(name = "product")
public class EProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 200, nullable = false)
    private String name;
    @Column(name = "description", length = 200)
    private String description;
    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<EPrice> prices;
}