package model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import view.frameClosing.InternalFrameClosing;

import javax.swing.*;
import java.io.IOException;

public class InternalFrameDeserializer extends StdDeserializer<InternalFrameClosing> {
    public InternalFrameDeserializer(){
        super(InternalFrameClosing.class);
    }

    @Override
    public InternalFrameClosing deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode xNode = node.get(ConfigFieldName.POSITION_X);
        JsonNode yNode = node.get(ConfigFieldName.POSITION_Y);
        JsonNode widthNode = node.get(ConfigFieldName.WIDTH);
        JsonNode heightNode = node.get(ConfigFieldName.HEIGHT);
        if (xNode == null || yNode == null || widthNode == null || heightNode == null) {
            throw new IOException();
        } else {
            var frame = new InternalFrameClosing("Title", true, false, false, false);

            frame.setSize(widthNode.intValue(), heightNode.intValue());
            frame.setLocation(xNode.intValue(), yNode.intValue());

            return frame;
        }
    }
}
