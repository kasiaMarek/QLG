package net.marek.kasia.qlg.parser

import scala.util.parsing.combinator._

class CQLL extends RegexParsers {
  def program: Parser[List[Expression]] = rep1(expression)

  def expression: Parser[Expression] = (
    variable~"="~v ^^
    { case v~"="~va => Attribution(v, va) }
  | qGate
  )

  def v: Parser[V] = (
    "1" ^^ (_ => One)
  | "0" ^^ (_ => Zero)
  | clsGate
  | variable
  )

  def clsGate: Parser[BoolFunction] = (
    "and("~>vList<~")" ^^ And
  | "or("~>vList<~")" ^^ Or
  | "not("~>v<~")" ^^ Not
  | "xor("~>vList<~")" ^^ Xor
    )

  def vList: Parser[List[V]] = rep1sep(v, ",")

  def qGate: Parser[QGate] = (
    "hdm("~>variable<~")" ^^ Hdm
  | "frd(:"~variable~","~variable~","~variable~")" ^^
    { case "frd(:"~c~","~v1~","~v2~")" => Frd(List(c), v1, v2) }
  | "frd("~variable~",:"~variable~","~variable~")" ^^
    { case "frd("~v1~",:"~c~","~v2~")" => Frd(List(c), v1, v2) }
  | "frd(:"~variable~","~variable~",:"~variable~")" ^^
    { case "frd("~v1~","~v2~",:"~c~")" => Frd(List(c), v1, v2) }

  | "swp("~variable~","~variable~")" ^^
      {case "swp("~v1~","~v2~")" => Frd(List(), v1, v2)}

  | "tfl("~optLeftControls~variable~optRightControls~")" ^^
    {case "tfl("~lc1~v~lc2~")" => Tfl(lc1 ++ lc2, v)}

  | "not("~>variable<~")" ^^ (x => NotQ(x))
  )

  def optLeftControls: Parser[List[Variable]] = opt(controls~",") ^^ {
    case Some(c~",") => c
    case _ => List()
  }
  def optRightControls: Parser[List[Variable]] = opt(","~controls) ^^ {
    case Some(","~c) => c
    case _ => List()
  }

  def controls: Parser[List[Variable]] = repsep(":"~>variable, ",")

  def variable: Parser[Variable] = "[a-zA-Z_]+".r ^^ Variable


}
