package com.company;

public class Main {
    public static void main(String[] args) throws Exception {
        VOGAudioFormat testFormat = new VOGAudioFormat(
                44100,
                16,
                2,
                true,
                false
        );

        PlayService playService = new PlayService(50123, testFormat);
        playService.run();

//        playService.testSocket();


//        VOGAudioFormat audioFormat = new VOGAudioFormat(PredefinedAudioFormats.CD_QUALITY_MONO);
//        VOGAudioFormat shitFormat = new VOGAudioFormat(8000, 8, 1, false, false);
//        VOGAudioFormat webFormat = new VOGAudioFormat(44100, 16, 1, false, false);
    }
}
