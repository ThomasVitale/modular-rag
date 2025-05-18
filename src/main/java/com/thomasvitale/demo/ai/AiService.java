package com.thomasvitale.demo.ai;

import reactor.core.publisher.Flux;

public interface AiService {

    Flux<String> chat(String input);

}
