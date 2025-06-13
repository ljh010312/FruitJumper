package kr.ac.tukorea.ge.and.leejunho3288.fruitjumper.game;

public class GameData {
    private static GameData instance;

    // 싱글톤 인스턴스 접근
    public static GameData get() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    private final int[] starCounts = new int[3];


    // 별 개수 저장
    public void saveStarCount(int stageIndex, int count) {
        if (stageIndex >= 0 && stageIndex < starCounts.length) {
            // 기존보다 높은 별 수만 저장
            starCounts[stageIndex] = Math.max(starCounts[stageIndex], count);
        }
    }

    // 별 개수 불러오기
    public int getStarCount(int stageIndex) {
        if (stageIndex >= 0 && stageIndex < starCounts.length) {
            return starCounts[stageIndex];
        }
        return 0;
    }
}