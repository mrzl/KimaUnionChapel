package pattern;

import main.Main;
import processing.core.PConstants;
import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mrzl on 04.02.2015.
 */
public class HydrogenCircle extends ChladniSurface {

    public static final float HYDROGEN_MIN_M = 0.0f;
    public static final float HYDROGEN_MAX_M = 20.0f;

    private static final String IMAGES_LOCATION = "media" + File.separator + "hydrogen_wave_function" + File.separator;
    private ArrayList< PImage > images;

    public HydrogenCircle ( Main p, int width, int height ) {
        super( p, width, height );

        images = new ArrayList<>();
        images.add( p.loadImage( IMAGES_LOCATION + "hw_200.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_210.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_211.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_300.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_310.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_311.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_320.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_321.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_322.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_400.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_410.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_411.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_420.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_421.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_422.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_430.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_431.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_432.jpg" ) );
        images.add( p.loadImage( IMAGES_LOCATION + "hw_433.jpg" ) );

        for( PImage im : images ) {
            im.resize( width, height );
        }

    }

    public void update() {
        getBuffer().beginDraw( );
        getBuffer().colorMode( PConstants.HSB );
        getBuffer().tint( getMinHue() * 255, 255, 255 );
        int currentIndex = ( int ) p.map( getM(), HYDROGEN_MIN_M, HYDROGEN_MAX_M, 0, images.size() );
        getBuffer( ).image( images.get( currentIndex ), 0, 0 );

        getBuffer().endDraw();
    }
}
