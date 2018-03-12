/**
 * Generated by API Builder - https://www.apibuilder.io
 * Service version: 0.0.1
 * apibuilder 0.14.3 app.apibuilder.io/faberoh/order-api/0.0.1/http4s_0_18
 */
package com.faberoh.order.api.v0.models {

  /**
   * An address
   * 
   * @param firstName The first name.
   * @param lastName The last name.
   * @param line1 The first line of the address.
   * @param line2 The second line of the address.
   * @param zip The zip code.
   * @param city The city name.
   * @param state The state name.
   * @param countryCode The country ISO code.
   */
  case class Address(
    firstName: String,
    lastName: String,
    line1: String,
    line2: _root_.scala.Option[String] = None,
    zip: String,
    city: String,
    state: String,
    countryCode: String
  )

  /**
   * A credit card.
   * 
   * @param number The credit card number.
   * @param expirationMonth The credit card expiration month.
   * @param expirationYear The credit card expiration year.
   * @param securityCode The credit card security code.
   * @param brand The credit card brand.
   * @param holderName The credit card holder name.
   */
  case class CreditCard(
    number: String,
    expirationMonth: Int,
    expirationYear: Int,
    securityCode: String,
    brand: com.faberoh.order.api.v0.models.CreditCardBrand,
    holderName: String
  )

  /**
   * An item of the order.
   * 
   * @param upc The UPC code for the product.
   * @param quantity The quantity of this item in the order.
   */
  case class Item(
    upc: String,
    quantity: Int
  )

  /**
   * A customer order
   * 
   * @param id The order unique identifier.
   * @param user The customer info.
   * @param status The current status of the order.
   * @param items The items of the order.
   * @param billingAddress The billing address of the order.
   * @param shippingAddress The shipping address of the order.
   * @param creditCard The payment method used.
   * @param summary the order summary amounts
   */
  case class Order(
    id: _root_.java.util.UUID,
    user: com.faberoh.order.api.v0.models.User,
    status: com.faberoh.order.api.v0.models.OrderStatus,
    items: Seq[com.faberoh.order.api.v0.models.Item],
    billingAddress: com.faberoh.order.api.v0.models.Address,
    shippingAddress: com.faberoh.order.api.v0.models.Address,
    creditCard: com.faberoh.order.api.v0.models.CreditCard,
    summary: com.faberoh.order.api.v0.models.OrderSummary
  )

  /**
   * An item of the order.
   * 
   * @param itemsTotal The total cost of the items in the order.
   * @param shippingCharge The charge for shipping the order.
   * @param taxes The taxes amount.
   * @param total The total amount.
   */
  case class OrderSummary(
    itemsTotal: Int,
    shippingCharge: Int,
    taxes: Int,
    total: Int
  )

  /**
   * Customer info.
   * 
   * @param id The unique ID for the user.
   * @param email The customer's email address.
   */
  case class User(
    id: _root_.java.util.UUID,
    email: String
  )

  /**
   * The credit card brands supported.
   */
  sealed trait CreditCardBrand extends _root_.scala.Product with _root_.scala.Serializable

  object CreditCardBrand {

    case object Visa extends CreditCardBrand { override def toString = "visa" }
    case object Mastercard extends CreditCardBrand { override def toString = "mastercard" }
    case object Amex extends CreditCardBrand { override def toString = "amex" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends CreditCardBrand

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all: scala.List[CreditCardBrand] = scala.List(Visa, Mastercard, Amex)

    private[this]
    val byName: Map[String, CreditCardBrand] = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): CreditCardBrand = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[CreditCardBrand] = byName.get(value.toLowerCase)

  }

  /**
   * The processing status of an order.
   */
  sealed trait OrderStatus extends _root_.scala.Product with _root_.scala.Serializable

  object OrderStatus {

    case object Open extends OrderStatus { override def toString = "open" }
    case object Submitted extends OrderStatus { override def toString = "submitted" }
    case object Processed extends OrderStatus { override def toString = "processed" }
    case object Shipped extends OrderStatus { override def toString = "shipped" }
    case object Cancelled extends OrderStatus { override def toString = "cancelled" }
    case object Returned extends OrderStatus { override def toString = "returned" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    case class UNDEFINED(override val toString: String) extends OrderStatus

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all: scala.List[OrderStatus] = scala.List(Open, Submitted, Processed, Shipped, Cancelled, Returned)

    private[this]
    val byName: Map[String, OrderStatus] = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): OrderStatus = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[OrderStatus] = byName.get(value.toLowerCase)

  }

}

