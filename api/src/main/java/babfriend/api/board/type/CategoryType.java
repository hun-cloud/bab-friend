package babfriend.api.board.type;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryType {
    KOREAN("한식"),
    WEST("양식"),
    CHINA("중식"),
    JAPAN("일식"),
    ALL("전체");

    private final String text;
}
