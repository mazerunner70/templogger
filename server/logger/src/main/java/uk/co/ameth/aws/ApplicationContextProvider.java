package uk.co.ameth.aws;

import org.springframework.context.ApplicationContext;

/**
 * credit for merging spring and aws to https://github.com/ccampo133/spring-aws-lambda
 */
public interface ApplicationContextProvider {
    ApplicationContext getApplicationContext();
}
