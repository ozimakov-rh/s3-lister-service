package com.redhat.demo;

import io.smallrye.mutiny.Multi;

public interface ListerService {

    Multi<String> listFiles();

}
