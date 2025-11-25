package com.app.BullyBeGone.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RulePatch {

    @NotBlank
    private String id;

    @NotBlank
    private String description;

    private int weight;

}
