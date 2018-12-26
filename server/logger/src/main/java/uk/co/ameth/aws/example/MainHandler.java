package uk.co.ameth.aws.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.co.ameth.aws.SpringRequestHandler;
import uk.co.ameth.aws.example.model.Request;
import uk.co.ameth.aws.example.model.Response;

public class MainHandler extends SpringRequestHandler<Request, Response> {

    private static final ApplicationContext APPLICATION_CONTEXT =
            new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

    @Override
    public ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

}
