package modelo;

import java.util.Arrays;
import java.util.List;

public enum TipoConsulta {
    MEDICA("Consulta Médica", Arrays.asList(
            "¿Cuál es su nombre?",
            "¿Qué síntomas presenta?",
            "¿Desde cuándo tiene estos síntomas?"
    )),
    PSICOLOGICA("Consulta Psicológica", Arrays.asList(
            "¿Cómo se siente emocionalmente?",
            "¿Ha experimentado estrés recientemente?",
            "¿Tiene problemas para dormir?"
    )),
    NUTRICIONAL("Consulta Nutricional", Arrays.asList(
            "¿Cuál es su peso actual?",
            "¿Sigue alguna dieta especial?",
            "¿Realiza actividad física regularmente?"
    ));

    private final String descripcion;
    private final List<String> preguntas;

    TipoConsulta(String descripcion, List<String> preguntas) {
        this.descripcion = descripcion;
        this.preguntas = preguntas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<String> getPreguntas() {
        return preguntas;
    }
}
