package com.company;

import javafx.application.Platform;

import javax.sound.sampled.*;
import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class PlayService {
    private int port;
    private VOGAudioFormat vogAudioFormat;
    private int buffer_size;
    private byte[] audioBuffer;
    private DatagramSocket socket;

    public PlayService(int port, VOGAudioFormat vogAudioFormat) {
        this.port = port;
        this.vogAudioFormat = vogAudioFormat;
        buffer_size = 512;
        audioBuffer = new byte[buffer_size];
    }

    public void run() {
        // Socket
        System.out.println("Starting at port: " + port);
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        // Audio setup
        SourceDataLine sourceDataLine = setupAudioDataLine();
        sourceDataLine.start();

        byte[] udpBuffer = new byte[buffer_size];
        DatagramPacket packet = null;

        int written = 0;
        boolean firs = true;
        packet = new DatagramPacket(udpBuffer, udpBuffer.length);
        while(true) {



            try {
                System.out.println("Receiving ...");
                socket.receive(packet);

                written += udpBuffer.length;

                System.out.println("Received ..." + udpBuffer.length);
                if(firs) {
                    System.out.println(Arrays.toString(udpBuffer));
                }
                firs = false;

                sourceDataLine.write(udpBuffer, 0, udpBuffer.length);
                System.out.println("Writen to sound card ..." + written);



            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }

    public void testSocket() {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }

        byte[] udpBuffer = new byte[buffer_size];
        DatagramPacket packet = null;

        int written = 0;
        boolean firs = true;
        packet = new DatagramPacket(udpBuffer, udpBuffer.length);
        while(true) {

            try {
                System.out.println("Receiving ...");
                socket.receive(packet);
                System.out.println("Received: " + written);
                written += udpBuffer.length;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private SourceDataLine setupAudioDataLine() {
        SourceDataLine sourceDataLine = null;

        AudioFormat cdAudioFormat = new AudioFormat(vogAudioFormat.getSample_rate(), vogAudioFormat.getSample_size_bits(), vogAudioFormat.getChannels(), vogAudioFormat.isSigned(), vogAudioFormat.isBigEndian());
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, cdAudioFormat);

        System.out.println("Data line info: " + dataLineInfo.toString());

        try {
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(cdAudioFormat);
        } catch (javax.sound.sampled.LineUnavailableException e) {
            e.printStackTrace();
        }

        return sourceDataLine;
    }

    private void playFromFile(SourceDataLine sourceDataLine) {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream("c:\\Users\\mtkew\\Documents\\Projekty\\vog\\chunky\\sample3");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int writen;
        while(true) {
            try {
                fileInputStream.read(audioBuffer);
                writen = sourceDataLine.write(audioBuffer, 0, audioBuffer.length);
                System.out.println("Write OK > " + writen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
