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
    "1" ^^ (_ => One())
  | "0" ^^ (_ => Zero())
  | clsGate
  | variable
  )

  def clsGate: Parser[BoolFunction] = (
    "and("~v~","~v~")" ^^
    { case "and("~v1~","~v2~")" => And(List(v1, v2)) }
  | "or("~v~","~v~")" ^^
    {case "or("~v1~","~v2~")" => Or(List(v1, v2))}
  | "not("~>v<~")" ^^ Not
  | "xor("~v~","~v~")" ^^
    { case "xor("~v1~","~v2~")" => Xor(List(v1, v2)) }
    )

  def qGate: Parser[QGate] = (
    "hdm("~>variable<~")" ^^ Hdm
  | "frd("~optLeftControls~variable~","~optLeftControls~variable~optRightControls~")" ^^
      { case "frd("~lc1~v1~","~lc2~v2~lc3~")" => Frd(lc1 ++ lc2 ++ lc3, v1, v2) }
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
