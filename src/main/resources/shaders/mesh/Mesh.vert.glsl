#version 440

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;
layout(location = 2) in vec3 normal;

out FragParams {
    vec2 texCoords;
    vec3 normal;
    vec3 look;
} fragParams;

vec4 worldPosition(in vec3 position) {
    return viewMatrix * modelMatrix * vec4(position, 1.0);
}

void main() {
    vec4 worldPosition = worldPosition(position);

    fragParams.texCoords = texCoord;
    fragParams.normal = normal;
    fragParams.look = normalize(worldPosition - modelMatrix[3]).xyz;

    gl_Position = projectionMatrix * worldPosition;
}
