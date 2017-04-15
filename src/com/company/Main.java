package com.company;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 * CD Audio -> 16 bit little endian, 44100 hz, stereo
 * Signed - zatim true
 */

public class Main {
    static int port = 50123;
    static int buff_size = 512;
    static int sampling_rate = 44100;
    static AudioInputStream audioInputStream;

    public static void main(String[] args) throws Exception {
        System.out.println("Running at port: " + port);

        // Socket
        DatagramSocket datagramSocket = new DatagramSocket(port);
        byte[] audioBuff = new byte[buff_size];

        System.out.printf("Listening on udp:%s:%d%n", InetAddress.getLocalHost().getHostAddress(), port);
        DatagramPacket receivePacket = new DatagramPacket(audioBuff, audioBuff.length);

        // Audio setup
        AudioFormat cdAudioFormat = new AudioFormat(44100, 16, 2, true, false);
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, cdAudioFormat);
        System.out.println("Data line info: " + dataLineInfo.toString());
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

        sourceDataLine.open(cdAudioFormat);
        sourceDataLine.start();

        // Get audio data from file
        // AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("sample.wav"));



        // Get audio from UDP
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivePacket.getData());

        //AudioInputStream audioInputStream = AudioSystem.;

        while(true) {

            System.out.println("DATA ARRIVED");


            datagramSocket.receive(receivePacket);

            System.out.println(receivePacket.getData());

            audioInputStream = new AudioInputStream(byteArrayInputStream, cdAudioFormat, receivePacket.getLength());

            // to speaker
            try{
                sourceDataLine.write(receivePacket.getData(), 0, receivePacket.getData().length);
            } catch (Exception e) {
                System.out.println("Playback error!");
                e.printStackTrace();
            }

//            audioInputStream.read(audioBuff);
//            sourceDataLine.write(audioBuff, 0, buff_size);
        }

    }


}
