/**
 * Generated by API Builder - https://www.apibuilder.io
 * Service version: 0.0.1
 * apibuilder 0.14.3 app.apibuilder.io/faberoh/order-api/0.0.1/http4s_0_18
 */
package com.faberoh.order.api.v0 {
import cats.effect._
import cats.implicits._

  object Constants {

    val BaseUrl = "http://localhost:8080"
    val Namespace = "com.faberoh.order.api.v0"
    val UserAgent = "apibuilder 0.14.3 app.apibuilder.io/faberoh/order-api/0.0.1/http4s_0_18"
    val Version = "0.0.1"
    val VersionMajor = 0

  }

  class Client[F[_]: Sync](
    val baseUrl: org.http4s.Uri = org.http4s.Uri.unsafeFromString("http://localhost:8080"),
    auth: scala.Option[com.faberoh.order.api.v0.Authorization] = None,
    defaultHeaders: Seq[(String, String)] = Nil,
    httpClient: org.http4s.client.Client[F]
  ) extends interfaces.Client[F] {
    import org.http4s.Response
    import com.faberoh.order.api.v0.models.json._


    def orders: Orders[F] = Orders

    object Orders extends Orders[F] {
      override def getByOrderId(
        orderId: _root_.java.util.UUID,
        requestHeaders: Seq[(String, String)] = Nil
      ): F[com.faberoh.order.api.v0.models.Order] = {
        val urlPath = Seq("orders", orderId.toString)

        _executeRequest[Unit, com.faberoh.order.api.v0.models.Order]("GET", path = urlPath, requestHeaders = requestHeaders) {
          case r if r.status.code == 200 => _root_.com.faberoh.order.api.v0.Client.parseJson[F, com.faberoh.order.api.v0.models.Order]("com.faberoh.order.api.v0.models.Order", r)
          case r if r.status.code == 404 => Sync[F].raiseError(new errors.UnitResponse(r.status.code))
          case r => Sync[F].raiseError(new com.faberoh.order.api.v0.errors.FailedRequest(r.status.code, s"Unsupported response code[${r.status.code}]. Expected: 200, 404"))
        }
      }

      override def post(
        order: com.faberoh.order.api.v0.models.Order,
        requestHeaders: Seq[(String, String)] = Nil
      ): F[Unit] = {
        val urlPath = Seq("orders")

        val payload = order

        _executeRequest[com.faberoh.order.api.v0.models.Order, Unit]("POST", path = urlPath, body = Some(payload), requestHeaders = requestHeaders) {
          case r if r.status.code == 201 => Sync[F].pure(())
          case r if r.status.code == 400 => Sync[F].raiseError(new errors.UnitResponse(r.status.code))
          case r => Sync[F].raiseError(new com.faberoh.order.api.v0.errors.FailedRequest(r.status.code, s"Unsupported response code[${r.status.code}]. Expected: 201, 400"))
        }
      }
    }

    private lazy val defaultApiHeaders = Seq(
      ("User-Agent", Constants.UserAgent),
      ("X-Apidoc-Version", Constants.Version),
      ("X-Apidoc-Version-Major", Constants.VersionMajor.toString)
    )

    def apiHeaders: Seq[(String, String)] = defaultApiHeaders

    def modifyRequest(request: F[org.http4s.Request[F]]): F[org.http4s.Request[F]] = request

    implicit def circeJsonEncoder[F[_]: Sync, A](implicit encoder: io.circe.Encoder[A]) = org.http4s.circe.jsonEncoderOf[F, A]

    def _executeRequest[T, U](
      method: String,
      path: Seq[String],
      queryParameters: Seq[(String, String)] = Nil,
      requestHeaders: Seq[(String, String)] = Nil,
      body: Option[T] = None
    )(handler: org.http4s.Response[F] => F[U]
    )(implicit encoder: io.circe.Encoder[T]): F[U] = {
      import org.http4s.QueryParamEncoder._

      val m = org.http4s.Method.fromString(method) match {
        case Right(m) => m
        case Left(e) => sys.error(e.toString)
      }

      val headers = org.http4s.Headers((
        apiHeaders ++
        defaultHeaders ++
        requestHeaders
      ).toList.map { case (k, v) => org.http4s.Header(k, v) })

      val queryMap = queryParameters.groupBy(_._1).map { case (k, v) => k -> v.map(_._2) }
      val uri = path.foldLeft(baseUrl){ case (uri, segment) => uri / segment }.setQueryParams(queryMap)

      val request = org.http4s.Request[F](method = m,
                                       uri = uri,
                                       headers = headers)

      val authReq = auth.fold(request) {
        case Authorization.Basic(username, passwordOpt) => {
          val userpass = s"$username:${passwordOpt.getOrElse("")}"
          val token = java.util.Base64.getEncoder.encodeToString(userpass.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1))
          request.putHeaders(org.http4s.Header("Authorization", s"Basic $token"))
        }
        case a => sys.error("Invalid authorization scheme[" + a.getClass + "]")
      }

      val authBody = body.fold(Sync[F].pure(authReq))(authReq.withBody)

      httpClient.fetch(modifyRequest(authBody))(handler)
    }

    object errors {

    case class UnitResponse(status: Int) extends Exception(s"HTTP $status")
    }
  }

  object Client {
    import cats.effect._


    implicit def circeJsonDecoder[F[_]: Sync, A](implicit decoder: io.circe.Decoder[A]) = org.http4s.circe.jsonOf[F, A]


    def parseJson[F[_]: Sync, T](
      className: String,
      r: org.http4s.Response[F]
    )(implicit decoder: io.circe.Decoder[T]): F[T] = r.attemptAs[T].value.flatMap {
      case Right(value) => Sync[F].pure(value)
      case Left(error) => Sync[F].raiseError(new com.faberoh.order.api.v0.errors.FailedRequest(r.status.code, s"Invalid json for class[" + className + "]", None, error))
    }
  }

  sealed trait Authorization extends _root_.scala.Product with _root_.scala.Serializable
  object Authorization {
    case class Basic(username: String, password: Option[String] = None) extends Authorization
  }

  package interfaces {

    trait Client[F[_]] {
      def baseUrl: org.http4s.Uri
      def orders: com.faberoh.order.api.v0.Orders[F]
    }

  }

  trait Orders[F[_]] {
    /**
     * Get an order by ID.
     * 
     * @param orderId The id of the order to retrieve.
     */
    def getByOrderId(
      orderId: _root_.java.util.UUID,
      requestHeaders: Seq[(String, String)] = Nil
    ): F[com.faberoh.order.api.v0.models.Order]

    /**
     * Create (submit) an order.
     */
    def post(
      order: com.faberoh.order.api.v0.models.Order,
      requestHeaders: Seq[(String, String)] = Nil
    ): F[Unit]
  }

  package errors {

    case class FailedRequest(responseCode: Int, message: String, requestUri: Option[_root_.java.net.URI] = None, parent: Exception = null) extends _root_.java.lang.Exception(s"HTTP $responseCode: $message", parent)

  }
}