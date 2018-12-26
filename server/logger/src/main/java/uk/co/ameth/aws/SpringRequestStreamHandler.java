package uk.co.ameth.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * credit for merging spring and aws to https://github.com/ccampo133/spring-aws-lambda
 */
public abstract class SpringRequestStreamHandler implements RequestStreamHandler, ApplicationContextProvider {

    private final RequestStreamHandler handler;

    public SpringRequestStreamHandler() {
        this.handler = getApplicationContext().getBean(RequestStreamHandler.class);
    }

    @Override
    public void handleRequest(final InputStream input, final OutputStream output, final Context context)
        throws IOException {
        handler.handleRequest(input, output, context);
    }

}
