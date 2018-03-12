package com.faberoh.order

import java.util.concurrent.Executors

import cats.effect.{Effect, IO}
import com.gilt.gfc.concurrent.JavaConverters._
import fs2._
import org.http4s.server.blaze.BlazeBuilder
import org.log4s._

import scala.concurrent.ExecutionContext

sealed abstract class BlazeServer[F[_]: Effect] extends StreamApp[F] {
  val threadPoolSize = 10
  implicit val ec = ExecutionContext.fromExecutorService(Executors.newScheduledThreadPool(threadPoolSize).asScala)

  override def stream(args: List[String], requestShutdown: F[Unit]): Stream[F, StreamApp.ExitCode] = {
    BlazeBuilder[F]
      .bindHttp(8080, "localhost")
      .mountService(new OrderService[F].service)
      .serve
  }
}

object Server extends BlazeServer[IO]
