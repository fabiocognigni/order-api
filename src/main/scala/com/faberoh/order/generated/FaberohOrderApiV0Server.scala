/**
 * Generated by API Builder - https://www.apibuilder.io
 * Service version: 0.0.1
 * apibuilder 0.14.3 app.apibuilder.io/faberoh/order-api/0.0.1/http4s_0_18
 */
package com.faberoh.order.api.v0.server

import org.http4s.dsl.{io => _, _}
import cats.effect._
import cats.implicits._
import com.faberoh.order.api.v0.models.json._

private[server] trait Matchers[F[_]] extends Http4sDsl[F] {

  implicit lazy val bigDecimalDateQueryParamDecoder: org.http4s.QueryParamDecoder[BigDecimal] =
    org.http4s.QueryParamDecoder.fromUnsafeCast[BigDecimal](p => BigDecimal(p.value))("BigDecimal")

  implicit lazy val instantQueryParamDecoder: org.http4s.QueryParamDecoder[java.time.Instant] =
    org.http4s.QueryParamDecoder.fromUnsafeCast[java.time.Instant](p => java.time.Instant.parse(p.value))("java.time.Instant")

  implicit lazy val localDateQueryParamDecoder: org.http4s.QueryParamDecoder[java.time.LocalDate] =
    org.http4s.QueryParamDecoder.fromUnsafeCast[java.time.LocalDate](p => java.time.LocalDate.parse(p.value))("java.time.LocalDate")

  implicit lazy val uuidQueryParamDecoder: org.http4s.QueryParamDecoder[java.util.UUID] =
    org.http4s.QueryParamDecoder.fromUnsafeCast[java.util.UUID](p => java.util.UUID.fromString(p.value))("java.util.UUID")


  object ApiVersion {
    val ApiVersionMajor = {
      "X-Apidoc-Version-Major".ci
    }

    def apply(req: org.http4s.Message[F]): Boolean = req.headers.get(ApiVersionMajor) match {
      case Some(v) if v.value == "0" => true
      case _ => false
    }
  }

  object UUIDVal {
    def unapply(s: String): Option[_root_.java.util.UUID] = scala.util.Try(java.util.UUID.fromString(s)).toOption
  }

}

trait OrderRoutes[F[_]] extends Matchers[F] {

  implicit def circeJsonDecoder[A](implicit decoder: _root_.io.circe.Decoder[A], sync: Sync[F]) = org.http4s.circe.jsonOf[F, A]
  implicit def circeJsonEncoder[A](implicit encoder: _root_.io.circe.Encoder[A], sync: Sync[F]) = org.http4s.circe.jsonEncoderOf[F, A]

  sealed trait GetByOrderIdResponse

  object GetByOrderIdResponse {
    case class HTTP200(value: com.faberoh.order.api.v0.models.Order, headers: Seq[org.http4s.Header] = Nil) extends GetByOrderIdResponse
    case class HTTP404(headers: Seq[org.http4s.Header] = Nil) extends GetByOrderIdResponse
  }

  def getByOrderId(
    _req: org.http4s.Request[F],
    orderId: _root_.java.util.UUID
  ): F[GetByOrderIdResponse]

  sealed trait PostResponse

  object PostResponse {
    case class HTTP201(headers: Seq[org.http4s.Header] = Nil) extends PostResponse
    case class HTTP400(headers: Seq[org.http4s.Header] = Nil) extends PostResponse
  }

  def post(
    _req: org.http4s.Request[F],
    body: => org.http4s.DecodeResult[F, com.faberoh.order.api.v0.models.Order]
  ): F[PostResponse]

  def apiVersionMatch(req: org.http4s.Message[F]): Boolean = ApiVersion(req)

  def service()(implicit sync: Sync[F]) = org.http4s.HttpService[F] {
    case _req @ GET -> Root / "orders" / UUIDVal(order_id) if apiVersionMatch(_req) =>
      getByOrderId(_req, order_id).flatMap {
        case GetByOrderIdResponse.HTTP200(value, headers) => Ok(value).map(_.putHeaders(headers: _*))
        case GetByOrderIdResponse.HTTP404(headers) => NotFound().map(_.putHeaders(headers: _*))
      }

    case _req @ POST -> Root / "orders" if apiVersionMatch(_req) =>
      post(_req, _req.attemptAs[com.faberoh.order.api.v0.models.Order]).flatMap {
        case PostResponse.HTTP201(headers) => Created().map(_.putHeaders(headers: _*))
        case PostResponse.HTTP400(headers) => BadRequest().map(_.putHeaders(headers: _*))
      }
  }
}

     