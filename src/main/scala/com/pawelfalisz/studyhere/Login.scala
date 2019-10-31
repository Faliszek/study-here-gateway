package com.pawelfalisz.studyhere

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._

trait Login[F[_]] {
  def token(t: Login.Token): F[Login.Token]
}

object Login {
  implicit def apply[F[_]](implicit ev: Login[F]): Login[F] = ev

  final case class Token(token: String) extends AnyVal






  object Response {
    implicit  val responseEncoder: Encoder[Token] = new Encoder[Token] {
      final def apply(a: Token): Json = Json.obj(
        ("token", Json.fromString(a.token))
      )
    }

    implicit def responseEntityEncoder[F[_]: Applicative]: EntityEncoder[F, Token] =
      jsonEncoderOf[F, Token]
  }

  def impl[F[_]: Applicative]: Login[F] = new Login[F] {
    def token(t: Login.Token): F[Login.Token] = Token("Some token").pure[F]
  }
}
