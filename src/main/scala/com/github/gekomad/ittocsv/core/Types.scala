package com.github.gekomad.ittocsv.core

object Types {

  trait Validate[A] {
    def validate(value: String): Either[ParseFailure, A]
  }

  type Cons[A] = String => A

  case class Validator[A](regex: String, typeName: String)(implicit apply: Cons[A]) extends Validate[A] {
    implicit val validator: com.github.gekomad.regexcollection.Collection.Validator[A] = com.github.gekomad.regexcollection.Collection.Validator[A](regex)
    def validate(value: String): Either[ParseFailure, A] =
      com.github.gekomad.regexcollection.Validate
        .validate[A](value)
        .map(_ => Right(apply(value)))
        .getOrElse(Left(ParseFailure(s"Not a $typeName $value")): Either[ParseFailure, A])
  }

  object implicits {
    case class Youtube(value: String)
    case class Facebook(value: String)
    case class Twitter(value: String)

    given Cons[Youtube]  = Youtube
    given Cons[Facebook] = Facebook
    given Cons[Twitter]  = Twitter
    import com.github.gekomad.regexcollection.Collection._

    // MACAddressOps
    case class MACAddress(value: String)
    given Cons[MACAddress] = MACAddress

    // EmailOps

    given Cons[Email]       = Email
    given Cons[EmailSimple] = EmailSimple
    given Cons[Email1]     = Email1

    case class Email(email: String)
    case class Email1(email: String)
    case class EmailSimple(emailSimple: String)

    // HexOps
    case class HEX(value: String)
    case class HEX1(value: String)
    case class HEX2(value: String)
    case class HEX3(value: String)

    given Cons[HEX]  = HEX
    given Cons[HEX1] = HEX1
    given Cons[HEX2] = HEX2
    given Cons[HEX3] = HEX3

    // UrlOps
    case class URL(value: String)
    case class URL1(value: String)
    case class URL2(value: String)
    case class URL3(value: String)
    case class FTP(value: String)
    case class FTP1(value: String)
    case class FTP2(value: String)
    case class Domain(value: String)

    given Cons[URL]    = URL
    given Cons[URL1]   = URL1
    given Cons[URL2]   = URL2
    given Cons[URL3]   = URL3
    given Cons[FTP]    = FTP
    given Cons[FTP1]   = FTP1
    given Cons[FTP2]   = FTP2
    given Cons[Domain] = Domain

    // MD5Ops
    given Cons[MD5] = MD5
    case class MD5(url: String)

    // SHAOps
    given Cons[SHA1]   = SHA1
    given Cons[SHA256] = SHA256

    case class SHA1(value: String)
    case class SHA256(value: String)

    // IPOps
    given Cons[IP]  = IP
    given Cons[IP6] = IP6

    case class IP(value: String)
    case class IP6(value: String)

    // BitcoinAddOps
    given Cons[BitcoinAdd] = BitcoinAdd

    case class BitcoinAdd(value: String)

    // PhonesOps
    given Cons[USphoneNumber]      = USphoneNumber
    given Cons[ItalianMobilePhone] = ItalianMobilePhone
    given Cons[ItalianPhone]       = ItalianPhone

    case class USphoneNumber(value: String)
    case class ItalianMobilePhone(value: String)
    case class ItalianPhone(value: String)

    // TimeOps
    given Cons[Time24] = Time24
    given Cons[MDY]    = MDY
    given Cons[MDY2]   = MDY2
    given Cons[MDY3]   = MDY3
    given Cons[MDY4]   = MDY4
    given Cons[DMY]    = DMY
    given Cons[DMY2]   = DMY2
    given Cons[DMY3]   = DMY3
    given Cons[DMY4]   = DMY4
    given Cons[Time]   = Time

    case class Time24(value: String)
    case class MDY(value: String)
    case class MDY2(value: String)
    case class MDY3(value: String)
    case class MDY4(value: String)
    case class DMY(value: String)
    case class DMY2(value: String)
    case class DMY3(value: String)
    case class DMY4(value: String)
    case class Time(value: String)

    // CrontabOps
    case class Cron(value: String)
    given Cons[Cron]            = Cron

    // CodesOps

    case class ItalianFiscalCode(value: String)
    case class ItalianVAT(value: String)
    case class ItalianIban(value: String)
    case class USstates(value: String)
    case class USstates1(value: String)
    case class USZipCode(value: String)
    case class ItalianZipCode(value: String)
    case class USstreets(value: String)
    case class USstreetNumber(value: String)
    case class GermanStreet(value: String)

    given Cons[ItalianFiscalCode] = ItalianFiscalCode
    given Cons[ItalianVAT]        = ItalianVAT
    given Cons[ItalianIban]       = ItalianIban
    given  Cons[USstates]         = USstates
    given  Cons[USstates1]        = USstates1
    given Cons[USZipCode]         = USZipCode
    given Cons[ItalianZipCode]    = ItalianZipCode
    given  Cons[USstreets]        = USstreets
    given  Cons[USstreetNumber]   = USstreetNumber
    given  Cons[GermanStreet]     = GermanStreet

    // ConcurrencyOps
    case class UsdCurrency(value: String)
    case class EurCurrency(value: String)
    case class YenCurrency(value: String)

    given Cons[UsdCurrency] = UsdCurrency
    given Cons[EurCurrency] = EurCurrency
    given Cons[YenCurrency] = YenCurrency

    // StringsOps
    case class NotASCII(value: String)
    case class SingleChar(value: String)
    case class AZString(value: String)
    case class StringAndNumber(value: String)
    case class AsciiString(value: String)

    given Cons[NotASCII]         = NotASCII
    given Cons[SingleChar]      = SingleChar
    given Cons[AZString]        = AZString
    given Cons[StringAndNumber] = StringAndNumber
    given Cons[AsciiString]     = AsciiString

    // LogsOps
    case class ApacheError(value: String)
    given Cons[ApacheError] = ApacheError

    // NumbersOps
    case class Number1(value: String)
    case class Unsigned32(value: String)
    case class Signed(value: String)
    case class Percentage(value: String)
    case class Scientific(value: String)
    case class SingleNumber(value: String)
    case class Celsius(value: String)
    case class Fahrenheit(value: String)

    given Cons[Number1]       = Number1
    given Cons[Unsigned32]    = Unsigned32
    given Cons[Signed]        = Signed
    given Cons[Percentage]    = Percentage
    given  Cons[Scientific]   = Scientific
    given  Cons[SingleNumber] = SingleNumber
    given  Cons[Celsius]      = Celsius
    given  Cons[Fahrenheit]   = Fahrenheit

    // CoordinatesOps
    case class Coordinate(value: String)
    case class Coordinate1(value: String)
    case class Coordinate2(value: String)

    given Cons[Coordinate]  = Coordinate
    given Cons[Coordinate1] = Coordinate1
    given Cons[Coordinate2] = Coordinate2

    //comments
    case class Comments(value: String)

    given Cons[Comments] = Comments

  }
}
