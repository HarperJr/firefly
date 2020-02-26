#version 440

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;
uniform sampler2D tex;
uniform vec4 color;

in FragParams {
    vec2 texCoord;
} fragParams;

out vec4 outColor;

void main() {
    outColor = color;
}
