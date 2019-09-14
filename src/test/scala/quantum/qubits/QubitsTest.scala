package quantum.qubits

import breeze.linalg.DenseVector
import org.scalatest.FunSuite
import quantum.QNum


class QubitsTest extends FunSuite {
//  test("tensor") {
//    assert(Qubits.tensor(DenseVector(1.0, 2.0, 0.5), DenseVector(1.0, 0.5)) == DenseVector(1.0, 0.5, 2.0, 1.0, 0.5, 0.25))
//  }
//
  test("make qbits from list") {
    assert(new Qubits(1,0,0,1).q == DenseVector(
      QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.one,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero,QNum.zero
    ))
  }

//  test("bin to dec") {
//    assert(Qubits.binToDec(List(1,0,1,0)) == 10)
//  }

//  test("dec to bin") {
//    assert(Qubits.decToBin(10) == List(1,0,1,0))
//  }

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
