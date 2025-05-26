package com.thomasvitale.demo.ai;

import org.jspecify.annotations.Nullable;
import reactor.core.publisher.Flux;

public interface AiService {

    @Nullable
    String chat(String input);

    default Flux<String> stream(String input) {
        return Flux.empty();
    }

}
