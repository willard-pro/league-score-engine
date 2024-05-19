package pro.willard.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class TeamScoreDto {
    private final String name;
    private final int score;
}
