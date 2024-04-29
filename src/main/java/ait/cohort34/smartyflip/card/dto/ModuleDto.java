package ait.cohort34.smartyflip.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDto {
    String moduleName;
    Set<String> tags;
    String stackName;
    boolean isPublic;
}
