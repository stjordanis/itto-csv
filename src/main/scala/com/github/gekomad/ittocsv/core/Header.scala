package com.github.gekomad.ittocsv.core

import com.github.gekomad.ittocsv.parser.{IttoCSVFormat, StringToCsvField}

object Header {

//  import shapeless._
//  import shapeless.ops.record._
//  import shapeless.ops.hlist.ToTraversable

  trait FieldNames[?] {
    def apply(): List[String]
  }

//  implicit def toNames[T, Repr <: HList, KeysRepr <: HList](
//    implicit gen: LabelledGeneric.Aux[T, Repr],
//    keys: Keys.Aux[Repr, KeysRepr],
//    traversable: ToTraversable.Aux[KeysRepr, List, Symbol]
//  ): FieldNames[T] = new FieldNames[T] {
//    def apply(): List[String] = keys().toList.map(_.name)
//  }

  import scala.deriving._
  import scala.compiletime.erasedValue
  import scala.compiletime.{constValue, S}

  inline def tupleToList[T <: Tuple] : List[String] =
    inline erasedValue[T] match {
    case _ : Unit => Nil
    case _ : (head *: tail) => (inline constValue[head] match { case str : String => str }) :: tupleToList[tail]
  }



  /**
    * @param csvFormat the [[com.github.gekomad.ittocsv.parser.IttoCSVFormat]] formatter
    * @return the string with class's fields name encoded according with csvFormat
    */
  def csvHeader[T](implicit h: FieldNames[T], csvFormat: IttoCSVFormat): String = h().map(StringToCsvField.stringToCsvField).mkString(csvFormat.delimeter.toString)
  inline def fieldNames[P <: scala.Product](implicit mirror:Mirror.Of[P]) : List[String] = tupleToList[mirror.MirroredElemLabels]

//  def fieldNames[T](implicit h: FieldNames[T]): List[String] = h()

}
