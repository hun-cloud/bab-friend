package babfriend.api.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PossibilityDto {

    private String msg;
    private boolean joinPossible;
    private boolean alreadyJoin;
}
