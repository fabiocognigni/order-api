package com.faberoh.order

import java.util.UUID

import cats.Applicative
import cats.effect.Effect
import org.http4s._
import com.faberoh.order.api.v0.server.OrderRoutes
import com.faberoh.order.api.v0.models.Order

final class OrderService[F[_]: Effect](implicit F: Applicative[F]) extends OrderRoutes[F] {

  def getByOrderId(req: Request[F], orderId: UUID): F[GetByOrderIdResponse] = {
    F.pure(GetByOrderIdResponse.HTTP404())
  }

  def post(req: Request[F], body: => DecodeResult[F, Order]): F[PostResponse] = {
    F.pure(PostResponse.HTTP201())
  }

}
