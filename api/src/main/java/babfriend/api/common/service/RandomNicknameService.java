package babfriend.api.common.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.IntStream;

@Service
public class RandomNicknameService {

    public String createRandomNickname() {
        String[] firstNames = {"민첩한", "야망 있는", "서투른", "너그러운", "용감한", "정중한", "공손한",
        "근면한", "성실한", "느긋한", "관대한", "상냥한", "다정한", "공손한", "내성적인", "까다로운", "진실한",
        "사교적인", "사려 깊은", "다재다능한"};

        String[] secondNames = {"고양이", "강아지", "거북이", "토끼", "뱀", "사자", "호랑이", "표범", "치타",
                "하이에나", "기린", "코끼리", "코뿔소", "하마", "악어", "펭귄", "부엉이", "올빼미", "곰",
                "돼지", "소", "닭", "독수리", "타조", "고릴라", "오랑우탄", "침팬지", "원숭이",
                "코알라", "캥거루", "고래", "상어", "칠면조", "직박구리", "쥐",
                "청설모", "메추라기", "앵무새", "삵", "스라소니", "판다", "오소리",
                "오리", "거위", "백조", "두루미", "고슴도치", "두더지", "우파루파",
                "맹꽁이", "너구리", "개구리", "두꺼비", "카멜레온", "이구아나", "노루",
                "제비", "까치", "고라니", "수달", "당나귀", "순록", "염소", "공작",
                "바다표범", "들소", "박쥐", "참새", "물개", "바다사자", "살모사",
                "구렁이", "얼룩말", "산양", "멧돼지", "카피바라", "도롱뇽", "북극곰", "퓨마",
                "미어캣", "코요테", "라마", "딱따구리", "기러기", "비둘기", "스컹크", "돌고래",
                "까마귀", "매", "낙타", "여우", "사슴", "늑대", "재규어", "알파카", "양", "다람쥐", "담비"};

        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String secondName = secondNames[random.nextInt(secondNames.length)];

        return String.format("%s %s", firstName, secondName);
    }
}
