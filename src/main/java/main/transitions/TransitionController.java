package main.transitions;

import main.Main;
import midi.VisualParameterEnum;
import osc.OscController;
import pattern.ChladniParticles;
import pattern.ColorModeEnum;
import pattern.RenderMode;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by mrzl on 20.02.2015.
 */
public class TransitionController {
    public enum STATE {
        MARE_UNDARUM_1, MARE_UNDARUM_2, MARE_UNDARUM_3,
        AXIS_MUNDI_1, AXIS_MUNDI_2, AXIS_MUNDI_3, AXIS_MUNDI_4,
        AURORA_1, AURORA_2, AURORA_3, AURORA_4, AURORA_5, AURORA_6
    }

    private Main p;
    private OscController osc;

    public TransitionController( Main p, OscController _osc) {
        this.p = p;
        this.osc = _osc;
    }

    public void select (  ChladniParticles particles, VisualParameterEnum selectedPiece ) {
        osc.clear( );
        selectPart( particles, selectedPiece );
        switch( selectedPiece ) {
            case AXIS_MUNDI_1:
                new AxisMundiChapter1( this ).select();
                break;
            case AXIS_MUNDI_2:
                new AxisMundiChapter2( this ).select();
                break;
            case AXIS_MUNDI_3:
                new AxisMundiChapter3( this ).select();
                break;
            case AXIS_MUNDI_4:
                new AxisMundiChapter4( this ).select();
                break;
            case MARE_UNDARUM_1:
                new MareUndarumChapter1( this ).select();
                break;
            case MARE_UNDARUM_2:
                new MareUndarumChapter2( this ).select();
                break;
            case MARE_UNDARUM_3:
                new MareUndarumChapter3( this ).select();
                break;
            case AURORA_1:
                new AuroraChapter1( this ).select();
                break;
            case AURORA_2:
                new AuroraChapter2( this ).select();
                break;
            case AURORA_3:
                new AuroraChapter3( this ).select();
                break;
            case AURORA_4:
                new AuroraChapter4( this ).select();
                break;
            case AURORA_5:
                new AuroraChapter5( this ).select();
                break;
            case AURORA_6:
                new AuroraChapter6( this ).select();
                break;
            default:
                //System.err.println( "Wrong ID in TransitionController" );
                break;
        }

    }

    private void selectPart( ChladniParticles particles, VisualParameterEnum part ) {
        switch (part ) {
            case AXIS_MUNDI_1:
            case AXIS_MUNDI_2:
            case AXIS_MUNDI_3:
            case AXIS_MUNDI_4:
                particles.setParticleJumpyness( 40.0f );
                particles.setParticleSize( 3.0f );
                particles.setParticleCount( 10500 );
                particles.setParticleOpacity( 0.07f );
                particles.setBackgroundOpacity( 6 );
                particles.setIntensity( 1.0f );
                particles.setRenderMode( RenderMode.POINTS );
                particles.getBloomModifier( ).setEnabled( false );
                particles.getOpacityToHueShader().setEnabled( false );
                particles.getBrightnessContrastShader().setEnabled( true );
                particles.getBrightnessContrastShader().setBrightness( 0.0f );
                particles.getBrightnessContrastShader().setContrast( 1.0f );

                particles.getColorMode().setRange( 0, 0.3f );
                particles.setColorModeEnum( ColorModeEnum.VELOCITIES );
                particles.getSurface().setDrawMonochrome( true );
                break;
            case MARE_UNDARUM_1:
            case MARE_UNDARUM_2:
            case MARE_UNDARUM_3:
                particles.setParticleJumpyness( 40.0f );
                particles.setParticleSize( 3.0f );
                particles.setParticleCount( 10500 );
                particles.setParticleOpacity( 0.23f );
                particles.setBackgroundOpacity( 255 );
                particles.setIntensity( 1.0f );
                particles.setRenderMode( RenderMode.ORIGINAL );
                particles.getBloomModifier().setEnabled( false );
                particles.getOpacityToHueShader().setEnabled( false );
                particles.getBrightnessContrastShader().setEnabled( true );
                particles.getBrightnessContrastShader().setBrightness( 0.0f );
                particles.getBrightnessContrastShader().setContrast( 1.0f );

                particles.getColorMode().setRange( 0.06f, 0.06f );
                particles.setColorModeEnum( ColorModeEnum.MONOCHROME );
                particles.getSurface().setDrawMonochrome( false );
                break;
            case AURORA_1:
            case AURORA_2:
            case AURORA_3:
            case AURORA_4:
            case AURORA_5:
            case AURORA_6:
                particles.setParticleJumpyness( 40.0f );
                particles.setParticleSize( 3.0f );
                particles.setParticleCount( 10500 );
                particles.setParticleOpacity( 0.23f );
                particles.setBackgroundOpacity( 40 );
                particles.setIntensity( 1.0f );
                particles.setRenderMode( RenderMode.POINTS );
                particles.getBloomModifier().setEnabled( true );
                particles.getBloomModifier().setBlurSize( 42 );
                particles.getBloomModifier().setBlurSigma( 8.0f );
                particles.getBloomModifier().setThreshold( 0.01f );
                particles.getOpacityToHueShader().setEnabled( true );
                particles.getOpacityToHueShader().setMinHue( 0.0f );
                particles.getOpacityToHueShader().setMaxHue( 0.12f );
                particles.getBrightnessContrastShader().setEnabled( true );
                particles.getBrightnessContrastShader().setBrightness( 0.0f );
                particles.getBrightnessContrastShader().setContrast( 1.0f );

                particles.getColorMode().setRange( 0.06f, 1 );
                particles.setColorModeEnum( ColorModeEnum.MONOCHROME );
                particles.getSurface().setDrawMonochrome( false );
                break;
        }
    }

    public Main getMain() {
        return p;
    }

    public OscController getOscController() {
        return osc;
    }
}
