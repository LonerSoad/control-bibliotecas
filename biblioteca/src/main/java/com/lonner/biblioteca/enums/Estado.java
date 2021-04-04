package com.lonner.biblioteca.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lonner.biblioteca.exceptions.EstadoNotFoundException;
import lombok.Getter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = Estado.EstadoDeserealizer.class)
@Getter
public enum Estado {

    AGUASCALIENTES(1, "Aguascalientes"),
    BAJA_CALIFORNIA(2, "Baja California"),
    BAJA_CALIFORNIA_SUR(3, "Baja California Sur"),
    CAMPECHE(4, "Campeche"),
    COAHUILA(5, "Coahuila"),
    COLIMA(6, "Colima"),
    CHIAPAS(7, "Chiapas"),
    CHIHUAHUA(8, "Chihuahua"),
    CDMX(9, "CDMX"),
    DURANGO(10, "Durango"),
    GUANAJUATO(11, "Guanajuato"),
    GUERRERO(12, "Guerrero"),
    HIDALGO(13, "Hidalgo"),
    JALISCO(14, "Jalisco"),
    MÉXICO(15, "Estado de México"),
    MICHOACAN(16, "Michoacán"),
    MORELOS(17, "Morelos"),
    NAYARIT(18, "Nayarit"),
    NUEVO_LEON(19, "Nuevo León"),
    OAXACA(20, "Oaxaca"),
    PUEBLA(21, "Puebla"),
    QUERÉTARO(22, "Querétaro"),
    QUINTANA_ROO(23, "Quintana Roo"),
    SAN_LUIS_POTOSI(24, "San Luis Potosí"),
    SINALOA(25, "Sinaloa"),
    SONORA(26, "Sonora"),
    TABASCO(27, "Tabasco"),
    TAMAULIPAS(28, "Tamaulipas"),
    TLAXCALA(29, "Tlaxcala"),
    VERACRUZ(30, "Veracruz"),
    YUCATAN(31, "Yucatán"),
    ZACATECAS(32, "Zacatecas");

    private Integer id;
    private String nombre;

    Estado(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Getter
    private static Map<Integer,Estado> estadoMap;
    static {
        estadoMap = new HashMap<>();
        for(Estado estado : Estado.values()){
            estadoMap.put(estado.getId(),estado);
        }
    }
    public static class EstadoDeserealizer extends StdDeserializer<Estado> {

        public EstadoDeserealizer() {
            super(Estado.class);
        }

        @Override
        public Estado deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
            Integer estadoId = Integer.parseInt(jp.readValueAs(JsonNode.class).get("id").toString().replace("\"", ""));
            return Optional.ofNullable(estadoMap.get(estadoId)).orElseThrow(
                    ()->new EstadoNotFoundException("Con id: ".concat(estadoId.toString())));
        }

    }
}
