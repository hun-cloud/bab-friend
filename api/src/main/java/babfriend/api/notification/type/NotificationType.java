package babfriend.api.notification.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationType {
    ALL("전체 알림"),
    NORMAL("일반 알림"),
    EVENT("이벤트 알림"),
    PENALTY("패널티 알림");

    private final String text;
}
