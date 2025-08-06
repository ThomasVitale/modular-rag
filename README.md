# Modular RAG

Samples showing architectural patterns for Modular RAG using Spring AI and Ollama.

## Pre-requisites

* Java 24
* Podman/Docker

### Ollama

The application consumes models from an [Ollama](https://ollama.ai) inference server. You can either run Ollama locally on your laptop, or rely on the [Arconia Dev Services](https://arconia.io/docs/arconia/latest/dev-services/) to spin up an Ollama service automatically. If you choose the first option, make sure you have Ollama installed and running on your laptop. Either way, Spring AI will take care of pulling the needed Ollama models when the application starts, if they are not available yet on your machine.

### Tavily (Optional)

The application consumes the [Tavily Search API](https://tavily.com).

#### Create an account

Visit [app.tavily.com](https://app.tavily.com/home) and sign up for a new account. You can choose the "Researcher" plan to get started for free.

#### Configure API Key

In the Tavily AI console, navigate to _Overview_. Then, in the _API Keys_ section, generate a new API key. Copy and securely store your API key on your machine as an environment variable. The application will use it to access the Tavily Search API.

```shell
export TAVILY_SEARCH_API_KEY=<YOUR-API-KEY>
```

## Running the application

If you're using the native Ollama application, run the application as follows:

```shell
./gradlew bootRun
```

Alternatively, you can use the [Arconia CLI](https://arconia.io/docs/arconia-cli/latest/):

```shell
arconia dev
```

Under the hood, the Arconia framework will automatically spin up a [PostgreSQL](https://arconia.io/docs/arconia/latest/dev-services/postgresql/) database and a [Grafana LGTM](https://arconia.io/docs/arconia/latest/dev-services/lgtm/) observability platform using Testcontainers (see [Arconia Dev Services](https://arconia.io/docs/arconia/latest/dev-services/) for more information).

If instead you want to rely on the [Ollama Dev Service](https://arconia.io/docs/arconia/latest/dev-services/ollama/) via Testcontainers, run the application as follows.

```shell
./gradlew bootRun -Darconia.dev.services.ollama.enabled=true
```

Either way, the application will be accessible at http://localhost:8080.

## Accessing Grafana

The application logs will show you the URL where you can access the Grafana observability platform.

```logs
...o.t.grafana.LgtmStackContainer: Access to the Grafana dashboard: http://localhost:38125
```

By default, logs, metrics, and traces are exported via OTLP using the HTTP/Protobuf format.

In Grafana, you can query the telemetry from the "Drilldown" and "Explore" sections.
