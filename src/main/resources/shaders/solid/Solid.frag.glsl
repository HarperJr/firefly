#version 440

struct Material {
    float dissolveFactor;
    float specularFactor;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
    vec3 emissive;
    sampler2D texAmbient;
    sampler2D texDiffuse;
    sampler2D texSpecular;
};

uniform Material material;

in FragParams params;
out vec4 outColor;

void main() {
    vec4 ambient = texture(material.texAmbient, params.texCoords) * material.ambient;
}
