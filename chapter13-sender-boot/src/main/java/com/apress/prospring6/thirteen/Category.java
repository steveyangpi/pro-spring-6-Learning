package com.apress.prospring6.thirteen;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;

@JsonSerialize(using = Category.CategorySerializer.class)
@JsonDeserialize(using = Category.CategoryDeserializer.class)
@Getter
@RequiredArgsConstructor
public enum Category {
    PERSONAL("Personal"),
    FORMAL("Formal"),
    MISC("Miscellaneous");

    private final String name;

    public static Category eventof(final String value){
        var result = Arrays.stream(Category.values()).filter(m -> m.getName().equalsIgnoreCase(value)).findAny();
        return result.orElse(null);
    }

    public static final class CategorySerializer extends JsonSerializer<Category>{
        @Override
        public void serialize(final Category enumValue, final JsonGenerator gen,final  SerializerProvider serializer) throws IOException {
            gen.writeString(enumValue.getName());
        }
    }

    public final static class CategoryDeserializer extends JsonDeserializer<Category>{
        @Override
        public Category deserialize(final JsonParser jsonParser,final DeserializationContext context) throws IOException, JsonProcessingException {
            final String jsonValue = jsonParser.getText();
            return Category.eventof(jsonValue);
        }
    }
}
