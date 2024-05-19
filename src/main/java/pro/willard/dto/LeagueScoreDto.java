package pro.willard.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class LeagueScoreDto {
    private final TeamScoreDto home;
    private final TeamScoreDto away;
}
