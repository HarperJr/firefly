#version 440


struct FragParams {
    vec2 texCoords;
    vec3 normal;
    vec3 look;
};

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoords;
layout(location = 2) in vec3 normal;

out FragParams frag;

void main() {
    vec4 worldPosition = modelViewMatrix * vec4(position, 1.0);

    frag.texCoords = texCoords;
    frag.normal = normal;
    frag.look = normalize(worldPosition - inverse(modelViewMatrix[3])).xyz;

    gl_Position = projectionMatrix * worldPosition;
}
