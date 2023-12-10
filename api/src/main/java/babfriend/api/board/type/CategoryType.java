package babfriend.api.board.type;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryType {
    KOREAN("한식"),
    USA("양식"),
    CHINA("중식"),
    JAPAN("일식");

    private final String text;
}
