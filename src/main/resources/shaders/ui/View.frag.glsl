#version 440

struct Material {
    float dissolveFactor;
    vec3 ambient;
    sampler2D texAmbient;
};

uniform Material material;

in FragParams params;
out vec4 outColor;

void main() {
    vec4 ambientColor = texture(texAmbient, params.texCoords) * material.ambient;
    outColor = vec4(ambientColor.rgb, material.dissolveFactor);
}
