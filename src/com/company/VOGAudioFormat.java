package com.company;

/*
 * CD Audio -> 16 bit little endian, 44100 hz, stereo
 * Signed - zatim true
 */

public class VOGAudioFormat {
    private PredefinedAudioFormats predefinedAudioFormat;
    private float sample_rate;
    private int sample_size_bits;
    private int channels;
    private boolean signed;
    private boolean bigEndian;

    public VOGAudioFormat(PredefinedAudioFormats format) {
        this.predefinedAudioFormat = format;

        switch (format) {
            case CD_QUALITY_STEREO:
                sample_rate = 44100;
                sample_size_bits = 16;
                channels = 2;
                signed = true;
                bigEndian = false;
                break;
            case CD_QUALITY_MONO:
                sample_rate = 44100;
                sample_size_bits = 16;
                channels = 1;
                signed = true;
                bigEndian = false;
                break;
            case LOW_QUALITY_STEREO:
                sample_rate = 8000;
                sample_size_bits = 16;
                channels = 2;
                signed = true;
                bigEndian = false;
                break;
            case LOW_QUALITY_MONO:
                sample_rate = 8000;
                sample_size_bits = 16;
                channels = 1;
                signed = true;
                bigEndian = false;
                break;
        }
    }

    public VOGAudioFormat(int sample_rate, int sample_size_bits, int channels, boolean signed, boolean bigEndian) {
        this.sample_rate = sample_rate;
        this.sample_size_bits = sample_size_bits;
        this.channels = channels;
        this.signed = signed;
        this.bigEndian = bigEndian;
        this.predefinedAudioFormat = PredefinedAudioFormats.CUSTOM;
    }

    public PredefinedAudioFormats getPredefinedAudioFormat() {
        return predefinedAudioFormat;
    }

    public void setPredefinedAudioFormat(PredefinedAudioFormats predefinedAudioFormat) {
        this.predefinedAudioFormat = predefinedAudioFormat;
    }

    public float getSample_rate() {
        return sample_rate;
    }

    public int getSample_size_bits() {
        return sample_size_bits;
    }

    public int getChannels() {
        return channels;
    }

    public boolean isSigned() {
        return signed;
    }

    public boolean isBigEndian() {
        return bigEndian;
    }

    public String toString() {
        return "Rate: " + sample_rate + " Sample size: " + sample_size_bits + " Channels: " + channels + " Signed: " + signed + " BigEndian: " + bigEndian;
    }
}
