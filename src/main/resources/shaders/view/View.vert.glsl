#version 440

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;

out FragParams {
    vec2 texCoord;
} fragParams;

vec4 screenPosition(in vec3 position) {
    return viewMatrix * modelMatrix * vec4(position, 1.0);
}

void main() {
    fragParams.texCoord = texCoord;
    gl_Position = projectionMatrix * screenPosition(position);
}