package com.app.BullyBeGone.domain.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rule {

    @NotBlank
    private String id;

    @NotBlank
    private String description;

    private int weight;

}
