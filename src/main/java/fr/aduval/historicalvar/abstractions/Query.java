package fr.aduval.historicalvar.abstractions;

/**
 * A simple query definition that separates concerns between readings and writings
 *
 * @param <Result> Result type of the query
 * @param <Request> Request especially defined for this query, reduces mistyping
 *                human errors drastically
 */
@FunctionalInterface
public interface Query<Result, Request extends QueryRequest> {
    Result execute(Request request);
}
