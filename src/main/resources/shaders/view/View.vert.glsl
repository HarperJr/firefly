#version 440

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

layout(location = 0) in vec3 position;
layout(location = 2) in vec2 texCoords;
layout(location = 3) in vec4 color;

out FragParams {
    vec2 texCoords;
    vec4 color;
} fragParams;

vec4 screenPosition(in vec3 position) {
    return modelViewMatrix * vec4(position, 1.0);
}

void main() {
    fragParams.texCoords = texCoords;
    fragParams.color = color;

    gl_Position = projectionMatrix * screenPosition(position);
}