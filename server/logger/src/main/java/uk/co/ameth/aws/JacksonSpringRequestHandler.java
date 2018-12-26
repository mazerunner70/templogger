package uk.co.ameth.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * credit for merging spring and aws to https://github.com/ccampo133/spring-aws-lambda
 */
public abstract class JacksonSpringRequestHandler<I, O> extends SpringRequestStreamHandler {

    private final Class<I> inputClass;

    private final RequestHandler handler;

    protected JacksonSpringRequestHandler(@NotNull final Class<I> inputClass) {
        this.inputClass = inputClass;
        this.handler = getApplicationContext().getBean(RequestHandler.class);
    }

    protected abstract ObjectMapper getObjectMapper();

    @Override
    public void handleRequest(@NotNull final InputStream input, @NotNull final OutputStream output,
                              @NotNull final Context context) throws IOException {
        final I inObject = getObjectMapper().readValue(input, inputClass);
        @SuppressWarnings("unchecked")
        final O outObject = (O) handler.handleRequest(inObject, context);
        getObjectMapper().writeValue(output, outObject);
    }

}
