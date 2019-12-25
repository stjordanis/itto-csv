package com.github.gekomad.ittocsv.core

import java.util.UUID
import com.github.gekomad.ittocsv.util.TryCatch.tryCatch

/**
  * Converts a string to type
  *
  * @author Giuseppe Cannella
  * @since 0.0.1
  * @see See test code for more information
  * @see See [[https://github.com/gekomad/itto-csv/blob/master/README.md]] for more information.
  */
object Conversions {

  trait ConvertTo[A] {
    def to(a: String): Either[ParseFailure, A]
  }

  given ConvertTo[Int] = (a: String) => tryCatch(a.toInt)(s"$a is not Int")

  given ConvertTo[Double] = (a: String) => tryCatch(a.toDouble)(s"$a is not Double")

  given ConvertTo[Byte] = (a: String) => tryCatch(a.toByte)(s"$a is not Byte")

  given ConvertTo[Short] = (a: String) => tryCatch(a.toShort)(s"$a is not Short")

  given ConvertTo[Float] = (a: String) => tryCatch(a.toFloat)(s"$a is not Float")

  given ConvertTo[Long] = (a: String) => tryCatch(a.toLong)(s"$a is not Long")

  given ConvertTo[Char] = (a: String) => tryCatch(if (a.length == 1) a(0) else throw new Exception)(s"$a is not Char")

  given ConvertTo[Boolean] = (a: String) => tryCatch(a.toBoolean)(s"$a is not Boolean")

  given ConvertTo[UUID] = (a: String) => tryCatch(UUID.fromString(a))(s"$a is not UUID")

  import java.time._
  import java.time.format.DateTimeFormatter._

  given (String => Either[ParseFailure, LocalDateTime]) = { s =>
    tryCatch(LocalDateTime.parse(s, ISO_LOCAL_DATE_TIME))(s"Not a LocalDataTime $s")
  }

  given [A](given f: String => Either[ParseFailure, A]): (String => Either[ParseFailure, Option[A]]) = {
//    import cats.implicits._
    s =>
      if (s == "") Right(None)
      else {
        val c: Either[ParseFailure, A] = f(s)
        c.map(r => Some(r))
      }
  }

  given ConvertTo[LocalDate] =
    (a: String) => tryCatch(LocalDate.parse(a, ISO_LOCAL_DATE))(s"Not a LocalDate $a")

  given ConvertTo[LocalTime] =
    (a: String) => tryCatch(LocalTime.parse(a, ISO_LOCAL_TIME))(s"Not a LocalTime $a")

  given ConvertTo[OffsetDateTime] =
    (s: String) => tryCatch(OffsetDateTime.parse(s, ISO_OFFSET_DATE_TIME))(s"Not a OffsetDateTime $s")

  given ConvertTo[OffsetTime] =
    (s: String) => tryCatch(OffsetTime.parse(s, ISO_OFFSET_TIME))(s"Not a OffsetTime $s")

  given ConvertTo[ZonedDateTime] =
    (s: String) => tryCatch(ZonedDateTime.parse(s, ISO_ZONED_DATE_TIME))(s"Not a ZonedDateTime $s")

  def convert[A](s: String)(given f: ConvertTo[A]): Either[ParseFailure, A] =
    f.to(s)
}
