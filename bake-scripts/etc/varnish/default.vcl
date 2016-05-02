backend default {
  .host = "127.0.0.1";
  .port = "8080";
}

sub vcl_deliver {
        if (obj.hits > 0) {
                set resp.http.X-Varnish-Cache = "HIT";
        } else {
                set resp.http.X-Varnish-Cache = "MISS";
        }
}

sub vcl_fetch {
    if (beresp.status == 404) {
        set beresp.ttl = 5s;
    }
    if (beresp.status == 400) {
        set beresp.ttl = 300s;
    }
    if (beresp.status > 499) {
        set beresp.ttl = 0s;
    }
}