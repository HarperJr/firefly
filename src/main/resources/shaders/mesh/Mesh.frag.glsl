#version 440

struct Material {
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    vec4 emissive;
    sampler2D texAmbient;
    sampler2D texDiffuse;
    sampler2D texSpecular;
};

uniform Material material;

in FragParams {
    vec2 texCoords;
    vec3 normal;
    vec3 look;
} fragParams;

out vec4 outColor;

void main() {
    vec4 ambientColor = texture(material.texAmbient, fragParams.texCoords) * material.ambient;
    outColor = ambientColor;
}
