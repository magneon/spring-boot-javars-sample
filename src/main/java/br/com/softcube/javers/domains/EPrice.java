package br.com.softcube.javers.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity(name = "price")
@Table(name = "price")
public class EPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value", scale = 6, precision = 2, nullable = false)
    private BigDecimal value;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    private EProduct product;
}