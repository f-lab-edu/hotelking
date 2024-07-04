package com.hotelking.domain.price;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CustomPriceDeserializer extends JsonDeserializer<List<HashMap<String, CustomPrice>>> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<HashMap<String, CustomPrice>> deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        List<HashMap<String, CustomPrice>> customPrices = new ArrayList<>();

        Iterator<Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String dateStr = field.getKey();
            validateDateFormat(dateStr);

            CustomPrice customPrice = p.getCodec().treeToValue(field.getValue(), CustomPrice.class);
            HashMap<String, CustomPrice> map = new HashMap<>();
            map.put(dateStr, customPrice);
            customPrices.add(map);
        }

        return customPrices;
    }

    private void validateDateFormat(String dateStr) {
        try {
            DATE_FORMAT.setLenient(false);
            DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
    }
}