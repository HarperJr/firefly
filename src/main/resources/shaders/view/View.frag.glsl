#version 440

uniform mat4 modelViewMatrix;
uniform mat4 projectionMatrix;

struct Material {
    float dissolveFactor;
    vec3 ambient;
    sampler2D texAmbient;
};

uniform Material material;

in FragParams {
    vec2 texCoords;
    vec4 color;
} fragParams;

out vec4 outColor;

void main() {
    vec4 ambientColor = texture(material.texAmbient, fragParams.texCoords) * vec4(material.ambient, 1.0);
    outColor = vec4(ambientColor.rgb, material.dissolveFactor);
}
