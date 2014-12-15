#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_COLOR_SHADER
#define M_PI 3.1415926535897932384626433832795

uniform sampler2D texture;
uniform vec2 texOffset;

varying vec4 vertColor;
varying vec4 vertTexCoord;

uniform float m;
uniform float n;
uniform float epsilon;
uniform vec2 resolution;

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main(void) {

  vec2 position = ( gl_FragCoord.xy / resolution.xy );
  //vec2 pixel = 1.0/resolution;

  float chladni = cos( n * M_PI * position.x ) * cos( m * M_PI * position.y ) - cos( m * M_PI * position.x ) * cos( n * M_PI * position.y );
  // if( abs(chladni) <= epsilon ) {
  //  gl_FragColor = vec4( 0.0, 0.0, 0.0, 1.0 );
  //}
  float fin = abs(chladni);

  if( rand( position ) > fin ) {
    fin = 1.0;
  } else {
    fin = 0.0;
  }
  gl_FragColor = vec4( fin, fin, fin, 1.0 );
}
