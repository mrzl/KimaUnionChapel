package main.transitions;

import main.KimaConstants;
import main.Main;
import osc.*;

/**
 * Created by mrzl on 20.02.2015.
 */
public class AuroraChapter2  extends Piece implements PieceInterface {

    public AuroraChapter2 ( TransitionController _tc ) {
        super( _tc );
    }

    @Override
    public void select () {
        // VOICE - TRIANGLE - CHANNEL 1
        OscParameterMapping voiceTriangleChannel1 = new OscParameterMapping( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.TRIANGLE1 ) );
        OscInputParameter sin11 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER1, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter cpp11 = new ChladniPatternParameter( ChladniPatternParameterEnum.SCALE, KimaConstants.TRIANGLE_SCALES_MIN, KimaConstants.TRIANGLE_SCALES_MAX );
        voiceTriangleChannel1.addMapping( sin11, cpp11 );

        // ORGAN - SQUARE - CHANNEL 3
        OscParameterMapping organRectChannel3 = new OscParameterMapping( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ) );
        OscInputParameter sin31 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter cpp31 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.RECTANGLE_M_MIN, KimaConstants.RECTANGLE_M_MAX );
        OscInputParameter sin32 = new OscInputParameter( OscParameterInputEnum.FUNDAMENTAL_PARAMETER3, KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX );
        ChladniPatternParameter cpp32 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        organRectChannel3.addMapping( sin31, cpp31 );
        organRectChannel3.addMapping( sin32, cpp32 );

        getTransitionController().getOscController().addSoundParameterMapping( voiceTriangleChannel1 );
        getTransitionController().getOscController().addSoundParameterMapping( organRectChannel3 );
    }
}
