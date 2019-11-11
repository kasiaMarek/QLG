package net.marek.kasia.qlg.exorcism.circuitToESOP

import net.marek.kasia.qlg.parser.{And, Not, One, Or, Variable, Xor, Zero}
import org.scalatest.FunSuite

class ShannonExpansionTest extends FunSuite {

  test("testMakeSimpleOr") {
    val orSimple = ShannonExpansion.makeSimple(Or(List(Variable("a"), Zero(), Variable("b"), Zero(), Zero())))
    assert(orSimple == Or(List(Variable("a"), Variable("b"))))
  }

  test("testMakeSimpleAnd") {
    val orSimple = ShannonExpansion.makeSimple(And(List(Variable("a"), Zero(), Variable("b"), Zero(), Zero())))
    assert(orSimple == Zero())
  }

  test("testMakeSimpleXor") {
    val orSimple = ShannonExpansion.makeSimple(Xor(List(Variable("a"), One(), Variable("b"), Zero(), One())))
    assert(orSimple == Xor(List(Variable("a"), Variable("b"))))
  }

  test("testMakeSimpleNot") {
    val orSimple = ShannonExpansion.makeSimple(Not(One()))
    assert(orSimple == Zero())
  }

  test("testMakeSimpleNotWithApplyFunction") {
    val orSimple = ShannonExpansion.makeSimple(Not(Variable("a")),v => if(v == Variable("a")) Zero() else One())
    assert(orSimple == One())
  }

  test("testMakeSimpleFunction") {
    val orSimple = ShannonExpansion.makeSimple(Xor(List(Variable("a"), And(List(Not(Zero()), One())), Variable("b"), And(List(Not(One()), One(), One())), Or(List(One(), Zero(), Zero())))))
    assert(orSimple == Xor(List(Variable("a"), Variable("b"))))
  }

  test("testMakeSimpleFunctionWithApplyVal") {
    val orSimple = ShannonExpansion.makeSimple(Xor(List(Variable("a"), And(List(Variable("c"), Variable("c"))), Variable("b"), And(List(Not(Variable("c")), Variable("c"), One())), Or(List(Variable("c"), Zero(), Zero())))), v => if(v == Variable("c")) One() else v)
    assert(orSimple == Xor(List(Variable("a"), Variable("b"))))
  }

}
