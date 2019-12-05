#version 440

struct FragParams {
    vec2 texCoords;
    vec4 color;
};

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

layout(location = 0) in vec3 position;
layout(location = 2) in vec2 texCoords;
layout(location = 3) in vec4 color;

out FragParams frag;

vec4 screenPosition(in vec3 position) {
    return modelViewMatrix * vec4(position, 1.0);
}

void main() {
    frag.texCoords = texCoords;
    frag.color = color;

    gl_Position = projectionMatrix * screenPosition(position);
}
