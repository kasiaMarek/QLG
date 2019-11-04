package net.marek.kasia.qlg.parser

sealed abstract class Expression //

case class Attribution(v: Variable, va: Va) extends Expression //

//net.marek.kasia.qlg.quantum gates
sealed abstract class QGate extends Expression
case class Frd(controls: List[Variable], v1: Variable, v2: Variable) extends QGate //
case class Tfl(controls: List[Variable], v: Variable) extends QGate //
case class Hdm(v: Variable) extends QGate //
case class NotQ(v: Variable) extends QGate //

sealed abstract class V
case class Variable(name: String) extends V //

sealed abstract class Va extends V

//const
case class One() extends Va
case class Zero() extends Va

//classical gates
sealed abstract class ClsGate extends Va
case class Or(vs: List[V]) extends ClsGate
case class And(vs: List[V]) extends ClsGate
case class Not(v:V) extends ClsGate
case class Copy(v: Variable) extends ClsGate
