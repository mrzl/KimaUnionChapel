#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
uniform vec2 texOffset;
uniform vec2 resolution;

varying vec4 vertColor;
varying vec4 vertTexCoord;
uniform float amount;

void main(void) {
  vec2 position = ( gl_FragCoord.xy / resolution.xy );
  vec4 col0 = texture2D(texture, position);

  gl_FragColor = vec4( col0.x + amount, col0.y + amount, col0.z + amount, 1.0 );
}
