package com.lonner.biblioteca.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lonner.biblioteca.exceptions.TipoPastaNotFoundException;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum TipoPasta {

    DURA(1,"Pasta Dura"),BLANDA(2,"Pasta Blanda"),RESTAURADA(3,"Pasta Restaurada");

    private int id;
    private String descripcion;
    @Getter
    private static Map<Integer,TipoPasta> tipoPastaMap;
    static {
        tipoPastaMap = new HashMap<>();
        for(TipoPasta tipoPasta : TipoPasta.values()){
            tipoPastaMap.put(tipoPasta.getId(),tipoPasta);
        }
    }
    private TipoPasta(int id,String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }

    public static class TipoPastaDeserealizer extends StdDeserializer<TipoPasta>{

        public TipoPastaDeserealizer(){
            super(TipoPasta.class);
        }

        @Override
        public TipoPasta deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            Integer tipoPastaId = Integer.parseInt(jsonParser.readValueAs(JsonNode.class).get("id")
                    .toString().replace("\"", ""));
            return Stream.of(TipoPasta.values()).filter(tipoPasta -> tipoPasta.getId()==tipoPastaId.intValue())
                    .findFirst().orElseThrow(()->new TipoPastaNotFoundException("Pasta con id: ".concat(tipoPastaId.toString())));
        }
    }
}
