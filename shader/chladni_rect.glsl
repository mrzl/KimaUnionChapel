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
uniform vec2 resolution;
uniform bool drawMonochrome;
uniform float minHue, maxHue, intensity;
uniform float time;
uniform float saturation;
uniform float l;
uniform float cutoff;

vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}


vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float map(float value, float start1, float stop1, float start2, float stop2) {
    return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
}

void main(void) {
  vec2 position = ( gl_FragCoord.xy / resolution.xy );
  float chladni = sin( n * M_PI * position.x / l ) * sin( m * M_PI * position.y / l ) - sin( m * M_PI * position.x / l ) * sin( n * M_PI * position.y / l );

  float fin = abs(chladni);

  float mapped = map( fin, 0.0, 1.0, minHue, maxHue );
  vec3 finalColor;

  if( drawMonochrome ) {
    finalColor = vec3( fin );
  } else {
    vec3 hsbColor = vec3( mapped, saturation, 1.0 - (fin * intensity) );
    hsbColor.z = clamp( hsbColor.z, cutoff, 1.0 );
    hsbColor.z = map( hsbColor.z, cutoff, 1.0, 0.0, 1.0 );
    hsbColor.x = map( fin, cutoff, 1.0, minHue, maxHue );

    finalColor = hsv2rgb( hsbColor );
  }


  //finalColor = vec3(mapped);

  gl_FragColor = vec4( finalColor, 1.0 );
}
