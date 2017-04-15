package com.company;

import javax.sound.sampled.*;
import java.net.DatagramSocket;

/*
 * CD Audio -> 16 bit little endian, 44100 hz, stereo
 * Signed - zatim true
 */

public class Main {
    static int port = 50123;
    static int buff_size = 512;
    static int sampling_rate = 44100;

    public static void main(String[] args) throws Exception {
        System.out.println("Running at port: " + port);

        // Socket
        DatagramSocket datagramSocket = new DatagramSocket(port);
        byte[] audioBuff = new byte[buff_size];

        // Audio setup
        AudioFormat cdAudioFormat = new AudioFormat(44100, 16, 2, true, false);
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, cdAudioFormat);
        System.out.println("Data line info: " + dataLineInfo.toString());
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

        sourceDataLine.open(cdAudioFormat);
        sourceDataLine.start();

        // Get audio data from file
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("sample.wav"));

        while(true) {
            audioInputStream.read(audioBuff);
            sourceDataLine.write(audioBuff, 0, buff_size);
        }

    }
}
