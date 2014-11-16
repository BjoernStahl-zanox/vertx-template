package com.zanox.external.template.vertx.verticles;

import org.vertx.scala.core.http.HttpServerRequest
import org.vertx.scala.platform.Verticle


class ScalaVerticle extends Verticle {
  override def start() {
    vertx.createHttpServer.requestHandler { req: HttpServerRequest =>
      req.response.end("This is a Verticle class written in Scala")
    }.listen(9090)
  }
}