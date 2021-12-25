package vn.quynv.springframework.func.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CustomerFucFactory {

    @Autowired
    ApplicationContext applicationContext;

    public  Function generateFunction(String pipline) {
        List<String> functionList = Stream.of(pipline.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        Function func = Function.identity();
        for(var strFuc : functionList) {
            Function childFuc = applicationContext.getBean(strFuc,Function.class);
            func  = func.andThen(childFuc);
        }
        return func;
    }
}
