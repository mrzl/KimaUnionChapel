#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_COLOR_SHADER
#define M_PI 3.1415926535897932384626433832795


uniform sampler2D src_tex_unit0;
uniform sampler2D src_tex_unit1;
uniform sampler2D src_tex_unit2;

uniform int numColors;
uniform int MAX_PARTICLES;
uniform int NUM_PARTICLES;

float fmp = float(MAX_PARTICLES);

float sumXY() {
    float sum = 0.0;
    for (int i=0; i<NUM_PARTICLES; i++) {
        vec2 coordinate = vec2((float(i)+0.5)/fmp, 0.5);
        vec4 p = texture2D(src_tex_unit1, coordinate).rgba;
        sum += p.a * p.z / distance(p.xy, gl_FragCoord.xy);
    }
    return sum;
}

void main(void) {

  vec2 position = ( gl_FragCoord.xy / resolution.xy );
  float chladni = cos( n * M_PI * position.x ) * cos( m * M_PI * position.y ) - cos( m * M_PI * position.x ) * cos( n * M_PI * position.y );

  float fin = abs(chladni);

  gl_FragColor = vec4( fin, fin, fin, 1.0 );

  float s = sumXY();
  vec4 offscreen_color = texture2D(src_tex_unit2, gl_TexCoord[2].st).rgba;
  s *= smoothstep(0.0, 1.0, offscreen_color.r);
  int colorSelect = int((float(numColors)-1.0)*clamp(s, 0.0, 1.0));
  vec2 coordinate = vec2((float(colorSelect)+0.5)/float(numColors), 0.5);
  gl_FragColor = texture2D(src_tex_unit0, coordinate).rgba;
}
