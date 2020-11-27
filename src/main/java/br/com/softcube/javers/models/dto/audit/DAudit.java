package br.com.softcube.javers.models.dto.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DAudit {
    private Long objectId;
    private LocalDateTime when;
    private String what;
    private String from;
    private String to;
    private String who;
    private String type;
}