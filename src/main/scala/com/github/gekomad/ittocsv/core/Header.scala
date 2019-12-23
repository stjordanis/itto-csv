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

  /**
    * @param csvFormat the [[com.github.gekomad.ittocsv.parser.IttoCSVFormat]] formatter
    * @return the string with class's fields name encoded according with csvFormat
    */
  def csvHeader[T](implicit h: FieldNames[T], csvFormat: IttoCSVFormat): String = h().map(StringToCsvField.stringToCsvField).mkString(csvFormat.delimeter.toString)

  def fieldNames[T](implicit h: FieldNames[T]): List[String] = h()

}
