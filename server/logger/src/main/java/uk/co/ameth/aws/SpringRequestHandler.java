package uk.co.ameth.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * credit for merging spring and aws to https://github.com/ccampo133/spring-aws-lambda
 */
public abstract class SpringRequestHandler<I, O> implements RequestHandler<I, O>, ApplicationContextProvider {

    private final RequestHandler handler;

    public SpringRequestHandler() {
        handler = getApplicationContext().getBean(RequestHandler.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public O handleRequest(final I input, final Context context) {
        return (O) handler.handleRequest(input, context);
    }
}
