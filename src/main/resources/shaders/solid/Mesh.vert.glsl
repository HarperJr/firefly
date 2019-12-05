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

vec4 worldPosition(in vec3 position) {
    return modelViewMatrix * vec4(position, 1.0);
}

void main() {
    vec4 worldPosition = worldPosition(position);

    frag.texCoords = texCoords;
    frag.normal = normal;
    frag.look = normalize(worldPosition - inverse(modelViewMatrix[3])).xyz;

    gl_Position = projectionMatrix * worldPosition;
}
