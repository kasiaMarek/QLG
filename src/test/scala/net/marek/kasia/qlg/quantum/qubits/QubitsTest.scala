package net.marek.kasia.qlg.quantum.qubits

import breeze.linalg.DenseVector
import org.scalatest.FunSuite
import net.marek.kasia.qlg.quantum.QNum


class QubitsTest extends FunSuite {
  test("make qbits from list") {
    assert(new Qubits(1,0,0,1).q == DenseVector(
      QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.one,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero
    ))
  }

  test("measure probability") {
    assert(
      new Qubits(
        new DenseVector(
          Array(QNum(rationalPart = 0.5), QNum(sqrtPart = 1), QNum(), QNum(rationalPart = 0.5))
        ), 2
      ).getProbabilitiesForAQubit(1) == (0.25, 0.75)
    )
  }

  test("measure probability for all") {
    assert(
      new Qubits(
        new DenseVector(
          Array(QNum(rationalPart = 0.5), QNum(sqrtPart = 1), QNum(), QNum(rationalPart = 0.5))
        ), 2
      ).getProbabilitiesOf1ForAll() == List(0.25, 0.75)
    )
  }


}
