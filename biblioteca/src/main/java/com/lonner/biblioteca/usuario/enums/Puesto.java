package com.lonner.biblioteca.usuario.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lonner.biblioteca.enums.TipoPasta;
import com.lonner.biblioteca.exceptions.PuestoNotFoundException;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = Puesto.PuestoDeserealizer.class)
@Getter
public enum Puesto {

    VENDEDOR(1,"Vendedor"),GERENTE(2,"Gerente"),CONSULTOR(3,"Consultor");

    private int id;
    private String descripcion;
    @Getter
    private static Map<Integer, Puesto> puestoMap;
    static {
        puestoMap = new HashMap<>();
        for(Puesto puesto : Puesto.values()){
            puestoMap.put(puesto.getId(),puesto);
        }
    }
    private Puesto(int id,String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }

    public static class PuestoDeserealizer extends StdDeserializer<Puesto>{

        public PuestoDeserealizer(){
            super(Puesto.class);
        }

        @Override
        public Puesto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Integer puestoId = Integer.parseInt(jsonParser.readValueAs(JsonNode.class).get("id")
                    .toString().replace("\"", ""));
            return Stream.of(Puesto.values()).findFirst()
                    .orElseThrow(()-> new PuestoNotFoundException("No se ha encontrado un puesto con id: "
                            .concat(puestoId.toString())));
        }
    }
}