package com.faberoh.order.api.v0.models {

  package object json {
    import io.circe.Decoder._
    import io.circe.Encoder._
    import scala.util.Try
    import io.circe.{Json, JsonObject, Encoder, Decoder, DecodingFailure}
    import io.circe.syntax._
    import com.faberoh.order.api.v0.models.json._

    // Make Scala 2.11 Either monadic
    private[v0] implicit def eitherOps[A,B](e: Either[A,B]) = cats.implicits.catsSyntaxEither(e)

    private[v0] implicit val decodeUUID: Decoder[_root_.java.util.UUID] =
      Decoder.decodeString.emapTry(str => Try(_root_.java.util.UUID.fromString(str)))

    private[v0] implicit val encodeUUID: Encoder[_root_.java.util.UUID] =
      Encoder.encodeString.contramap[_root_.java.util.UUID](_.toString)

    private[v0] implicit val decodeInstant: Decoder[_root_.java.time.Instant] =
      Decoder.decodeString.emapTry(str => Try(_root_.java.time.Instant.parse(str)))

    private[v0] implicit val encodeInstant: Encoder[_root_.java.time.Instant] =
      Encoder.encodeString.contramap[_root_.java.time.Instant](_.toString)

    private[v0] implicit val decodeLocalDate: Decoder[_root_.java.time.LocalDate] =
      Decoder.decodeString.emapTry(str => Try(_root_.java.time.LocalDate.parse(str)))

    private[v0] implicit val encodeLocalDate: Encoder[_root_.java.time.LocalDate] =
      Encoder.encodeString.contramap[_root_.java.time.LocalDate](_.toString)

    implicit val jsonDecoderOrderApiCreditCardBrand: Decoder[com.faberoh.order.api.v0.models.CreditCardBrand] =
      Decoder.decodeString.map(com.faberoh.order.api.v0.models.CreditCardBrand(_))

    implicit val jsonEncoderOrderApiCreditCardBrand: Encoder[com.faberoh.order.api.v0.models.CreditCardBrand] =
      Encoder.encodeString.contramap[com.faberoh.order.api.v0.models.CreditCardBrand](_.toString)

    implicit val jsonDecoderOrderApiOrderStatus: Decoder[com.faberoh.order.api.v0.models.OrderStatus] =
      Decoder.decodeString.map(com.faberoh.order.api.v0.models.OrderStatus(_))

    implicit val jsonEncoderOrderApiOrderStatus: Encoder[com.faberoh.order.api.v0.models.OrderStatus] =
      Encoder.encodeString.contramap[com.faberoh.order.api.v0.models.OrderStatus](_.toString)

    implicit def decodeOrderApiAddress: Decoder[Address] = Decoder.instance { c =>
     for {
        firstName <- c.downField("first_name").as[String]
        lastName <- c.downField("last_name").as[String]
        line1 <- c.downField("line1").as[String]
        line2 <- c.downField("line2").as[Option[String]]
        zip <- c.downField("zip").as[String]
        city <- c.downField("city").as[String]
        state <- c.downField("state").as[String]
        countryCode <- c.downField("country_code").as[String]
      } yield {
        Address(
          firstName = firstName,
          lastName = lastName,
          line1 = line1,
          line2 = line2,
          zip = zip,
          city = city,
          state = state,
          countryCode = countryCode
        )
      }
    }

    implicit def encodeOrderApiAddress: Encoder[Address] = Encoder.instance { t =>
      Json.fromFields(Seq(
        Some("first_name" -> t.firstName.asJson),
        Some("last_name" -> t.lastName.asJson),
        Some("line1" -> t.line1.asJson),
        t.line2.map(t => "line2" -> t.asJson),
        Some("zip" -> t.zip.asJson),
        Some("city" -> t.city.asJson),
        Some("state" -> t.state.asJson),
        Some("country_code" -> t.countryCode.asJson)
      ).flatten)
    }

    implicit def decodeOrderApiCreditCard: Decoder[CreditCard] = Decoder.instance { c =>
     for {
        number <- c.downField("number").as[String]
        expirationMonth <- c.downField("expiration_month").as[Int]
        expirationYear <- c.downField("expiration_year").as[Int]
        securityCode <- c.downField("security_code").as[String]
        brand <- c.downField("brand").as[com.faberoh.order.api.v0.models.CreditCardBrand]
        holderName <- c.downField("holder_name").as[String]
      } yield {
        CreditCard(
          number = number,
          expirationMonth = expirationMonth,
          expirationYear = expirationYear,
          securityCode = securityCode,
          brand = brand,
          holderName = holderName
        )
      }
    }

    implicit def encodeOrderApiCreditCard: Encoder[CreditCard] = Encoder.instance { t =>
      Json.fromFields(Seq(
        Some("number" -> t.number.asJson),
        Some("expiration_month" -> t.expirationMonth.asJson),
        Some("expiration_year" -> t.expirationYear.asJson),
        Some("security_code" -> t.securityCode.asJson),
        Some("brand" -> t.brand.asJson),
        Some("holder_name" -> t.holderName.asJson)
      ).flatten)
    }

    implicit def decodeOrderApiItem: Decoder[Item] = Decoder.instance { c =>
     for {
        upc <- c.downField("upc").as[String]
        quantity <- c.downField("quantity").as[Int]
      } yield {
        Item(
          upc = upc,
          quantity = quantity
        )
      }
    }

    implicit def encodeOrderApiItem: Encoder[Item] = Encoder.instance { t =>
      Json.fromFields(Seq(
        Some("upc" -> t.upc.asJson),
        Some("quantity" -> t.quantity.asJson)
      ).flatten)
    }

    implicit def decodeOrderApiOrder: Decoder[Order] = Decoder.instance { c =>
     for {
        id <- c.downField("id").as[_root_.java.util.UUID]
        user <- c.downField("user").as[com.faberoh.order.api.v0.models.User]
        status <- c.downField("status").as[com.faberoh.order.api.v0.models.OrderStatus]
        items <- c.downField("items").as[Seq[com.faberoh.order.api.v0.models.Item]]
        billingAddress <- c.downField("billing_address").as[com.faberoh.order.api.v0.models.Address]
        shippingAddress <- c.downField("shipping_address").as[com.faberoh.order.api.v0.models.Address]
        creditCard <- c.downField("credit_card").as[com.faberoh.order.api.v0.models.CreditCard]
        summary <- c.downField("summary").as[com.faberoh.order.api.v0.models.OrderSummary]
      } yield {
        Order(
          id = id,
          user = user,
          status = status,
          items = items,
          billingAddress = billingAddress,
          shippingAddress = shippingAddress,
          creditCard = creditCard,
          summary = summary
        )
      }
    }

    implicit def encodeOrderApiOrder: Encoder[Order] = Encoder.instance { t =>
      Json.fromFields(Seq(
        Some("id" -> t.id.asJson),
        Some("user" -> t.user.asJson),
        Some("status" -> t.status.asJson),
        Some("items" -> t.items.asJson),
        Some("billing_address" -> t.billingAddress.asJson),
        Some("shipping_address" -> t.shippingAddress.asJson),
        Some("credit_card" -> t.creditCard.asJson),
        Some("summary" -> t.summary.asJson)
      ).flatten)
    }

    implicit def decodeOrderApiOrderSummary: Decoder[OrderSummary] = Decoder.instance { c =>
     for {
        itemsTotal <- c.downField("items_total").as[Int]
        shippingCharge <- c.downField("shipping_charge").as[Int]
        taxes <- c.downField("taxes").as[Int]
        total <- c.downField("total").as[Int]
      } yield {
        OrderSummary(
          itemsTotal = itemsTotal,
          shippingCharge = shippingCharge,
          taxes = taxes,
          total = total
        )
      }
    }

    implicit def encodeOrderApiOrderSummary: Encoder[OrderSummary] = Encoder.instance { t =>
      Json.fromFields(Seq(
        Some("items_total" -> t.itemsTotal.asJson),
        Some("shipping_charge" -> t.shippingCharge.asJson),
        Some("taxes" -> t.taxes.asJson),
        Some("total" -> t.total.asJson)
      ).flatten)
    }

    implicit def decodeOrderApiUser: Decoder[User] = Decoder.instance { c =>
     for {
        id <- c.downField("id").as[_root_.java.util.UUID]
        email <- c.downField("email").as[String]
      } yield {
        User(
          id = id,
          email = email
        )
      }
    }

    implicit def encodeOrderApiUser: Encoder[User] = Encoder.instance { t =>
      Json.fromFields(Seq(
        Some("id" -> t.id.asJson),
        Some("email" -> t.email.asJson)
      ).flatten)
    }
  }
}