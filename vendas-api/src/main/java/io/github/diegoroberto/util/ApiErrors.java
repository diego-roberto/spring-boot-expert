package io.github.diegoroberto.util;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrors {

    private final List<String> errors;

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String msg){
        this.errors = Collections.singletonList(msg);
    }
}
