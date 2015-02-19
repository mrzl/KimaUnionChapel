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
uniform float brightness;
uniform float contrast;

void main(void) {
  vec2 position = ( gl_FragCoord.xy / resolution.xy );
  vec4 col = texture2D(texture, position);

  col.xyz = ((col.xyz - 0.5) * max(contrast, 0.0)) + 0.5;

    // Apply brightness.
    col.xyz += brightness;

    // Return final pixel color.
    col.rgb *= col.a;

  gl_FragColor = vec4( col.xyz, 1.0 );
}
