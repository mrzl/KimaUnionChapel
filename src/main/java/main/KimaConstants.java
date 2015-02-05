package main;

/**
 * Created by mrzl on 02.02.2015.
 */
public class KimaConstants {
    // attack = [0,1}
    // frequency = [200,10000]
    // amplitude = [0,0.99]
    public static final int MIN_FREQUENCY = 430;
    public static final int MAX_FREQUENCY = 4000;
    public static final float MIN_AMPLITUDE = 0.01f;
    public static final float MAX_AMPLITUDE = 39.99f;

    public static final int MIN_ATTACK = 0;
    public static final int MAX_ATTACK = 1;

    public static final float FREQUENCY_VOICE_MIN = 430;
    public static final float FREQUENCY_VOICE_MAX = 3937;
    public static final float AMPLITUDE_VOICE_MIN = 0.01f;
    public static final float AMPLITUDE_VOICE_MAX = 39.99f;

    public static final float FREQUENCY_PERCUSSION_MIN = 430;
    public static final float FREQUENCY_PERCUSSION_MAX = 2080;
    public static final float AMPLITUDE_PERCUSSION_MIN = 0.01f;
    public static final float AMPLITUDE_PERCUSSION_MAX = 39.99f;

    public static final float FREQUENCY_ORGAN_MIN = 430;
    public static final float FREQUENCY_ORGAN_MAX = 3544;
    public static final float AMPLITUDE_ORGAN_MIN = 0.01f;
    public static final float AMPLITUDE_ORGAN_MAX = 39.99f;

    public static final int MIN_MIDI = 0;
    public static final int MAX_MIDI = 127;
}
