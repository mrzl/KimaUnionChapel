package main.transitions;

import main.Main;
import main.transitions.pieces.*;
import midi.VisualParameterEnum;
import osc.OscController;
import pattern.ChladniParticles;
import pattern.ColorModeEnum;
import pattern.RenderMode;

/**
 * Created by mrzl on 20.02.2015.
 */
public class TransitionController {
    private Main p;
    private OscController osc;
    private Piece currentPiece;

    public TransitionController( Main p, OscController _osc) {
        this.p = p;
        this.osc = _osc;
        this.currentPiece = new Piece( this );
    }

    public void select (  ChladniParticles particles, VisualParameterEnum selectedPiece ) {
        osc.clear( );
        selectPart( particles, selectedPiece );
        currentPiece.stopColorTransitioning();
        switch( selectedPiece ) {
            case AXIS_MUNDI_1:
                currentPiece = new AxisMundiChapter1( this );
                currentPiece.select();
                break;
            case AXIS_MUNDI_2:
                currentPiece = new AxisMundiChapter2( this );
                currentPiece.select();
                break;
            case AXIS_MUNDI_3:
                currentPiece = new AxisMundiChapter3( this );
                currentPiece.select();
                break;
            case AXIS_MUNDI_4:
                currentPiece = new AxisMundiChapter4( this );
                currentPiece.select();
                break;
            case MARE_UNDARUM_1:
                currentPiece = new MareUndarumChapter1( this );
                currentPiece.select();
                break;
            case MARE_UNDARUM_2:
                currentPiece = new MareUndarumChapter2( this );
                currentPiece.select();
                break;
            case MARE_UNDARUM_3:
                currentPiece = new MareUndarumChapter3( this );
                currentPiece.select();
                break;
            case AURORA_1:
                currentPiece = new AuroraChapter1( this );
                currentPiece.select();
                break;
            case AURORA_2:
                currentPiece = new AuroraChapter2( this );
                currentPiece.select();
                break;
            case AURORA_3:
                currentPiece = new AuroraChapter3( this );
                currentPiece.select();
                break;
            case AURORA_4:
                currentPiece = new AuroraChapter4( this );
                currentPiece.select();
                break;
            case AURORA_5:
                currentPiece = new AuroraChapter5( this );
                currentPiece.select();
                break;
            case AURORA_6:
                currentPiece = new AuroraChapter6( this );
                currentPiece.select();
                break;
            default:
                //System.err.println( "Wrong ID in TransitionController" );
                break;
        }

    }

    private void selectPart( ChladniParticles particles, VisualParameterEnum part ) {
        switch (part ) {
            case MARE_UNDARUM_1:
            case MARE_UNDARUM_2:
            case MARE_UNDARUM_3:
                particles.setParticleJumpyness( 40.0f );
                particles.setParticleSize( 3.0f );
                particles.setParticleCount( 10500 );
                particles.setParticleOpacity( 0.23f );
                particles.setBackgroundOpacity( 255 );
                particles.setIntensity( 1.0f );
                particles.setRenderMode( RenderMode.ORIGINAL);
                particles.getBloomModifier().setEnabled( false );
                particles.getOpacityToHueShader().setEnabled( false );
                particles.getBrightnessContrastShader().setEnabled( true );
                particles.getBrightnessContrastShader().setBrightness( 0.0f );
                particles.getBrightnessContrastShader().setContrast( 1.0f );
                particles.getSurface().setCutoff( 0.0f );
                particles.getDirectionalBlur2().setEnabled( false );

                particles.getColorMode().setRange( 0.06f, 0.06f );
                particles.setColorModeEnum( ColorModeEnum.MONOCHROME );
                particles.getSurface().setDrawMonochrome( false );
                particles.getSurface().setUseGradient( false );
                break;
            case AXIS_MUNDI_1:
            case AXIS_MUNDI_2:
            case AXIS_MUNDI_3:
            case AXIS_MUNDI_4:
                particles.setParticleJumpyness( 80.0f );
                particles.setParticleSize( 6.0f );
                particles.setParticleCount( 10500 );
                particles.setParticleOpacity( 0.2f );
                particles.setBackgroundOpacity( 6 );
                particles.setIntensity( 1.0f );
                particles.setRenderMode( RenderMode.POINTS );
                particles.getBloomModifier( ).setEnabled( false );
                particles.getOpacityToHueShader().setEnabled( false );
                particles.getBrightnessContrastShader().setEnabled( true );
                particles.getBrightnessContrastShader().setBrightness( 0.0f );
                particles.getBrightnessContrastShader().setContrast( 1.03f );
                particles.getSurface().setCutoff( 0.0f );
                particles.getDirectionalBlur2().setEnabled( false );

                particles.getColorMode().setRange( 0, 0.3f );
                particles.setColorModeEnum( ColorModeEnum.VELOCITIES );
                particles.getSurface().setDrawMonochrome( true );

                particles.getSurface().setUseGradient( false );
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
                particles.setBackgroundOpacity( 255 );
                particles.setIntensity( 1.0f );
                particles.setRenderMode( RenderMode.IMAGE_GRADIENT );
                particles.getBloomModifier().setEnabled( false );
                particles.getBloomModifier().setBlurSize( 80 );
                particles.getBloomModifier().setBlurSigma( 12.6f );
                particles.getBloomModifier().setThreshold( 0.01f );
                particles.getOpacityToHueShader().setEnabled( false );
                particles.getOpacityToHueShader().setMinHue( 0.0f );
                particles.getOpacityToHueShader().setMaxHue( 0.12f );
                particles.getBrightnessContrastShader().setEnabled( true );
                particles.getBrightnessContrastShader().setBrightness( 0.0f );
                particles.getBrightnessContrastShader().setContrast( 1.0f );
                particles.getSurface().setCutoff( 0.8f );
                particles.getDirectionalBlur2().setEnabled( true );
                particles.getDirectionalBlur2().setDirection( 2.27f );
                particles.getDirectionalBlur2().setRadius( 0.087f );
                particles.getSurface().setUseGradient( true );

                particles.setIntensity( 0.84f );

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
