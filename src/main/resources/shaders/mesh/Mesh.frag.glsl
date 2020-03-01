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

struct Light {
    vec4 color;
    vec3 direction;
};

uniform Material material;
uniform Light[8] lights;

in FragParams {
    vec2 texCoords;
    vec3 normal;
    vec3 look;
} fragParams;

out vec4 outColor;

vec4 lambertLighting(in vec4 ambient, in vec4 diffuse, in Light light, in vec3 normal) {
    vec3 encodedNormal = normal * 0.5 + 0.5;
    float ndotl = max(dot(encodedNormal, -light.direction), 0.0);
    return diffuse * ambient * ndotl * light.color;
}

void phongLighting(in Material mat, in Light light, in vec3 normal, in vec3 look) {
    vec3 reflect = normalize(reflect(-light.direction, normal));
    mat.specular = vec4(mat.specular.rgb * pow(max(0.0, dot(reflect, -look)), mat.specular.w), 1.0);
}

void main() {
    vec4 ambientColor = vec4(1.0);

    for (int i = 0; i < lights.length; i++)
    ambientColor = lambertLighting(ambientColor, material.diffuse, lights[i], fragParams.normal);

    outColor = ambientColor;
}
