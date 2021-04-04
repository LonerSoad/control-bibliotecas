package com.lonner.biblioteca.usuario.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lonner.biblioteca.enums.TipoPasta;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = Accion.AccionDeserealizer.class)
@Getter
public enum Accion {

    AGREGAR(1,"Agregar"),CONSULTAR(2,"Consultar"),
    ELIMINAR(3,"Eliminar"),VENDER(4,"Vender");

    private int id;
    private String descripcion;
    @Getter
    private static Map<Integer, Accion> accionMap;
    static {
        accionMap = new HashMap<>();
        for(Accion accion : Accion.values()){
            accionMap.put(accion.getId(),accion);
        }
    }
    private Accion(int id,String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }

    public static class AccionDeserealizer extends StdDeserializer<Accion>{

        public AccionDeserealizer(){
            super(Accion.class);
        }

        @Override
        public Accion deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Integer accionId = Integer.parseInt(jsonParser.readValueAs(JsonNode.class).get("id")
                    .toString().replace("\"", ""));
            return Stream.of(Accion.values()).filter((accion)-> accion.getId() == accionId.intValue())
                    .findFirst().orElseThrow(()->new InternalError("No existe una accion con clave".concat(accionId.toString())));
        }
    }
}
