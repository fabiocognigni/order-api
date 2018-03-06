package com.faberoh.order

import java.util.UUID

import cats.effect.IO
import com.faberoh.order.api.v0.mock.{Factories => MockOrderFactories}
import org.http4s.Uri
import org.http4s.client.blaze.Http1Client
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpec}

class OrderServiceSpec extends WordSpec with MustMatchers with BeforeAndAfterAll {

  val client = new com.faberoh.order.api.v0.Client[IO](
                      baseUrl     = Uri.unsafeFromString("http://localhost:8080"),
                      httpClient  = Http1Client[IO]().unsafeRunSync)

  "OrderService" when {
    "receives a GET order request for an invalid order ID" must {
      "return 404" in {
        val getById = client.orders.getByOrderId(UUID.randomUUID())

        getById.attempt.unsafeRunSync() match {
          case Left(e: client.errors.UnitResponse)  => e.status mustBe (404)
          case _                                    => fail()
        }
      }
    }

    "receives a POST order request with a valid payload" must {
      "return 201" in {
        val createOrder = client.orders.post(MockOrderFactories.makeOrder())

        createOrder.unsafeRunSync() mustBe ((): Unit)
      }
    }
  }

}
