package serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import view.frameClosing.InternalFrameClosing;

import java.io.IOException;

public class InternalFrameSerializer extends StdSerializer<InternalFrameClosing> {
    public InternalFrameSerializer() {
        super(InternalFrameClosing.class);
    }

    @Override
    public void serialize(InternalFrameClosing internalFrameClosing, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField(ConfigFieldName.POSITION_X, internalFrameClosing.getX());
        jsonGenerator.writeNumberField(ConfigFieldName.POSITION_Y, internalFrameClosing.getY());
        jsonGenerator.writeNumberField(ConfigFieldName.WIDTH, internalFrameClosing.getWidth());
        jsonGenerator.writeNumberField(ConfigFieldName.HEIGHT, internalFrameClosing.getHeight());
        jsonGenerator.writeEndObject();
    }
}
