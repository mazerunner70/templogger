package uk.co.ameth.aws.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.ameth.aws.example.model.Request;
import uk.co.ameth.aws.example.model.Response;
import uk.co.ameth.aws.example.service.ExampleServiceA;
import uk.co.ameth.aws.example.service.ExampleServiceB;
import uk.co.ameth.logger.Controller;

@Component
public class ExampleRequestHandler implements RequestHandler<Request, Response> {

    private final ExampleServiceA exampleServiceA;
    private final ExampleServiceB exampleServiceB;

    @Autowired
    public ExampleRequestHandler(final ExampleServiceA exampleServiceA, final ExampleServiceB exampleServiceB) {
        this.exampleServiceA = exampleServiceA;
        this.exampleServiceB = exampleServiceB;
    }

    @Autowired
    private Controller controller;


    @Override
    public Response handleRequest(Request request, Context context) {
        final String responseMessage = "Request Message:"+request.toString()
                + ", Service A message: "+exampleServiceA.getMessage()
                + ", Service B message: "+exampleServiceB.getMessage();
        try {
            controller.run("gg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Response response = new Response();
        response.setMessage(responseMessage);
        response.setStatus(Response.Status.OK);
        return response;
    }
}
