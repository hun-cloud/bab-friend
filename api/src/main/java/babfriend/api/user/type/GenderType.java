package babfriend.api.user.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GenderType {
    MALE("남성"),
    FEMALE("여성"),
    ALL("전체");

    private final String text;
}
