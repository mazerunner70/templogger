package uk.co.ameth.aws;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * credit for merging spring and aws to https://github.com/ccampo133/spring-aws-lambda
 */
public abstract class SpringVoidRequestHandler<I> extends SpringRequestHandler<I, Void> {

    @Override
    public Void handleRequest(final I input, final Context context) {
        super.handleRequest(input, context);
        return null;
    }
}
