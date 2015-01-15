#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_COLOR_SHADER
#define M_PI 3.1415926535897932384626433832795

uniform sampler2D texture;
uniform vec2 texOffset;

uniform vec2 resolution;
uniform int pointcount;
uniform float points_x[];
uniform float points_y[];
uniform sampler2D pointData;

float rand(vec2 co)
{
   return fract(sin(dot(co.xy,vec2(12.9898,78.233))) * 43758.5453);
}

void main(void) {
  vec2 pos = ( gl_FragCoord.xy / resolution.xy );
  vec4 col = texture2D( pointData, pos );

  //col.x = col.x + ( rand( pos ) / 100.0 );
  //for( int i = 0; i < points_x)
  gl_FragColor = vec4(col.xyz, 1.0);
}
