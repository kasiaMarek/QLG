package net.marek.kasia.qlg.functionTranslation.exorcism

import net.marek.kasia.qlg.functionTranslation.Term
import org.scalatest.FunSuite

class ExorcismTest extends FunSuite {

  test("testPerformExorlink") {

  }

  test("testExorlink") {
    assert(Exorcism.exorlink(Term(List(0, 1, 2, 0)), Term(List(0, 0, 2, 0))) == List(Term(List(0, 2, 2, 0))))

    val resTests = Exorcism.exorlink(Term(List(0, 1, 2, 0)), Term(List(0, 0, 2, 2)))
    assert(resTests.contains(Term(List(0,2,2,2))))
    assert(resTests.contains(Term(List(0,1,2,2))))
  }

}
