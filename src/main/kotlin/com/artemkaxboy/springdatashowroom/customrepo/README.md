# Customized Repository

This package shows how to create and use repository with custom functions.

1.  [Spring-style](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.custom-implementations) implementation can be seen in `springstyle/repository`
2.  Extensions-style implementation can be seen in `extensions/repository`. All additional functions store in the same repository file. It is more isolated and easier to maintain. Be careful, you cannot use autowired variables in extension functions.
3.  Delegate-style implementation can be seen in `delegate/repository`. All additional functions store in a separate service. Any abstract repository function can be used through delegate without necessity to override it explicitly. Service can use any available been.
