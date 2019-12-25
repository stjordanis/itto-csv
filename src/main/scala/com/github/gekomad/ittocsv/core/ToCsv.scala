//package com.github.gekomad.ittocsv.core
//
//import java.util.UUID
//
//import com.github.gekomad.ittocsv.core.Header._
//
//import com.github.gekomad.ittocsv.core.Types.implicits._
//
//import com.github.gekomad.ittocsv.parser.{IttoCSVFormat, StringToCsvField}
////import shapeless.{::, Generic, HList, HNil, Lazy}
//
//trait CsvStringEncoder[A] {
//  def encode(value: A): String
//}
//
///**
//  * Converts the type A to CSV
//  *
//  * @author Giuseppe Cannella
//  * @since 0.0.1
//  * @see See test code for more information
//  * @see See [[https://github.com/gekomad/itto-csv/blob/master/README.md]] for more information.
//  */
//object ToCsv {
//
//  def createEncoder[A](func: A => String): CsvStringEncoder[A] =
//    new CsvStringEncoder[A] {
//      override def encode(value: A): String = func(value)
//    }
//
//  val csvConverter = StringToCsvField
//
//  given def stringEncoder(given IttoCSVFormat): CsvStringEncoder[String] =
//    createEncoder(t => csvConverter.stringToCsvField(t))
//
//  given def intEncoder(given IttoCSVFormat): CsvStringEncoder[Int] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def longEncoder(given IttoCSVFormat): CsvStringEncoder[Long] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def doubleEncoder(given IttoCSVFormat): CsvStringEncoder[Double] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def booleanEncoder(given IttoCSVFormat): CsvStringEncoder[Boolean] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def byteEncoder(given IttoCSVFormat): CsvStringEncoder[Byte] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def uuidEncoder(given IttoCSVFormat): CsvStringEncoder[UUID] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def shortEncoder(given IttoCSVFormat): CsvStringEncoder[Short] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def floatEncoder(given IttoCSVFormat): CsvStringEncoder[Float] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def charEncoder(given IttoCSVFormat): CsvStringEncoder[Char] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  import java.time.LocalDateTime
//  import java.time.LocalDate
//  import java.time.LocalTime
//  import java.time.OffsetDateTime
//  import java.time.OffsetTime
//  import java.time.ZonedDateTime
//
//  given def localDateEncoder(given IttoCSVFormat): CsvStringEncoder[LocalDate] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def localDateTimeEncoder(given IttoCSVFormat): CsvStringEncoder[LocalDateTime] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def localTimeEncoder(given IttoCSVFormat): CsvStringEncoder[LocalTime] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def offsetDateTimeEncoder(given IttoCSVFormat): CsvStringEncoder[OffsetDateTime] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def offsetTimeEncoder(given IttoCSVFormat): CsvStringEncoder[OffsetTime] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  given def zonedDateTimeEncoder(given IttoCSVFormat): CsvStringEncoder[ZonedDateTime] =
//    createEncoder(t => csvConverter.stringToCsvField(t.toString))
//
//  ///////////////////////////
//
//  given val hnilEncoder: CsvStringEncoder[HNil] =
//    new CsvStringEncoder[HNil] {
//      override def encode(value: HNil): String = ""
//    }
//
////  given def genericEncoder[A, R](given gen: Generic.Aux[A, R], rEncoder: Lazy[CsvStringEncoder[R]]): CsvStringEncoder[A] =
////    createEncoder { value =>
////      rEncoder.value.encode(gen.to(value))
////    }
//
//  private def header[A: FieldNames](given CsvStringEncoder[A], csvFormat: IttoCSVFormat): String =
//    if (csvFormat.printHeader) csvHeader[A] + csvFormat.recordSeparator else ""
//
//  /**
//    * @param a                    is the element to convert
//    * @param printRecordSeparator if true, appends the record separator to end of string
//    * @param enc                  the [[com.github.gekomad.ittocsv.core.CsvStringEncoder]] encoder
//    * @param csvFormat            the [[com.github.gekomad.ittocsv.parser.IttoCSVFormat]] formatter
//    * @return the CSV string encoded
//    * {{{
//    * import com.github.gekomad.ittocsv.core.ToCsv._
//    * given val csvFormat = com.github.gekomad.ittocsv.parser.IttoCSVFormat.default
//    *
//    * case class Bar(a: String, b: Int)
//    * assert(toCsv(Bar("侍", 42)) == "侍,42")
//    * case class Baz(x: String)
//    * case class Foo(a: Int, c: Baz)
//    * case class Xyz(a: String, b: Int, c: Foo)
//    *
//    * assert(toCsv(Xyz("hello", 3, Foo(1, Baz("hi, dude")))) == "hello,3,1,\"hi, dude\"")
//    * }}}
//    *
//    */
//  def toCsv[A](a: A, printRecordSeparator: Boolean = false)(given enc: CsvStringEncoder[A], csvFormat: IttoCSVFormat): String =
//    (if (printRecordSeparator) csvFormat.recordSeparator else "") + enc.encode(a)
//
//  /**
//    * @param a         is the List of elements to convert
//    * @param csvFormat the [[com.github.gekomad.ittocsv.parser.IttoCSVFormat]] formatter
//    * @return the CSV string encoded
//    * {{{
//    * import com.github.gekomad.ittocsv.core.ToCsv._
//    * given val csvFormat = com.github.gekomad.ittocsv.parser.IttoCSVFormat.default
//    * case class Bar(a: String, b: Int)
//    * assert(toCsv(List(Bar("abc", 42), Bar("def", 24))) == "abc,42,def,24")
//    * }}}
//    *
//    */
//  def toCsv[A](a: Seq[A])(given CsvStringEncoder[A], csvFormat: IttoCSVFormat): String =
//    a.map(value => toCsv(value)).mkString(csvFormat.delimeter.toString)
//
//  /**
//    * @param a         is the List of elements to convert
//    * @param csvFormat the [[com.github.gekomad.ittocsv.parser.IttoCSVFormat]] formatter
//    * {{{
//    * import com.github.gekomad.ittocsv.core.ToCsv._
//    * given val csvFormat = com.github.gekomad.ittocsv.parser.IttoCSVFormat.default
//    * case class Bar(a: String, b: Int)
//    * assert(toCsvL(List(Bar("abc", 42), Bar("def", 24))) == "a,b\r\nabc,42\r\ndef,24")
//    * }}}
//    *
//    */
//  def toCsvL[A: FieldNames](a: Seq[A])(given CsvStringEncoder[A], csvFormat: IttoCSVFormat): String =
//    header + a.map(value => toCsv(value)).mkString(csvFormat.recordSeparator)
//
//  given def hlistEncoder[H, T <: HList](given hEncoder: CsvStringEncoder[H], tEncoder: CsvStringEncoder[T], csvFormat: IttoCSVFormat): CsvStringEncoder[H :: T] = createEncoder {
//    case h :: HNil        => hEncoder.encode(h)
//    case h :: Nil :: HNil => hEncoder.encode(h)
//    case h :: t =>
//      hEncoder.encode(h) ++ csvFormat.delimeter.toString + tEncoder.encode(t)
//  }
//
//  //import shapeless.{:+:, CNil, Coproduct, Inl, Inr}
//
//  given val cnilEncoder: CsvStringEncoder[CNil] = createEncoder(_ => throw new Exception("Inconceivable!"))
//
//  given def coproductEncoder[H, T <: Coproduct](
//    given
//    hEncoder: Lazy[CsvStringEncoder[H]],
//    tEncoder: CsvStringEncoder[T]
//  ): CsvStringEncoder[H :+: T] = createEncoder {
//    case Inl(h) => hEncoder.value.encode(h)
//    case Inr(t) => tEncoder.encode(t)
//  }
//}
